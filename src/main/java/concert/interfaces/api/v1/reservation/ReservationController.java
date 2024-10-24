package concert.interfaces.api.v1.reservation;

import concert.application.reserve.Reserve;
import concert.application.reserve.ReserveCommand;
import concert.domain.reservation.Reservation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/v1/api/reservation")
@RestController
@RequiredArgsConstructor
public class ReservationController {

    private final Reserve reserve;

    @PostMapping("/{userId}")
    public ResponseEntity<ReservationResponse> reservation(@PathVariable("userId") long userId,
                                                           @RequestBody ReservationRequest request) throws Exception {

        Reservation reservation = reserve.reserve(
                ReserveCommand.builder()
                        .userId(userId)
                        .seatId(request.getSeatId())
                        .build()
        );

        ReservationResponse response = new ReservationResponse(reservation.getId(), reservation.getUserId(), reservation.getSeatId());

        return ResponseEntity.ok(response);
    }
}
