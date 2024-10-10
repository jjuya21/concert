package concert.interfaces;

import concert.interfaces.seat.SeatResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("api/seat")
@RestController
public class SeatController {

    @GetMapping("/{concertItemId}/available-seats")
    public ResponseEntity<List<SeatResponse>> getAvailableSeats(@PathVariable("concertItemId") long concertItemId) {

        List<SeatResponse> seatResponses = new ArrayList<>();
        seatResponses.add(new SeatResponse(1, 43, 80000, "available"));
        seatResponses.add(new SeatResponse(2, 44, 80000, "available"));
        return ResponseEntity.ok(seatResponses);
    }
}
