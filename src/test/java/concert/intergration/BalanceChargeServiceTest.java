package concert.intergration;

import concert.application.chargebalance.ChargeBalance;
import concert.application.chargebalance.ChargeBalanceCommand;
import concert.application.createtoken.CreateToken;
import concert.domain.balance.Balance;
import concert.domain.balance.service.BalanceInfo;
import concert.domain.balance.service.BalanceService;
import concert.domain.queuetoken.TokenStatus;
import concert.domain.queuetoken.service.QueueTokenInfo;
import concert.domain.queuetoken.service.QueueTokenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class BalanceChargeServiceTest {

    @Autowired
    private ChargeBalance chargeBalance;

    @Autowired
    private CreateToken createToken;

    @Autowired
    private QueueTokenService queueTokenService;

    @Autowired
    private BalanceService balanceService;

    private String token;
    private long userId;
    private long amount;

    @BeforeEach
    void setUp() {
        // 테스트에 필요한 데이터 초기화
        token = createToken.createToken().getToken();
        queueTokenService.updateStatus(
                QueueTokenInfo.builder()
                        .token(token)
                        .status(TokenStatus.PROCESSED)
                        .build()
        );
        userId = 1L; // 테스트를 위한 임의의 사용자 ID
        amount = 200L; // 테스트를 위한 금액
    }

    @DisplayName("잔액 충전을 하면 amount만큼 금액이 충전되있어야한다")
    @Test
    @Transactional
    void chargeBalanceTest() {
        // Given
        ChargeBalanceCommand command = new ChargeBalanceCommand(token, userId, amount);

        // When
        Balance actualBalance = chargeBalance.chargeBalance(command);

        // Then
        Balance updatedBalance = balanceService.getBalance(
                BalanceInfo.builder()
                        .userId(userId)
                        .build()
        );

        assertThat(updatedBalance.getBalance()).isEqualTo(amount);
        assertThat(actualBalance.getUserId()).isEqualTo(userId);
        assertThat(actualBalance.getBalance()).isEqualTo(amount);
    }
}