package concert.domain.queuetoken;

import java.util.List;
import java.util.Optional;

public interface QueueTokenRepository {

    QueueToken create(QueueToken queueToken);

    Optional<QueueToken> getByToken(String token);

    Optional<QueueToken> getByQueuePosition(long queuePosition);

    QueueToken update(QueueToken queueToken);

    List<QueueToken> getAll();

    void expiry(QueueToken queueToken);
}
