package concert.domain.concertitem;

import java.util.List;
import java.util.Optional;

public interface ConcertItemRepository {

    Optional<List<ConcertItem>> getByConcertId(long concertId);

    ConcertItem create(ConcertItem concertItem);
}
