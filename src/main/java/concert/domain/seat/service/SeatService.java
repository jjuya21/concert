package concert.domain.seat.service;

import concert.domain.seat.Seat;
import concert.domain.seat.SeatRepository;
import concert.domain.seat.SeatStatus;
import jakarta.persistence.OptimisticLockException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SeatService {

    private final SeatRepository seatRepository;

    public Seat createSeat(SeatInfo info) {

        Seat seat = seatRepository.create(
                Seat.builder()
                        .seatNo(info.getSeatNo())
                        .concertItemId(info.getConcertItemId())
                        .price(info.getPrice())
                        .status(SeatStatus.EMPTY)
                        .build()
        );

        return seat;
    }

    public Seat getSeat(SeatInfo info) throws Exception {

        Seat seat = seatRepository.getById(info.getSeatId())
                .orElseThrow(() -> new Exception("좌석이 존재하지 않습니다."));

        return seat;
    }

    public Seat updateStatus(SeatInfo info) throws Exception {

        try {
            Seat seat = getSeat(info);

            seat.setStatus(info.getStatus());

            seat = seatRepository.update(
                    Seat.builder()
                            .id(info.getSeatId())
                            .seatNo(seat.getSeatNo())
                            .concertItemId(seat.getConcertItemId())
                            .price(seat.getPrice())
                            .status(seat.getStatus())
                            .holdExpiryTime(seat.getHoldExpiryTime())
                            .build()
            );

            return seat;
        } catch (OptimisticLockException e) {
            throw new OptimisticLockException("이미 선점 중인 좌석입니다");
        }
    }
}
