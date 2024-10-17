package concert.domain.concertitem;

import java.util.List;
import java.util.UUID;

public interface ConcertItemRepository {

    List<ConcertItem> getByConcertId(long concertId);
    ConcertItem create(ConcertItem concertItem);
}
