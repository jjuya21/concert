package concert.application.scheduler;

import concert.domain.outbox.Outbox;
import concert.domain.outbox.OutboxRepository;
import concert.domain.outbox.OutboxStatus;
import concert.domain.outbox.service.OutboxInfo;
import concert.domain.outbox.service.OutboxService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OutboxScheduler {

    private final OutboxService outboxService;
    private final OutboxRepository outboxRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Scheduled(fixedRate = 500)
    public void processOutbox() {
        outboxService.findByStatus(OutboxInfo.builder().status(OutboxStatus.CREATED).build())
                .forEach(this::processOutboxMessage);
    }

    private void processOutboxMessage(Outbox outbox) {
        try {
            kafkaTemplate.send(outbox.getEvent(), outbox.getPayload());
            outbox.markPublished();
        } catch (Exception e) {
            outbox.markFailed();
        }
        outboxRepository.save(outbox);
    }
}
