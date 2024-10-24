package concert.interfaces.api.v1.payment;

import concert.application.payment.Payment;
import concert.application.payment.PaymentCommand;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/v1/api/payment")
@RestController
@RequiredArgsConstructor
public class PaymentController {

    private final Payment payment;

    @PostMapping("/{reservationId}")
    public ResponseEntity<PaymentResponse> getPayment(@PathVariable long reservationId, HttpServletRequest request) throws Exception {

        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new Exception("토큰이 존재하지 않습니다.");
        }

        String token = authorizationHeader.substring(7);

        concert.domain.payment.Payment payment = this.payment.payment(
                PaymentCommand.builder()
                        .token(token)
                        .reservationId(reservationId)
                        .build()
        );

        PaymentResponse response = new PaymentResponse(payment.getId(), payment.getReservationId(), payment.getCreatedAt());

        return ResponseEntity.ok(response);
    }
}
