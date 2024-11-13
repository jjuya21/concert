package concert.infrastructure.queuetoken.redis;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class WaitingQueueRepository {

    private final RedisTemplate<String, String> redisTemplate;

    public WaitingQueueRepository(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void enqueue(String token) {
        double score = System.currentTimeMillis();
        redisTemplate.opsForZSet().add("WAITING_QUEUE", token, score);
    }

    public List<String> dequeue(long count) {
        Set<ZSetOperations.TypedTuple<String>> tokens = redisTemplate.opsForZSet().popMin("WAITING_QUEUE", count);

        return Optional.ofNullable(tokens)
                .map(t -> t.stream()
                        .map(ZSetOperations.TypedTuple::getValue)
                        .collect(Collectors.toList()))
                .orElse(Collections.emptyList());
    }

    public List<String> getWaitingQueue() {
        Set<String> queue = redisTemplate.opsForZSet().range("WAITING_QUEUE", 0, -1);
        return queue == null ? new ArrayList<>() : new ArrayList<>(queue);
    }

    public Optional<Long> getRank(String token) {
        return Optional.ofNullable(redisTemplate.opsForZSet().rank("WAITING_QUEUE", token));
    }
}

