package concert.intergration;

import concert.application.chargebalance.ChargeBalanceCommand;
import concert.application.chargebalance.ChargeBalanceService;
import concert.application.createtoken.CreateTokenService;
import concert.domain.balance.Balance;
import concert.domain.balance.BalanceRepository;
import concert.domain.balance.service.BalanceRequest;
import concert.domain.balance.service.BalanceService;
import concert.domain.queuetoken.TokenStatus;
import concert.domain.queuetoken.service.QueueTokenRequest;
import concert.domain.queuetoken.service.QueueTokenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class BalanceChargeServiceTest {

    @Autowired
    private ChargeBalanceService chargeBalanceService;

    @Autowired
    private CreateTokenService createTokenService;

    @Autowired
    private QueueTokenService queueTokenService;

    @Autowired
    private BalanceService balanceService;

    private UUID token;
    private long userId;
    private long amount;

    @BeforeEach
    void setUp() {
        // 테스트에 필요한 데이터 초기화
        token = createTokenService.createToken().getToken();
        queueTokenService.updateStatus(
                QueueTokenRequest.builder()
                        .token(token)
                        .status(TokenStatus.PROCESSED)
                        .build()
        );
        userId = 1L; // 테스트를 위한 임의의 사용자 ID
        amount = 100L; // 테스트를 위한 금액
    }

    @Test
    @Transactional
    void chargeBalance_shouldChargeSuccessfully() {
        // Given
        ChargeBalanceCommand command = new ChargeBalanceCommand(token, userId, amount);

        // When
        Balance actualBalance = chargeBalanceService.chargeBalance(command);

        // Then
        Balance updatedBalance = balanceService.getBalance(
                BalanceRequest.builder()
                        .userId(userId)
                        .build()
        );

        assertThat(updatedBalance.getBalance()).isEqualTo(amount);
        assertThat(actualBalance.getUserId()).isEqualTo(userId);
        assertThat(actualBalance.getBalance()).isEqualTo(amount);
    }
}