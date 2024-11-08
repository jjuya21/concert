package concert.interfaces.common.interceptor;

import concert.domain.queuetoken.QueueTokenRedisRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Component
@RequiredArgsConstructor
public class ValidationTokenInterceptor implements HandlerInterceptor {

    private final QueueTokenRedisRepository queueTokenRedisRepository;

    @Override
    @Transactional
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }

        String token = authorizationHeader.substring(7);

        log.info("Extracted Token: {}", token);

        queueTokenRedisRepository.getActiveToken(token)
                .orElseThrow(() -> new Exception("사용 불가한 토큰입니다"));

        return true;
    }
}
