package concert.domain.concertitem;

import java.util.List;

public interface ConcertItemRepository {

    List<ConcertItem> getByConcertId(long concertId);

    ConcertItem create(ConcertItem concertItem);
}
