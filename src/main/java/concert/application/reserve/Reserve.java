package concert.application.reserve;

import concert.application.TokenCheck;
import concert.domain.reservation.Reservation;
import concert.domain.reservation.ReservationRepository;
import concert.domain.reservation.ReservationStatus;
import concert.domain.seat.SeatStatus;
import concert.domain.seat.service.SeatInfo;
import concert.domain.seat.service.SeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class Reserve {

    private final ReservationRepository reservationRepository;
    private final TokenCheck tokenCheck;
    private final SeatService seatService;

    @Transactional
    public Reservation reserve(ReserveCommand command) {

        tokenCheck.tokenCheck(command.getToken());

        SeatStatus seatStatus = seatService.getSeat(
                SeatInfo.builder()
                        .seatId(command.getSeatId())
                        .build()
        ).getStatus();

        if (seatStatus == SeatStatus.HOLDING || seatStatus == SeatStatus.RESERVED) {
            throw new RuntimeException("이미 선점된 좌석입니다.");
        }

        long price = seatService.updateStatus(
                SeatInfo.builder()
                        .seatId(command.getSeatId())
                        .status(SeatStatus.HOLDING)
                        .build()
        ).getPrice();

        Reservation reservation = reservationRepository.reserve(
                Reservation.builder()
                        .userId(command.getUserId())
                        .seatId(command.getSeatId())
                        .price(price)
                        .status(ReservationStatus.WAITING_PAYMENT)
                        .build()
        );

        return reservation;
    }
}
