package concert.interfaces.api.v1.balance;


import concert.application.chargebalance.ChargeBalanceCommand;
import concert.application.chargebalance.ChargeBalanceService;
import concert.domain.balance.Balance;
import concert.domain.balance.service.BalanceRequest;
import concert.domain.balance.service.BalanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping("/v1/api/balance")
@RestController
@RequiredArgsConstructor
public class BalanceController {

    private final BalanceService balanceService;
    private final ChargeBalanceService chargeBalanceService;

    @GetMapping("/{userId}")
    public ResponseEntity<BalanceResponse> getBalance(@PathVariable("userId") long userId) {

        Balance balance = balanceService.getBalance(
                BalanceRequest.builder()
                        .userId(userId)
                        .build()
        );
        BalanceResponse response = new BalanceResponse(balance.getUserId(), balance.getBalance());

        return ResponseEntity.ok(response);
    }

    @PostMapping("/{userId}")
    public ResponseEntity<BalanceResponse> chargeBalance(@PathVariable("userId") long userId, @RequestParam("amount") int amount) {

        Balance balance = chargeBalanceService.chargeBalance(
                ChargeBalanceCommand.builder()
                        .userId(userId)
                        .amount(amount)
                        .build()
        );
        BalanceResponse response = new BalanceResponse(balance.getUserId(), balance.getBalance());

        return ResponseEntity.ok(response);
    }
}
