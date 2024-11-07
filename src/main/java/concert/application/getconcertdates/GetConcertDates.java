package concert.application.getconcertdates;

import concert.domain.concertitem.ConcertItem;
import concert.domain.concertitem.ConcertItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetConcertDates {

    private final ConcertItemRepository concertItemRepository;

    @Transactional
    public List<ConcertItem> getConcertDates(GetConcertDatesCommand command) {

        List<ConcertItem> concertItems = concertItemRepository.getByConcertId(command.getConcertId());

        return concertItems;
    }
}
