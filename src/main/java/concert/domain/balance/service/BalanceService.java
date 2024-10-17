package concert.domain.balance.service;

import concert.domain.balance.Balance;
import concert.domain.balance.BalanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BalanceService {

    private final BalanceRepository repository;

    public Balance getBalance(BalanceRequest request) {

        Balance balance = repository.getBalance(request.getUserId());

        return balance;
    }

    public Balance useBalance(BalanceRequest request) {

        Balance balance = getBalance(request);
        balance.use(request.getAmount());

        balance = Balance.builder()
                .id(balance.getId())
                .userId(balance.getUserId())
                .balance(balance.getBalance())
                .build();

        repository.update(balance);

        return balance;
    }

    public Balance chargeBalance(BalanceRequest request) {

        Balance balance = getBalance(request);
        balance.charge(request.getAmount());

        balance = Balance.builder()
                .id(balance.getId())
                .userId(balance.getUserId())
                .balance(balance.getBalance())
                .build();

        repository.update(balance);

        return balance;
    }
}
