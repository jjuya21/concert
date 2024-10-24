package concert.infrastructure.concertitem;

import concert.domain.concertitem.ConcertItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ConcertItemJpaRepository extends JpaRepository<ConcertItem, Long> {

    Optional<List<ConcertItem>> findByConcertId(long concertId);

    ConcertItem save(ConcertItem concertItem);

}
