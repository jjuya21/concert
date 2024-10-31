package concert.unit;

import concert.domain.balance.Balance;
import concert.domain.balance.BalanceRepository;
import concert.domain.balance.service.BalanceInfo;
import concert.domain.balance.service.BalanceService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class BalanceServiceTest {

    private final long DEFAULT_BALANCE_ID = 1L;
    private final long DEFAULT_BALANCE_USER_ID = 1L;
    private final long DEFAULT_BALANCE = 80000L;
    private final Balance DEFAULT_SAVED_BALANCE = Balance.builder()
            .id(DEFAULT_BALANCE_ID)
            .userId(DEFAULT_BALANCE_USER_ID)
            .balance(DEFAULT_BALANCE)
            .build();

    @Mock
    private BalanceRepository balanceRepository;
    @InjectMocks
    private BalanceService balanceService;

    @Test
    void getBalance() throws Exception {
        // given
        BalanceInfo info = BalanceInfo.builder()
                .userId(DEFAULT_BALANCE_USER_ID)
                .build();

        given(balanceRepository.getBalance(DEFAULT_BALANCE_USER_ID)).willReturn(Optional.of(DEFAULT_SAVED_BALANCE));

        // when
        Balance result = balanceService.getBalance(info);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getUserId()).isEqualTo(DEFAULT_BALANCE_USER_ID);
        assertThat(result.getBalance()).isEqualTo(DEFAULT_BALANCE);
    }

    @Test
    void useBalance() throws Exception {
        // given
        long useBalance = 10000L;
        Balance updateBalance = Balance.builder()
                .id(DEFAULT_BALANCE_ID)
                .userId(DEFAULT_BALANCE_USER_ID)
                .balance(DEFAULT_BALANCE - useBalance)
                .build();

        BalanceInfo info = BalanceInfo.builder()
                .userId(DEFAULT_BALANCE_USER_ID)
                .amount(useBalance)
                .build();

        given(balanceRepository.getBalance(DEFAULT_BALANCE_USER_ID)).willReturn(Optional.of(DEFAULT_SAVED_BALANCE));
        given(balanceRepository.update(any(Balance.class))).willReturn(updateBalance);

        // when
        Balance result = balanceService.useBalance(info);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getUserId()).isEqualTo(1L);
        assertThat(result.getBalance()).isEqualTo(70000L);
    }

    @Test
    void useBalanceWithException() throws Exception {
        // given
        long useBalance = DEFAULT_BALANCE + 1L;
        BalanceInfo info = BalanceInfo.builder()
                .userId(DEFAULT_BALANCE_USER_ID)
                .amount(useBalance)
                .build();

        given(balanceRepository.getBalance(DEFAULT_BALANCE_USER_ID)).willReturn(Optional.of(DEFAULT_SAVED_BALANCE));

        // when & then
        assertThatThrownBy(() -> balanceService.useBalance(info))
                .isInstanceOf(Exception.class)
                .hasMessage("잔액이 부족합니다.");
    }

    @Test
    void chargeBalance() throws Exception {
        // given
        long chargeBalance = 10000L;
        Balance updateBalance = Balance.builder()
                .id(DEFAULT_BALANCE_ID)
                .userId(DEFAULT_BALANCE_USER_ID)
                .balance(DEFAULT_BALANCE + chargeBalance)
                .build();

        BalanceInfo info = BalanceInfo.builder()
                .userId(DEFAULT_BALANCE_USER_ID)
                .amount(chargeBalance)
                .build();

        given(balanceRepository.getBalance(DEFAULT_BALANCE_USER_ID)).willReturn(Optional.of(DEFAULT_SAVED_BALANCE));
        given(balanceRepository.update(any(Balance.class))).willReturn(updateBalance);

        // when
        Balance result = balanceService.chargeBalance(info);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getUserId()).isEqualTo(DEFAULT_BALANCE_USER_ID);
        assertThat(result.getBalance()).isEqualTo(DEFAULT_BALANCE + chargeBalance);
    }

    @Test
    void chargeBalanceWithException() throws Exception {
        // given
        BalanceInfo info = BalanceInfo.builder()
                .userId(DEFAULT_BALANCE_USER_ID)
                .amount(-1L)
                .build();

        given(balanceRepository.getBalance(DEFAULT_BALANCE_USER_ID)).willReturn(Optional.of(DEFAULT_SAVED_BALANCE));

        // when & then
        assertThatThrownBy(() -> balanceService.chargeBalance(info))
                .isInstanceOf(Exception.class)
                .hasMessage("충전 금액은 음수일 수 없습니다.");
    }
}
