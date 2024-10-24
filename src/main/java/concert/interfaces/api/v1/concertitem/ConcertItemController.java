package concert.interfaces.api.v1.concertitem;

import concert.application.getconcertdates.GetConcertDates;
import concert.application.getconcertdates.GetConcertDatesCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequestMapping("/v1/api/concert-item")
@RestController
@RequiredArgsConstructor
public class ConcertItemController {

    private final GetConcertDates getConcertDates;

    @GetMapping("/{concertId}/available-dates")
    public ResponseEntity<List<ConcertItemResponse>> getAvailableDates(
            @PathVariable("concertId") long concertId) throws Exception {

        List<ConcertItemResponse> responses = getConcertDates.getConcertDates(
                GetConcertDatesCommand.builder()
                        .concertId(concertId)
                        .build()
        ).stream().map(ConcertItemResponse::from).toList();

        return ResponseEntity.ok(responses);
    }
}
