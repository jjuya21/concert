package concert.interfaces.api.v1.balance;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BalanceResponse {

    private long userId;
    private long balance;
}
