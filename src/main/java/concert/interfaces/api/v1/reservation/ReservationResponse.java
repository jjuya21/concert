package concert.interfaces.api.v1.reservation;

import concert.domain.reservation.ReservationStatus;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
public class ReservationResponse {

    private long reservationId;
    private long userId;
    private long seatId;
}
