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

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class BalanceServiceTest {

    @Mock
    private BalanceRepository balanceRepository;

    @InjectMocks
    private BalanceService balanceService;

    @Test
    void getBalance() {
        // given
        Balance savedBalance = Balance.builder()
                .userId(1L)
                .balance(80000L)
                .build();

        BalanceInfo info = BalanceInfo.builder()
                .userId(savedBalance.getUserId())
                .build();

        given(balanceRepository.getBalance(savedBalance.getUserId())).willReturn(savedBalance);

        // when
        Balance result = balanceService.getBalance(info);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getUserId()).isEqualTo(1L);
        assertThat(result.getBalance()).isEqualTo(80000L);
    }

    @Test
    void useBalance() {
        // given
        Balance savedBalance = Balance.builder()
                .userId(1L)
                .balance(80000L)
                .build();

        Balance updateBalance = Balance.builder()
                .userId(1L)
                .balance(70000L)
                .build();

        BalanceInfo info = BalanceInfo.builder()
                .userId(savedBalance.getUserId())
                .amount(10000L)
                .build();

        given(balanceService.getBalance(info)).willReturn(savedBalance);
        given(balanceRepository.update(any(Balance.class))).willReturn(updateBalance);

        // when
        Balance result = balanceService.useBalance(info);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getUserId()).isEqualTo(1L);
        assertThat(result.getBalance()).isEqualTo(70000L);
    }

    @Test
    void useBalanceWith1Exception() {
        // given
        Balance savedBalance = Balance.builder()
                .userId(1L)
                .balance(5000L)
                .build();

        BalanceInfo info = BalanceInfo.builder()
                .userId(savedBalance.getUserId())
                .amount(-100L)
                .build();

        given(balanceService.getBalance(info)).willReturn(savedBalance);

        // when & then
        assertThatThrownBy(() -> balanceService.useBalance(info))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Use amount must be greater than zero.");
    }

    @Test
    void useBalanceWith2Exception() {
        // given
        Balance savedBalance = Balance.builder()
                .userId(1L)
                .balance(5000L)
                .build();

        BalanceInfo info = BalanceInfo.builder()
                .userId(savedBalance.getUserId())
                .amount(10000L)
                .build();

        given(balanceService.getBalance(info)).willReturn(savedBalance);

        // when & then
        assertThatThrownBy(() -> balanceService.useBalance(info))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Use amount must be less than balance.");
    }

    @Test
    void chargeBalance() {
        // given
        Balance savedBalance = Balance.builder()
                .userId(1L)
                .balance(80000L)
                .build();

        Balance updateBalance = Balance.builder()
                .userId(1L)
                .balance(90000L)
                .build();

        BalanceInfo info = BalanceInfo.builder()
                .userId(savedBalance.getUserId())
                .amount(10000L)
                .build();

        given(balanceService.getBalance(info)).willReturn(savedBalance);
        given(balanceRepository.update(any(Balance.class))).willReturn(updateBalance);

        // when
        Balance result = balanceService.chargeBalance(info);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getUserId()).isEqualTo(1L);
        assertThat(result.getBalance()).isEqualTo(90000L);
    }

    @Test
    void chargeBalanceWithException() {
        // given
        Balance savedBalance = Balance.builder()
                .userId(1L)
                .balance(5000L)
                .build();

        BalanceInfo info = BalanceInfo.builder()
                .userId(savedBalance.getUserId())
                .amount(0L)
                .build();

        given(balanceService.getBalance(info)).willReturn(savedBalance);

        // when & then
        assertThatThrownBy(() -> balanceService.chargeBalance(info))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Charge amount must be greater than zero.");
    }
}
