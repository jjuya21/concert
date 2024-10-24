package concert.interfaces.api.v1.reservation;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReservationResponse {

    private long reservationId;
    private long userId;
    private long seatId;
}
