package concert.intergration;

import concert.application.reserve.Reserve;
import concert.application.reserve.ReserveCommand;
import concert.domain.reservation.Reservation;
import concert.domain.reservation.ReservationRepository;
import concert.domain.seat.Seat;
import concert.domain.seat.SeatRepository;
import concert.domain.seat.SeatStatus;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootTest
class ReserveTest {

    private final long SEAT_NO = 12L;
    private final long CONCERT_ITEM_ID = 1L;
    private final long PRICE = 80000L;
    @Autowired
    ReservationRepository reservationRepository;
    @Value("${custom.token}")
    private String token;
    @Autowired
    private Reserve reserve;
    @Autowired
    private SeatRepository seatRepository;

    @BeforeEach
    void setUp() {
        seatRepository.deleteAll();
    }

    @DisplayName("예매를 하면 좌석에 정보에 맞춰 예매 정보가 생성되어야한다.")
    @Test
    void reserveTest() throws Exception {
        // given
        Seat createdSeat = createSeat(SEAT_NO, CONCERT_ITEM_ID, PRICE);

        ReserveCommand command = ReserveCommand.builder()
                .seatId(createdSeat.getId())
                .userId(1)
                .build();

        // when
        Reservation result = reserve.reserve(command);

        // then
        Reservation reservation = reservationRepository.getReservation(result.getId()).get();
        Assertions.assertThat(reservation.getSeatId()).isEqualTo(result.getSeatId());
    }

    @DisplayName("예매가 되기 전에 좌석의 상태가 바뀌어 다음 요청은 실행되지 않아야한다.")
    @Test
    void reserveConcurrencyTest() throws Exception {
        // given
        Seat createdSeat = createSeat(SEAT_NO, CONCERT_ITEM_ID, PRICE);

        int threadCount = 5;

        ReserveCommand command = ReserveCommand.builder()
                .seatId(createdSeat.getId())
                .userId(1)
                .build();

        // when
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(threadCount);

        Reservation result = reserve.reserve(command);

        for (int i = 1; i <= threadCount; i++) {
            executor.execute(() -> {
                try {
                    reserve.reserve(command);
                } catch (Exception ignored) {

                } finally {
                    latch.countDown();
                }
            });
        }

        // then
        Reservation reservation = reservationRepository.getReservation(result.getId()).get();
        Assertions.assertThat(reservation.getSeatId()).isEqualTo(result.getSeatId());
    }

    private Seat createSeat(long seatNo, long concertItemId, long price) {

        return seatRepository.create(
                Seat.builder()
                        .seatNo(seatNo)
                        .concertItemId(concertItemId)
                        .price(price)
                        .status(SeatStatus.EMPTY)
                        .build()
        );
    }
}