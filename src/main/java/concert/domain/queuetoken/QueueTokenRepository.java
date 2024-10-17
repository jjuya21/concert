package concert.domain.queuetoken;

import java.util.List;
import java.util.UUID;

public interface QueueTokenRepository {

    QueueToken create(QueueToken queueToken);

    QueueToken getByToken(UUID token);

    QueueToken update(QueueToken queueToken);

    List<QueueToken> getAll();

    void expiry(QueueToken queueToken);
}
