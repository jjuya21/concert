package concert.application.chargebalance;

import concert.application.TokenCheck;
import concert.domain.balance.Balance;
import concert.domain.balance.service.BalanceRequest;
import concert.domain.balance.service.BalanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ChargeBalanceService {

    private final BalanceService balanceService;
    private final TokenCheck tokenCheck;

    @Transactional
    public Balance chargeBalance(ChargeBalanceCommand command) {

        tokenCheck.tokenCheck(command.getToken());

        Balance balance = balanceService.chargeBalance(
                BalanceRequest.builder()
                        .userId(command.getUserId())
                        .amount(command.getAmount())
                        .build()
        );

        return balance;
    }
}
