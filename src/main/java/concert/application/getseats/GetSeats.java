package concert.application.getseats;

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

    @Transactional
    public List<Seat> getSeats(GetSeatsCommand command) throws Exception {

        List<Seat> seats = seatRepository.getByConcertItemId(command.getConcertItemId());

        return seats;
    }
}
