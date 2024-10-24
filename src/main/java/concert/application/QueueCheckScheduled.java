package concert.application;

import concert.domain.queuetoken.QueueToken;
import concert.domain.queuetoken.QueueTokenRepository;
import concert.domain.queuetoken.TokenStatus;
import concert.domain.queuetoken.service.QueueTokenInfo;
import concert.domain.queuetoken.service.QueueTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;


@Component
@RequiredArgsConstructor
public class QueueCheckScheduled {

    private final QueueTokenService queueTokenService;
    private final QueueTokenRepository queueTokenRepository;
    @Value("${custom.queue.max-capacity}")
    private long maxQueuePassCapacity;

    @Scheduled(initialDelay = 1000, fixedDelay = 5000)
    public void passNextInQueue() {

        List<QueueToken> tokens = queueTokenService.getProcessedTokens();
        long remainingSeats = maxQueuePassCapacity - tokens.size();

        if (remainingSeats > 0L) {

            long nextProcessQueuePosition = tokens.stream()
                    .max(Comparator.comparing(QueueToken::getQueuePosition))
                    .map(QueueToken::getQueuePosition)
                    .map(maxPosition -> maxPosition + 1L)
                    .orElse(1L);

            QueueToken queueToken = queueTokenRepository.getByQueuePosition(nextProcessQueuePosition);

            queueTokenService.updateStatus(
                    QueueTokenInfo.builder()
                            .token(queueToken.getToken())
                            .status(TokenStatus.PROCESSED)
                            .build()
            );
        }
    }
}
