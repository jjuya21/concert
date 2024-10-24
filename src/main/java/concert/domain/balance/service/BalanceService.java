package concert.domain.balance.service;

import concert.domain.balance.Balance;
import concert.domain.balance.BalanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BalanceService {

    private final BalanceRepository repository;

    public Balance getBalance(BalanceInfo info) {

        Balance balance = repository.getBalance(info.getUserId());

        return balance;
    }

    public Balance useBalance(BalanceInfo info) {

        Balance balance = getBalance(info);
        balance.use(info.getAmount());

        balance = Balance.builder()
                .id(balance.getId())
                .userId(balance.getUserId())
                .balance(balance.getBalance())
                .build();

        repository.update(balance);

        return balance;
    }

    public Balance chargeBalance(BalanceInfo info) {

        Balance balance = getBalance(info);
        balance.charge(info.getAmount());

        balance = Balance.builder()
                .id(balance.getId())
                .userId(balance.getUserId())
                .balance(balance.getBalance())
                .build();

        repository.update(balance);

        return balance;
    }
}
