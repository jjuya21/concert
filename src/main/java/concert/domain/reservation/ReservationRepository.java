package concert.domain.reservation;

import java.util.Optional;

public interface ReservationRepository {

    Reservation reserve(Reservation reservation);

    Optional<Reservation> getReservation(long reservationId);

    Reservation updateReservation(Reservation reservation);
}
