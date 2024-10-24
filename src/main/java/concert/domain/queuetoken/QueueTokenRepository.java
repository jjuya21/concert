package concert.domain.queuetoken;

import java.util.List;

public interface QueueTokenRepository {

    QueueToken create(QueueToken queueToken);

    QueueToken getByToken(String token);

    QueueToken getByQueuePosition(long queuePosition);

    QueueToken update(QueueToken queueToken);

    List<QueueToken> getAll();

    void expiry(QueueToken queueToken);
}
