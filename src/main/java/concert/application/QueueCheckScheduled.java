package concert.application;

import concert.domain.queuetoken.QueueToken;
import concert.domain.queuetoken.QueueTokenRepository;
import concert.domain.queuetoken.TokenStatus;
import concert.domain.queuetoken.service.QueueTokenRequest;
import concert.domain.queuetoken.service.QueueTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class QueueCheckScheduled {

    private final QueueTokenService queueTokenService;
    private final QueueTokenRepository queueTokenRepository;

    private static long MAX_QUEUE_PASS_CAPACITY = 50L;

    @Transactional
    @Scheduled(initialDelay = 1000, fixedDelay = 5000)
    public void getQueuePosition() {

        List<QueueToken> tokens = queueTokenService.getProcessedTokens();
        long remainingSeats = MAX_QUEUE_PASS_CAPACITY - tokens.size();

        if (remainingSeats > 0L) {

            List<QueueToken> queueTokens = queueTokenRepository.getAll();
            long nextProcessQueuePosition = tokens.stream()
                    .max(Comparator.comparing(QueueToken::getQueuePosition))
                    .map(QueueToken::getQueuePosition)
                    .map(maxPosition -> maxPosition + 1L)
                    .orElse(1L);

            for (QueueToken queueToken : queueTokens) {

                if (nextProcessQueuePosition == queueToken.getQueuePosition() && queueToken.getStatus() == TokenStatus.WAIT) {
                    queueTokenService.updateStatus(
                            QueueTokenRequest.builder()
                                    .token(queueToken.getToken())
                                    .status(TokenStatus.PROCESSED)
                                    .build()
                    );
                }
            }
        }
    }
}
