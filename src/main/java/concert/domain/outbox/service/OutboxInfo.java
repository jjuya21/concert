package concert.domain.outbox.service;

import concert.domain.event.Event;
import concert.domain.outbox.OutboxStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OutboxInfo {

    private String event;
    private Event payload;
    private OutboxStatus status;
}
