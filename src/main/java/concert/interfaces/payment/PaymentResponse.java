package concert.interfaces.payment;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PaymentResponse {

    long id;
    long reservationId;
    long amount;
    String paymentDate;
}
