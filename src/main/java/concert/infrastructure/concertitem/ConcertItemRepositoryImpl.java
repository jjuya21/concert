package concert.infrastructure.concertitem;

import concert.domain.concertitem.ConcertItem;
import concert.domain.concertitem.ConcertItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ConcertItemRepositoryImpl implements ConcertItemRepository {

    private final ConcertItemJpaRepository jpaRepository;

    @Override
    public List<ConcertItem> getByConcertId(long concertId) {

        return jpaRepository.findByConcertId(concertId);
    }

    @Override
    public ConcertItem create(ConcertItem concertItem) {
        return jpaRepository.save(concertItem);
    }
}
