package concert.interfaces;

import concert.interfaces.payment.PaymentResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("/api/payment")
@RestController
public class PaymentController {

    @GetMapping("/{userId}")
    public ResponseEntity<List<PaymentResponse>> getPayment(@PathVariable long userId) {

        List<PaymentResponse> paymentResponses = new ArrayList<>();
        paymentResponses.add(new PaymentResponse(1, 2, 80000, "2024-12-21"));
        paymentResponses.add(new PaymentResponse(2, 3, 80000, "2024-12-21"));
        return ResponseEntity.ok(paymentResponses);
    }
}
