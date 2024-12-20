package concert.infrastructure.concertitem;

import concert.domain.concertitem.ConcertItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConcertItemJpaRepository extends JpaRepository<ConcertItem, Long> {

    List<ConcertItem> findByConcertId(long concertId);

    ConcertItem save(ConcertItem concertItem);

}
