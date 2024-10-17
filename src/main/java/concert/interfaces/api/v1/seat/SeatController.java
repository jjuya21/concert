package concert.interfaces.api.v1.seat;

import concert.application.getseats.GetSeatsCommand;
import concert.application.getseats.GetSeatsService;
import concert.domain.seat.Seat;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("/v1/api/seat")
@RestController
@RequiredArgsConstructor
public class SeatController {

    private final GetSeatsService getSeatsService;

    @GetMapping("/{concertItemId}/available-seats")
    public ResponseEntity<List<SeatResponse>> getAvailableSeats(@PathVariable("concertItemId") long concertItemId) {

        List<SeatResponse> responses = getSeatsService.getSeats(
                GetSeatsCommand.builder()
                        .concertItemId(concertItemId)
                        .build()
        ).stream().map(SeatResponse::from).toList();

        return ResponseEntity.ok(responses);
    }
}
