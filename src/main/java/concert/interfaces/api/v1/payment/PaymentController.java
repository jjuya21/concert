package concert.interfaces.api.v1.payment;

import concert.application.payment.Payment;
import concert.application.payment.PaymentCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/v1/api/payment")
@RestController
@RequiredArgsConstructor
public class PaymentController {

    private final Payment payment;

    @PostMapping("/{reservationId}")
    public ResponseEntity<PaymentResponse> getPayment(@PathVariable long reservationId,
                                                      @RequestBody PaymentRequest request) {

        concert.domain.payment.Payment payment = this.payment.payment(
                PaymentCommand.builder()
                        .token(request.getToken())
                        .reservationId(reservationId)
                        .build()
        );

        PaymentResponse response = new PaymentResponse(payment.getId(), payment.getReservationId(), payment.getCreatedAt());

        return ResponseEntity.ok(response);
    }
}
