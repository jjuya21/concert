package concert.interfaces.reservation;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ReservationResponse {

    long id;
    long userId;
    long seatId;
    String reservationDate;
    String status;
}
