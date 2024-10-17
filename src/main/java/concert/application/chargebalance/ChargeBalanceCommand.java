package concert.application.chargebalance;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChargeBalanceCommand {

    private UUID token;
    private long userId;
    private long amount;
}
