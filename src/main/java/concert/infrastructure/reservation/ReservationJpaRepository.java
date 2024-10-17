package concert.infrastructure.reservation;

import concert.domain.reservation.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ReservationJpaRepository extends JpaRepository<Reservation, Long> {

    Reservation findById(long reservationId);

    Reservation save(Reservation reservation);
}
