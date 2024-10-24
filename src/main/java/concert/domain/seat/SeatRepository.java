package concert.domain.seat;

import java.util.List;

public interface SeatRepository {

    List<Seat> getByConcertItemId(long concertItemId);

    Seat update(Seat seat);

    Seat create(Seat seat);

    Seat getById(long seatId);

    void deleteAll();
}
