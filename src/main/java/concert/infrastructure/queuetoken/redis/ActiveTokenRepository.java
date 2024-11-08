package concert.infrastructure.queuetoken.redis;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Component
public class ActiveTokenRepository {

    private final RedisTemplate<String, String> redisTemplate;

    public ActiveTokenRepository(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public Optional<String> getToken(String token) {
        return Optional.ofNullable(
                redisTemplate.opsForValue().get(token)
        );
    }

    public void deleteToken(String token) {
        redisTemplate.delete(token);
    }

    public void saveToken(String token, int expiry) {
        redisTemplate.opsForValue().set(token, token);
        redisTemplate.expire(token, expiry, TimeUnit.MINUTES);
    }
}
