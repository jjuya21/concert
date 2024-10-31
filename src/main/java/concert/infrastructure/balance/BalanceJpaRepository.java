package concert.infrastructure.balance;

import concert.domain.balance.Balance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BalanceJpaRepository extends JpaRepository<Balance, Long> {

    // @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Balance> findByUserId(long userId);

    Balance save(Balance balance);
}
