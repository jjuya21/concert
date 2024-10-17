package concert.domain.reservation;

import java.util.UUID;

public interface ReservationRepository {

    Reservation reserve(Reservation reservation);

    Reservation getReservation(long reservationId);

    Reservation updateReservation(Reservation reservation);
}
