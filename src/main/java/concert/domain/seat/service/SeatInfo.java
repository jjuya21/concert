package concert.domain.seat.service;

import concert.domain.seat.SeatStatus;
import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
public class SeatInfo {

    private long seatId;
    private long seatNo;
    private long concertItemId;
    private long price;
    private SeatStatus status;
}
