package concert.infrastructure.balance;

import concert.domain.balance.Balance;
import concert.domain.balance.BalanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class BalanceRepositoryImpl implements BalanceRepository {

    private final BalanceJpaRepository jpaRepository;

    @Override
    public Balance getBalance(long userId) {
        return jpaRepository.findByUserId(userId);
    }

    @Override
    public Balance update(Balance balance) {
        return jpaRepository.save(balance);
    }
}
