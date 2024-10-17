package concert.application.getconcertdates;

import concert.application.TokenCheck;
import concert.domain.concertitem.ConcertItem;
import concert.domain.concertitem.ConcertItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetConcertDatesService {

    private final ConcertItemRepository concertItemRepository;
    private final TokenCheck tokenCheck;

    @Transactional
    public List<ConcertItem> getConcertDates(GetConcertDatesCommand command) {

        tokenCheck.tokenCheck(command.getToken());

        List<ConcertItem> concertItems = concertItemRepository.getByConcertId(command.getConcertId());

        return concertItems;
    }
}
