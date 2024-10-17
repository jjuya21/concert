package concert.infrastructure.balance;

import concert.domain.balance.Balance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BalanceJpaRepository extends JpaRepository<Balance, Long> {

    Balance findByUserId(long userId);

    Balance save(Balance balance);
}
