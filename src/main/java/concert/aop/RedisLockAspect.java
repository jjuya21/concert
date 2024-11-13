package concert.aop;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Order(1)
@Aspect
@Component
@RequiredArgsConstructor
public class RedisLockAspect {

    private final RedissonClient redissonClient;

    @Around("@annotation(lockManager)")
    public Object aroundRedisLock(ProceedingJoinPoint joinPoint, LockManager lockManager) throws Throwable {
        String lockName = lockManager.key();
        RLock lock = redissonClient.getLock(lockName);

        boolean isLocked = false;
        try {

            isLocked = lock.tryLock(lockManager.waitTime(), lockManager.leaseTime(), TimeUnit.SECONDS);

            if (isLocked) {
                return joinPoint.proceed();
            } else {
                throw new Exception("다른 트랜잭션이 락을 보유하고 있습니다.");
            }
        } finally {
            if (isLocked) {
                lock.unlock();
            }
        }
    }
}
