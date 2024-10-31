package concert.domain.seat;

import java.util.List;
import java.util.Optional;

public interface SeatRepository {

    List<Seat> getByConcertItemId(long concertItemId);

    Seat update(Seat seat);

    Seat create(Seat seat);

    Optional<Seat> getById(long seatId);

    void deleteAll();
}
