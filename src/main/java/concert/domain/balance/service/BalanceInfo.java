package concert.domain.balance.service;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BalanceInfo {

    private long userId;
    private long amount;
}
