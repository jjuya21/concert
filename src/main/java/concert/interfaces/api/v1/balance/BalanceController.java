package concert.interfaces.api.v1.balance;


import concert.application.chargebalance.ChargeBalanceCommand;
import concert.application.chargebalance.ChargeBalanceService;
import concert.domain.balance.Balance;
import concert.domain.balance.service.BalanceInfo;
import concert.domain.balance.service.BalanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/v1/api/balance")
@RestController
@RequiredArgsConstructor
public class BalanceController {

    private final BalanceService balanceService;
    private final ChargeBalanceService chargeBalanceService;

    @GetMapping("/{userId}")
    public ResponseEntity<BalanceResponse> getBalance(@PathVariable("userId") long userId) throws Exception {

        Balance balance = balanceService.getBalance(
                BalanceInfo.builder()
                        .userId(userId)
                        .build()
        );
        BalanceResponse response = new BalanceResponse(balance.getUserId(), balance.getBalance());

        return ResponseEntity.ok(response);
    }

    @PostMapping("/{userId}")
    public ResponseEntity<BalanceResponse> chargeBalance(@PathVariable("userId") long userId,
                                                         @RequestBody BalanceRequest request) throws Exception {

        Balance balance = chargeBalanceService.chargeBalance(
                ChargeBalanceCommand.builder()
                        .userId(userId)
                        .amount(request.getAmount())
                        .build()
        );
        BalanceResponse response = new BalanceResponse(balance.getUserId(), balance.getBalance());

        return ResponseEntity.ok(response);
    }
}
