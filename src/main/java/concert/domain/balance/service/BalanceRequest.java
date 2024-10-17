package concert.domain.balance.service;

import concert.application.chargebalance.ChargeBalanceCommand;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BalanceRequest {

    private long userId;
    private long amount;
}
