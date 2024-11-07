package concert.application.createtoken;

import concert.aop.RedisLock;
import concert.domain.queuetoken.QueueToken;
import concert.domain.queuetoken.QueueTokenRepository;
import concert.domain.queuetoken.TokenStatus;
import concert.domain.queuetoken.service.QueueTokenInfo;
import concert.domain.queuetoken.service.QueueTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CreateToken {

    private final QueueTokenRepository queueTokenRepository;
    private final QueueTokenService queueTokenService;
    @Value("${custom.queue.max-capacity}")
    private long maxQueuePassCapacity;

    @RedisLock(key = "token")
    @Transactional
    public QueueToken createToken() throws Exception {

        List<QueueToken> tokens = queueTokenService.getProcessedTokens();
        long remainingSeats = maxQueuePassCapacity - tokens.size();
        TokenStatus status = TokenStatus.WAIT;
        if (remainingSeats > 0L) {
            status = TokenStatus.PROCESSED;
        }

        String token = UUID.randomUUID().toString();

        QueueToken queueToken = queueTokenRepository.create(
                QueueToken.builder()
                        .token(token)
                        .queuePosition(0L)
                        .status(status)
                        .expiryTime(LocalDateTime.now().plusMinutes(30))
                        .build()
        );

        queueToken = queueTokenService.createQueuePosition(
                QueueTokenInfo.builder()
                        .token(queueToken.getToken())
                        .build()
        );

        return queueToken;
    }
}
