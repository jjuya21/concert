package concert.domain.seat.service;

import concert.domain.seat.SeatStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SeatRequest {

    private long seatId;
    private SeatStatus status;
}
