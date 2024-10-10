package concert.interfaces;

import concert.interfaces.reservation.ReservationResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("api/{userId}/reservation")
@RestController
public class ReservationController {

    @PostMapping
    public ResponseEntity<ReservationResponse> reservation(@PathVariable("userId") long userId, @RequestParam("seatId") long seatId) {

        ReservationResponse reservationResponse = new ReservationResponse(1, 2, 4, "2024-01-01", "payed");
        return ResponseEntity.ok(reservationResponse);
    }
}
