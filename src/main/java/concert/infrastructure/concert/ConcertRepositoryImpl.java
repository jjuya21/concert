package concert.infrastructure.concert;

import concert.domain.concert.Concert;
import concert.domain.concert.ConcertRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ConcertRepositoryImpl implements ConcertRepository {

    private final ConcertJpaRepository jpaRepository;

    @Override
    public List<Concert> getAll() {
        return jpaRepository.findAll();
    }
}
