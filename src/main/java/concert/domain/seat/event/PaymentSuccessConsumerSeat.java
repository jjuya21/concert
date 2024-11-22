package concert.domain.seat.event;

import com.fasterxml.jackson.databind.ObjectMapper;
import concert.application.payment.event.PaymentSuccessEvent;
import concert.domain.seat.SeatStatus;
import concert.domain.seat.service.SeatInfo;
import concert.domain.seat.service.SeatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Slf4j
public class PaymentSuccessConsumerSeat {

    private final SeatService seatService;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "PaymentSuccessEvent", groupId = "seat-service")
    @Transactional
    public void updateSeatStatus(String message) {
        try {
            PaymentSuccessEvent event = convertMessageToEvent(message);

            seatService.updateStatus(
                    SeatInfo.builder()
                            .seatId(event.getSeatId())
                            .status(SeatStatus.RESERVED)
                            .build()
            );

            log.info("좌석 상태가 성공적으로 업데이트되었습니다. SeatId: {}", event.getSeatId());
        } catch (Exception e) {
            log.error("좌석 상태 업데이트 중 오류가 발생했습니다: {}", e.getMessage(), e);
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
