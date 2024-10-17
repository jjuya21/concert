package concert.application;

import concert.domain.queuetoken.QueueToken;
import concert.domain.queuetoken.QueueTokenRepository;
import concert.domain.queuetoken.TokenStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class TokenCheck {

    private final QueueTokenRepository queueTokenRepository;

    public void tokenCheck(UUID token) {

        TokenStatus status = queueTokenRepository.getByToken(token).getStatus();

        if (status == TokenStatus.WAIT) {

            throw new RuntimeException("대기열 대기중");
        }
    }
}
