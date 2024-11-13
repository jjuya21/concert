package concert.application.getconcerts;

import concert.domain.concert.Concert;
import concert.domain.concert.ConcertRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetConcerts {

    private final ConcertRepository concertRepository;

    @Cacheable(value = "concerts")
    @Transactional
    public List<Concert> getConcerts() {

        List<Concert> concerts = concertRepository.getAll();

        return concerts;
    }
}
