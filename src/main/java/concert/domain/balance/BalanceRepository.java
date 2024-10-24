package concert.domain.balance;

import java.util.Optional;

public interface BalanceRepository {

    Optional<Balance> getBalance(long userId);

    Balance update(Balance balance);
}
