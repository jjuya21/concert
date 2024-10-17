package concert.infrastructure.seat;

import concert.domain.seat.Seat;
import concert.domain.seat.SeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class SeatRepositoryImpl implements SeatRepository {

    private final SeatJpaRepository jpaRepository;

    @Override
    public List<Seat> getByConcertItemId(long concertItemId) {
        return jpaRepository.findByConcertItemId(concertItemId);
    }

    @Override
    public Seat updateStatus(Seat seat) {
        return jpaRepository.save(seat);
    }

    @Override
    public Seat getById(long seatId) {
        return jpaRepository.findById(seatId);
    }
}
