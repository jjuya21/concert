package concert.interfaces.api.v1.reservation;

import concert.application.reserve.ReserveCommand;
import concert.application.reserve.ReserveService;
import concert.domain.reservation.Reservation;
import concert.domain.reservation.ReservationStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RequestMapping("/v1/api/reservation")
@RestController
@RequiredArgsConstructor
public class ReservationController {

    private final ReserveService reserveService;

    @PostMapping("/{userId}")
    public ResponseEntity<ReservationResponse> reservation(@PathVariable("userId") long userId, @RequestParam("seatId") long seatId) {

        Reservation reservation = reserveService.reserve(
                ReserveCommand.builder()
                        .userId(userId)
                        .seatId(seatId)
                        .build()
        );

        ReservationResponse response = new ReservationResponse(reservation.getId(), reservation.getUserId(), reservation.getSeatId());

        return ResponseEntity.ok(response);
    }
}
