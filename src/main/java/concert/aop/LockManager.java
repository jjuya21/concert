package concert.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value = ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LockManager {
    String key(); // 락을 걸 때 사용할 키 값

    long waitTime() default 3; // 락 대기 시간 (초)

    long leaseTime() default 5; // 락 유지 시간 (초)
}