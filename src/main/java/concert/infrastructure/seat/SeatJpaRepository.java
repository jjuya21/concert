package concert.infrastructure.seat;

import concert.domain.seat.Seat;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import java.util.List;

public interface SeatJpaRepository extends JpaRepository<Seat, Long> {

    List<Seat> findByConcertItemId(long concertItemId);

    Seat save(Seat seat);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Seat findById(long seatId);
}
