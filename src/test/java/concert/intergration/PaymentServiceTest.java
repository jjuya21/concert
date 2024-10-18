package concert.intergration;

import concert.application.payment.PaymentCommand;
import concert.application.payment.PaymentService;
import concert.domain.payment.Payment;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class PaymentServiceTest {

    private UUID token = UUID.fromString("a8098c1a-f86e-11da-bd1a-00112444be1e");

    @Autowired
    private PaymentService paymentService;

    @Test
    @Transactional
    void paymentServiceTest() {
        // given
        PaymentCommand command = PaymentCommand.builder()
                .token(token)
                .reservationId(1)
                .userId(1)
                .build();

        // when
        Payment result = paymentService.payment(command);

        // given
        assertThat(result).isNotNull();
        assertThat(result.getUserId()).isEqualTo(1);
    }
}
