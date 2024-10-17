package concert.interfaces.api.v1.concertitem;

import concert.application.getconcertdates.GetConcertDatesCommand;
import concert.application.getconcertdates.GetConcertDatesService;
import concert.domain.concertitem.ConcertItem;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequestMapping("/v1/api/concert-item")
@RestController
@RequiredArgsConstructor
public class ConcertItemController {

    private final GetConcertDatesService getConcertDatesService;

    @GetMapping("/{concertId}/available-dates")
    public ResponseEntity<List<ConcertItemResponse>> getAvailableDates(
            @PathVariable("concertId") long concertId,
            @RequestHeader("Authorization") String authHeader) {

        String tokenStr = authHeader.replace("Bearer ", "").trim();
        UUID token = UUID.fromString(tokenStr);

        List<ConcertItemResponse> responses = getConcertDatesService.getConcertDates(
                GetConcertDatesCommand.builder()
                        .token(token)
                        .concertId(concertId)
                        .build()
        ).stream().map(ConcertItemResponse::from).toList();

        return ResponseEntity.ok(responses);
    }
}
