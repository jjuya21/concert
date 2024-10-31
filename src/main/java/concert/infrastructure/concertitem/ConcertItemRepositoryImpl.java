package concert.infrastructure.concertitem;

import concert.domain.concertitem.ConcertItem;
import concert.domain.concertitem.ConcertItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ConcertItemRepositoryImpl implements ConcertItemRepository {

    private final ConcertItemJpaRepository jpaRepository;

    @Override
    public Optional<List<ConcertItem>> getByConcertId(long concertId) {

        return jpaRepository.findByConcertId(concertId);
    }

    @Override
    public ConcertItem create(ConcertItem concertItem) {
        return jpaRepository.save(concertItem);
    }
}
