package concert.interfaces.api.v1.payment;

import concert.application.payment.PaymentCommand;
import concert.application.payment.PaymentService;
import concert.domain.payment.Payment;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RequestMapping("/v1/api/payment")
@RestController
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/{reservationId}")
    public ResponseEntity<PaymentResponse> getPayment(@PathVariable long reservationId) {

        Payment payment = paymentService.payment(
                PaymentCommand.builder()
                        .reservationId(reservationId)
                        .build()
        );

        PaymentResponse response = new PaymentResponse(payment.getId(), payment.getReservationId(), payment.getCreatedAt());

        return ResponseEntity.ok(response);
    }
}
