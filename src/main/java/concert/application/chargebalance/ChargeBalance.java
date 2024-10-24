package concert.application.chargebalance;

import concert.domain.balance.Balance;
import concert.domain.balance.service.BalanceInfo;
import concert.domain.balance.service.BalanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ChargeBalance {

    private final BalanceService balanceService;

    @Transactional
    public Balance chargeBalance(ChargeBalanceCommand command) {

        Balance balance = balanceService.chargeBalance(
                BalanceInfo.builder()
                        .userId(command.getUserId())
                        .amount(command.getAmount())
                        .build()
        );

        return balance;
    }
}
