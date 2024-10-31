package concert.interceptor;

import concert.domain.queuetoken.QueueToken;
import concert.domain.queuetoken.service.QueueTokenInfo;
import concert.domain.queuetoken.service.QueueTokenService;
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

    private final QueueTokenService queueTokenService;

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

        QueueToken queueToken = queueTokenService.getQueueToken(
                QueueTokenInfo.builder()
                        .token(token)
                        .build()
        );

        if (queueToken.checkIsWait()) {

            throw new Exception("대기열 대기중");
        }

        return true;
    }
}
