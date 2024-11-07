package concert.intergration;

import concert.application.chargebalance.ChargeBalanceService;
import concert.domain.balance.Balance;
import concert.domain.balance.BalanceRepository;
import concert.domain.balance.service.BalanceInfo;
import concert.domain.balance.service.BalanceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class BalanceUseServiceTest {

    private final long DEFAULT_USER_ID = 1L;
    private final long DEFAULT_AMOUNT = 200L;
    private final long DEFAULT_BALANCE = 800000L;
    @Autowired
    private ChargeBalanceService chargeBalanceService;
    @Autowired
    private BalanceService balanceService;
    @Autowired
    private BalanceRepository balanceRepository;

    @BeforeEach
    void setUp() {
        createBalance();
    }

    @DisplayName("잔액 충전을 하면 amount만큼 금액이 충전되있어야한다")
    @Test
    @Transactional
    void useBalanceTest() throws Exception {
        // Given
        BalanceInfo info = new BalanceInfo(DEFAULT_USER_ID, DEFAULT_AMOUNT);

        // When
        Balance actualBalance = balanceService.useBalance(info);

        // Then
        Balance updatedBalance = balanceService.getBalance(
                BalanceInfo.builder()
                        .userId(DEFAULT_USER_ID)
                        .build()
        );

        assertThat(actualBalance.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(updatedBalance.getBalance()).isEqualTo(DEFAULT_BALANCE - DEFAULT_AMOUNT);
    }

    @DisplayName("동시에 여러 결제 요청이 들어오면 첫번째 요청이외에는 실패한다.")
    @Test
    void useConcurrencyTest() throws Exception {
        // given
        int threadCount = 1000;

        BalanceInfo info = new BalanceInfo(DEFAULT_USER_ID, DEFAULT_AMOUNT);

        // when
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(threadCount);

        for (int i = 1; i <= threadCount; i++) {
            executor.execute(() -> {
                try {
                    balanceService.useBalance(info);
                } catch (Exception ignored) {

                } finally {
                    latch.countDown();
                }
            });
        }
        latch.await();
        executor.shutdown();

        // then
        Balance updatedBalance = balanceService.getBalance(
                BalanceInfo.builder()
                        .userId(DEFAULT_USER_ID)
                        .build()
        );

        assertThat(updatedBalance.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(updatedBalance.getBalance()).isEqualTo(DEFAULT_BALANCE - (DEFAULT_AMOUNT));
    }

    private void createBalance() {
        balanceRepository.save(
                Balance.builder()
                        .userId(DEFAULT_USER_ID)
                        .balance(DEFAULT_BALANCE)
                        .build()
        );
    }
}
