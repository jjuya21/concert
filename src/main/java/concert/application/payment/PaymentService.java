package concert.application.payment;

import concert.application.TokenCheck;
import concert.domain.balance.service.BalanceRequest;
import concert.domain.balance.service.BalanceService;
import concert.domain.payment.Payment;
import concert.domain.payment.PaymentRepository;
import concert.domain.payment.PaymentStatus;
import concert.domain.queuetoken.QueueTokenRepository;
import concert.domain.queuetoken.service.QueueTokenRequest;
import concert.domain.queuetoken.service.QueueTokenService;
import concert.domain.reservation.Reservation;
import concert.domain.reservation.ReservationRepository;
import concert.domain.reservation.ReservationStatus;
import concert.domain.seat.SeatStatus;
import concert.domain.seat.service.SeatRequest;
import concert.domain.seat.service.SeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final ReservationRepository reservationRepository;
    private final PaymentRepository paymentRepository;
    private final BalanceService balanceService;
    private final QueueTokenRepository queueTokenRepository;
    private final QueueTokenService queueTokenService;
    private final SeatService seatService;
    private final TokenCheck tokenCheck;

    @Transactional
    public Payment payment(PaymentCommand command) {

        tokenCheck.tokenCheck(command.getToken());

        Reservation reservation = reservationRepository.getReservation(command.getReservationId());

        balanceService.useBalance(
                BalanceRequest.builder()
                        .userId(command.getUserId())
                        .amount(reservation.getPrice())
                        .build()
        );

        reservation.setStatus(ReservationStatus.PAYED);
        reservationRepository.updateReservation(reservation);

        seatService.updateStatus(
                SeatRequest.builder()
                        .seatId(reservation.getSeatId())
                        .status(SeatStatus.RESERVED)
                        .build()
        );

        Payment payment = paymentRepository.create(
                Payment.builder()
                        .userId(command.getUserId())
                        .reservationId(command.getReservationId())
                        .status(PaymentStatus.PAYED)
                        .createdAt(LocalDateTime.now())
                        .build()
        );

        queueTokenRepository.expiry(queueTokenService.getQueueToken(
                QueueTokenRequest.builder()
                        .token(command.getToken())
                        .build()
        ));

        return payment;
    }
}
