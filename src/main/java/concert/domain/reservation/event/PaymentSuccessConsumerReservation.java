package concert.application.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import concert.application.payment.event.PaymentSuccessEvent;
import concert.domain.reservation.Reservation;
import concert.domain.reservation.ReservationRepository;
import concert.domain.reservation.ReservationStatus;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentSuccessConsumerReservation {

    private final ReservationRepository reservationRepository;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "PaymentSuccessEvent", groupId = "reservation-consumer-group")
    @Transactional
    public void reservationStatusUpdate(String message) {
        try {
            PaymentSuccessEvent event = convertMessageToEvent(message);

            Optional<Reservation> optionalReservation = reservationRepository.getReservation(event.getReservationId());

            if (optionalReservation.isPresent()) {
                Reservation reservation = optionalReservation.get();
                reservation.setStatus(ReservationStatus.PAYED);

                reservationRepository.update(reservation);

                log.info("예약 상태가 PAYED로 업데이트되었습니다. 예약 ID: {}", event.getReservationId());
            } else {
                log.warn("해당 예약 정보를 찾을 수 없습니다. 예약 ID: {}", event.getReservationId());
            }
        } catch (Exception e) {
            log.error("PaymentSuccessEvent 처리 중 오류가 발생했습니다. 메시지 내용: {}", message, e);
        }
    }

    private PaymentSuccessEvent convertMessageToEvent(String message) {
        try {
            return objectMapper.readValue(message, PaymentSuccessEvent.class);
        } catch (Exception e) {
            log.error("메시지 변환 중 오류 발생: {}", e.getMessage(), e);
            throw new RuntimeException("메시지 변환 실패");
        }
    }
}
