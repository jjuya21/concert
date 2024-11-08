package concert.unit;


import concert.domain.balance.Balance;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BalanceTest {

    private final long DEFAULT_BALANCE_VALUE = 5000L;
    private Balance DEFAULT_BALANCE;

    @BeforeEach
    void setUp() {
        DEFAULT_BALANCE = Balance.builder()
                .balance(DEFAULT_BALANCE_VALUE)
                .build();
    }

    @DisplayName("충전하면 amount만큼 잔액에 더해져야한다")
    @Test
    void chargeTest() throws Exception {
        // given
        long amount = 1000L;

        // when
        DEFAULT_BALANCE.charge(amount);

        // then
        assertThat(DEFAULT_BALANCE.getBalance()).isEqualTo(DEFAULT_BALANCE_VALUE + amount);
    }

    @DisplayName("amount가 음수면 에러가 발생해야한다")
    @Test
    void chargeExceptionTest() throws Exception {
        // given
        long amount = -1000L;

        // when & then
        assertThrows(Exception.class, () -> DEFAULT_BALANCE.charge(amount));
    }

    @DisplayName("결제를 할 때 amount만큼 잔액에서 빼져야한다")
    @Test
    void useTest() throws Exception {
        // given
        long amount = 1000L;

        // when
        DEFAULT_BALANCE.use(amount);

        // then
        assertThat(DEFAULT_BALANCE.getBalance()).isEqualTo(DEFAULT_BALANCE_VALUE - amount);
    }

    @DisplayName("결제 시에 잔액보다 amount가 많을 때 에러가 발생해야한다")
    @Test
    void useExceptionTest() throws Exception {
        // given
        long amount = 10000L;

        // when & then
        assertThrows(Exception.class, () -> DEFAULT_BALANCE.use(amount));
    }
}
