package concert.intergration;

import concert.application.payment.Payment;
import concert.application.payment.PaymentCommand;
import concert.domain.balance.Balance;
import concert.domain.balance.BalanceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class PaymentTest {

    @Value("${custom.token}")
    private String token;

    @Autowired
    private Payment payment;

    @Autowired
    private BalanceRepository balanceRepository;

    @BeforeEach
    void setUp() {
        createBalance();
    }

    @DisplayName("결재를 하면 예매 정보에 맞춰 결제 정보가 생성되어야한다")
    @Test
    @Transactional
    void paymentTest() throws Exception {
        // given
        PaymentCommand command = PaymentCommand.builder()
                .token(token)
                .reservationId(1)
                .userId(3L)
                .build();

        // when
        concert.domain.payment.Payment result = payment.payment(command);

        // given
        assertThat(result).isNotNull();
        assertThat(result.getUserId()).isEqualTo(3L);
    }

    private void createBalance() {
        balanceRepository.save(
                Balance.builder()
                        .userId(3L)
                        .balance(80000L)
                        .build()
        );
    }
}
