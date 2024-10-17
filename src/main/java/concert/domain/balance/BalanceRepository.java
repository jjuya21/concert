package concert.domain.balance;

import java.util.UUID;

public interface BalanceRepository {

    Balance getBalance(long userId);

    Balance update(Balance balance);
}
