package concert.application;

import concert.domain.queuetoken.QueueToken;
import concert.domain.queuetoken.service.QueueTokenInfo;
import concert.domain.queuetoken.service.QueueTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TokenCheck {

    private final QueueTokenService queueTokenService;

    public void tokenCheck(String token) {

        QueueToken queueToken = queueTokenService.getQueueToken(
                QueueTokenInfo.builder()
                        .token(token)
                        .build()
        );

        if (queueToken.checkIsWait()) {

            throw new RuntimeException("대기열 대기중");
        }
    }
}
