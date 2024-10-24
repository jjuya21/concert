package concert.interfaces.api.v1.payment;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class PaymentResponse {

    private long paymentId;
    private long reservationId;
    private LocalDateTime date;
}
