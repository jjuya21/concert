package concert.interfaces.api.v1.payment;

import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
public class PaymentResponse {

    private long paymentId;
    private long reservationId;
    private LocalDateTime date;
}
