package concert.domain.balance.service;

import concert.domain.balance.Balance;
import concert.domain.balance.BalanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BalanceService {

    private final BalanceRepository repository;

    public Balance getBalance(BalanceInfo info) throws Exception {

        Balance balance = repository.getBalance(info.getUserId())
                .orElseThrow(() -> new Exception("유저가 가지고 있는 Balance가 존재하지 않습니다."));

        return balance;
    }

    public Balance useBalance(BalanceInfo info) throws Exception {

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

    public Balance chargeBalance(BalanceInfo info) throws Exception {

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
