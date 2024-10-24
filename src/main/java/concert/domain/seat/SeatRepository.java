package concert.domain.seat;

import java.util.List;
import java.util.UUID;

public interface SeatRepository {

    List<Seat> getByConcertItemId(long concertItemId);

    Seat updateStatus(Seat seat);

    Seat getById(long seatId);
}
