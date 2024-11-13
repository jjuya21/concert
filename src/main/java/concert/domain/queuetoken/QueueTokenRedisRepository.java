package concert.domain.queuetoken;

import java.util.List;
import java.util.Optional;

public interface QueueTokenRedisRepository {

    void enqueue(String token);

    List<String> dequeue(long count);

    List<String> getWaitingQueue();

    Optional<Long> getRank(String token);

    Optional<String> getActiveToken(String token);

    void deleteActiveToken(String token);

    void saveActiveToken(String token, int expiry);
}
