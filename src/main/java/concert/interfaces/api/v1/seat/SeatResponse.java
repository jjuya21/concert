package concert.interfaces.api.v1.seat;

import concert.domain.seat.Seat;
import concert.domain.seat.SeatStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class SeatResponse {

    private long seatId;
    private long seatNo;
    private SeatStatus status;

    public static SeatResponse from(Seat seat) {

        return SeatResponse.builder()
                .seatId(seat.getId())
                .seatNo(seat.getSeatNo())
                .status(seat.getStatus())
                .build();
    }
}
