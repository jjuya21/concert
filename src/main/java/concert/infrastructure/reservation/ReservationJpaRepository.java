package concert.infrastructure.reservation;

import concert.domain.reservation.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReservationJpaRepository extends JpaRepository<Reservation, Long> {

    Optional<Reservation> findById(long reservationId);

    Reservation save(Reservation reservation);
}
