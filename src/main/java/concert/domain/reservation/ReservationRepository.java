package concert.domain.reservation;

import java.util.Optional;

public interface ReservationRepository {

    Reservation create(Reservation reservation);

    Optional<Reservation> getReservation(long reservationId);

    Reservation update(Reservation reservation);
}
