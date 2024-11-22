package concert.domain.outbox.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import concert.domain.outbox.Outbox;
import concert.domain.outbox.OutboxRepository;
import concert.domain.outbox.OutboxStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class OutboxService {

    private final OutboxRepository outboxRepository;
    private final ObjectMapper objectMapper;

    public void createOutbox(OutboxInfo info) throws JsonProcessingException {

        String eventJson = objectMapper.writeValueAsString(info.getPayload());

        Outbox outbox = Outbox.builder()
                .event(info.getEvent())
                .payload(eventJson)
                .status(OutboxStatus.CREATED)
                .createdAt(LocalDateTime.now())
                .retryCount(0)
                .build();

        outboxRepository.save(outbox);
    }

    public List<Outbox> findByStatus(OutboxInfo info) {

        return outboxRepository.findAll()
                .stream()
                .filter(outbox -> outbox.getStatus().equals(info.getStatus()))
                .collect(Collectors.toList());
    }
}
