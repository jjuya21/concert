package concert.interfaces.api.v1.reservation;

import concert.application.reserve.Reserve;
import concert.application.reserve.ReserveCommand;
import concert.domain.reservation.Reservation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/v1/api/reservation")
@RestController
@RequiredArgsConstructor
public class ReservationController {

    private final Reserve reserve;

    @PostMapping("/{userId}/seat/{seatId}")
    public ResponseEntity<ReservationResponse> reservation(@PathVariable("userId") long userId,
                                                           @PathVariable("userId") long seatId) throws Exception {

        Reservation reservation = reserve.reserveWithRedisson(
                ReserveCommand.builder()
                        .userId(userId)
                        .seatId(seatId)
                        .build()
        );

        ReservationResponse response = new ReservationResponse(reservation.getId(), reservation.getUserId(), reservation.getSeatId());

        return ResponseEntity.ok(response);
    }
}
