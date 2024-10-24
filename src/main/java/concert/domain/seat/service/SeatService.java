package concert.domain.seat.service;

import concert.domain.seat.Seat;
import concert.domain.seat.SeatRepository;
import concert.domain.seat.SeatStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SeatService {

    private final SeatRepository seatRepository;

    public Seat getSeat(SeatRequest request) {

        Seat seat = seatRepository.getById(request.getSeatId());

        return seat;
    }

    public Seat updateStatus(SeatRequest request) {

        Seat seat = getSeat(request);
        seat.setStatus(request.getStatus());

        seat = seatRepository.updateStatus(
                Seat.builder()
                        .id(request.getSeatId())
                        .concertItemId(seat.getConcertItemId())
                        .price(seat.getPrice())
                        .status(seat.getStatus())
                        .holdExpiryTime(seat.getHoldExpiryTime())
                        .build()
        );

        return seat;
    }
}
