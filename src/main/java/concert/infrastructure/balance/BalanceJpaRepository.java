package concert.infrastructure.balance;

import concert.domain.balance.Balance;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

public interface BalanceJpaRepository extends JpaRepository<Balance, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Balance findByUserId(long userId);

    Balance save(Balance balance);
}
