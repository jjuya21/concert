package concert.infrastructure.seat;

import concert.domain.seat.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SeatJpaRepository extends JpaRepository<Seat, Long> {

    List<Seat> findByConcertItemId(long concertItemId);

    Seat save(Seat seat);

    // @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Seat> findById(long seatId);
}
