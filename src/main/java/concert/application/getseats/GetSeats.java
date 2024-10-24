package concert.application.getseats;

import concert.application.TokenCheck;
import concert.domain.seat.Seat;
import concert.domain.seat.SeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetSeats {

    private final SeatRepository seatRepository;
    private final TokenCheck tokenCheck;

    @Transactional
    public List<Seat> getSeats(GetSeatsCommand command) {

        tokenCheck.tokenCheck(command.getToken());

        List<Seat> seats = seatRepository.getByConcertItemId(command.getConcertItemId());

        return seats;
    }
}
