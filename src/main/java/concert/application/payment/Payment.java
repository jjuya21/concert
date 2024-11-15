package concert.application.payment;

import concert.application.event.ApplicationEventPublisher;
import concert.domain.balance.service.BalanceInfo;
import concert.domain.balance.service.BalanceService;
import concert.domain.event.DomainEventPublisher;
import concert.domain.payment.PaymentRepository;
import concert.domain.payment.PaymentStatus;
import concert.domain.queuetoken.QueueTokenRepository;
import concert.domain.reservation.Reservation;
import concert.domain.reservation.ReservationRepository;
import concert.domain.reservation.event.ReservationStatusUpdateEvent;
import concert.domain.seat.event.SeatStatusUpdateEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class Payment {

    private final ReservationRepository reservationRepository;
    private final PaymentRepository paymentRepository;
    private final QueueTokenRepository queueTokenRepository;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final DomainEventPublisher domainEventPublisher;
    private final BalanceService balanceService;

    @Transactional
    public concert.domain.payment.Payment payment(PaymentCommand command) throws Exception {

        concert.domain.payment.Payment payment = paymentRepository.create(
                concert.domain.payment.Payment.builder()
                        .userId(command.getUserId())
                        .reservationId(command.getReservationId())
                        .status(PaymentStatus.PAYED)
                        .createdAt(LocalDateTime.now())
                        .build()
        );

        Reservation reservation = reservationRepository.getReservation(command.getReservationId())
                .orElseThrow(() -> new Exception("존재하지 않는 예매정보입니다"));

        balanceService.useBalance(
                BalanceInfo.builder()
                        .userId(command.getUserId())
                        .amount(reservation.getPrice())
                        .build()
        );

        applicationEventPublisher.publish(new PaymentSuccessEvent(
                command.getReservationId(), reservation.getSeatId(), command.getToken())
        );

        return payment;
    }

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    void handlePaymentSuccess(PaymentSuccessEvent event) {

        domainEventPublisher.publish(
                new ReservationStatusUpdateEvent(event.getReservationId())
        );
        domainEventPublisher.publish(
                new SeatStatusUpdateEvent(event.getSeatId())
        );
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    void expireQueueToken(PaymentSuccessEvent event) {

        queueTokenRepository.expireActiveToken(event.getToken());
    }
}