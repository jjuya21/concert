package concert.interfaces.api.v1.seat;

import concert.application.getseats.GetSeats;
import concert.application.getseats.GetSeatsCommand;
import concert.domain.seat.service.SeatInfo;
import concert.domain.seat.service.SeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/v1/api/seat")
@RestController
@RequiredArgsConstructor
public class SeatController {

    private final SeatService seatService;
    private final GetSeats getSeats;

    @GetMapping("/{concertItemId}/available-seats")
    public ResponseEntity<List<SeatResponse>> getAvailableSeats(
            @PathVariable("concertItemId") long concertItemId) throws Exception {

        List<SeatResponse> responses = getSeats.getSeats(
                GetSeatsCommand.builder()
                        .concertItemId(concertItemId)
                        .build()
        ).stream().map(SeatResponse::from).toList();

        return ResponseEntity.ok(responses);
    }

    @PostMapping
    public ResponseEntity<SeatResponse> createSeat(@RequestBody SeatRequest request) {

        SeatResponse response = SeatResponse.from(seatService.createSeat(
                SeatInfo.builder()
                        .seatNo(request.getSeatNo())
                        .concertItemId(request.getConcertItemId())
                        .price(request.getPrice())
                        .build()
        ));

        return ResponseEntity.ok(response);
    }
}
