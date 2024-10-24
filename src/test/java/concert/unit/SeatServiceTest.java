package concert.unit;

import concert.domain.seat.Seat;
import concert.domain.seat.SeatRepository;
import concert.domain.seat.SeatStatus;
import concert.domain.seat.service.SeatInfo;
import concert.domain.seat.service.SeatService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class SeatServiceTest {

    private final long DEFAULT_ID = 1L;
    private final long DEFAULT_SEAD_NO = 123L;
    private final long DEFAULT_CONCERT_ITEM_ID = 1L;
    private final long DEFAULT_PRICE = 80000L;
    @Mock
    private SeatRepository seatRepository;
    @InjectMocks
    private SeatService seatService;

    @Test
    void getSeat() throws Exception {
        // given
        Seat savedSeat = Seat.builder().id(DEFAULT_ID).build();

        SeatInfo seatInfo = SeatInfo.builder()
                .seatId(DEFAULT_ID)
                .build();

        given(seatRepository.getById(DEFAULT_ID)).willReturn(Optional.ofNullable(savedSeat));

        // when
        Seat result = seatService.getSeat(seatInfo);

        // then
        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(savedSeat);
        assertThat(result.getId()).isEqualTo(DEFAULT_ID);
    }

    @Test
    void updateStatusTest() throws Exception {
        // given
        Seat savedSeat = Seat.builder()
                .id(DEFAULT_ID)
                .seatNo(DEFAULT_SEAD_NO)
                .concertItemId(DEFAULT_CONCERT_ITEM_ID)
                .price(DEFAULT_PRICE)
                .status(SeatStatus.EMPTY)
                .holdExpiryTime(null)
                .build();

        Seat updateSeat = Seat.builder()
                .id(DEFAULT_ID)
                .seatNo(DEFAULT_SEAD_NO)
                .concertItemId(DEFAULT_CONCERT_ITEM_ID)
                .price(DEFAULT_PRICE)
                .status(SeatStatus.HOLDING)
                .holdExpiryTime(null)
                .build();

        SeatInfo seatInfo = SeatInfo.builder()
                .seatId(DEFAULT_ID)
                .build();

        given(seatService.getSeat(seatInfo)).willReturn(savedSeat);
        given(seatRepository.update(any(Seat.class))).willReturn(updateSeat);

        // when
        Seat result = seatService.updateStatus(seatInfo);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(DEFAULT_ID);
        assertThat(result.getStatus()).isEqualTo(SeatStatus.HOLDING);
    }

}
