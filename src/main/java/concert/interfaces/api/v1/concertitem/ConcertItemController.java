package concert.interfaces.api.v1.concertitem;

import concert.application.getconcertdates.GetConcertDates;
import concert.application.getconcertdates.GetConcertDatesCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequestMapping("/v1/api/concert-item")
@RestController
@RequiredArgsConstructor
public class ConcertItemController {

    private final GetConcertDates getConcertDates;

    @GetMapping("/{concertId}/available-dates")
    public ResponseEntity<List<ConcertItemResponse>> getAvailableDates(
            @PathVariable("concertId") long concertId,
            @RequestBody ConcertItemRequest request) {

        List<ConcertItemResponse> responses = getConcertDates.getConcertDates(
                GetConcertDatesCommand.builder()
                        .token(request.getToken())
                        .concertId(concertId)
                        .build()
        ).stream().map(ConcertItemResponse::from).toList();

        return ResponseEntity.ok(responses);
    }
}
