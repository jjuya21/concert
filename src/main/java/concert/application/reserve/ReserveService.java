package concert.application.reserve;

import concert.application.TokenCheck;
import concert.domain.balance.service.BalanceService;
import concert.domain.payment.PaymentRepository;
import concert.domain.reservation.Reservation;
import concert.domain.reservation.ReservationRepository;
import concert.domain.reservation.ReservationStatus;
import concert.domain.seat.Seat;
import concert.domain.seat.SeatRepository;
import concert.domain.seat.SeatStatus;
import concert.domain.seat.service.SeatRequest;
import concert.domain.seat.service.SeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReserveService {

    private final ReservationRepository reservationRepository;
    private final TokenCheck tokenCheck;
    private final SeatService seatService;

    @Transactional
    public Reservation reserve(ReserveCommand command) {

        tokenCheck.tokenCheck(command.getToken());

        long price = seatService.updateStatus(
                SeatRequest.builder()
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
