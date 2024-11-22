package concert.domain.outbox;

import java.util.List;

public interface OutboxRepository {

    Outbox save(Outbox outbox);

    List<Outbox> findAll();
}
