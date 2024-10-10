package concert.interfaces;

import concert.interfaces.balance.BalanceResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/balance")
@RestController
public class BalanceController {

    @GetMapping("/{userId}")
    public ResponseEntity<BalanceResponse> getBalance(@PathVariable("userId") long userId) {

        BalanceResponse balanceResponse = new BalanceResponse(1, 100000);
        return ResponseEntity.ok(balanceResponse);
    }

    @PostMapping("/{userId}")
    public ResponseEntity<BalanceResponse> chargeBalance(@PathVariable("userId") long userId, @RequestParam("amount") int amount) {

        BalanceResponse balanceResponse = new BalanceResponse(1, 100000);
        return ResponseEntity.ok(balanceResponse);
    }
}
