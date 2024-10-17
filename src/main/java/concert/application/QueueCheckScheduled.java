package concert.application;

import concert.domain.queuetoken.QueueToken;
import concert.domain.queuetoken.QueueTokenRepository;
import concert.domain.queuetoken.TokenStatus;
import concert.domain.queuetoken.service.QueueTokenRequest;
import concert.domain.queuetoken.service.QueueTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

@RequiredArgsConstructor
public class QueueCheckScheduled {

    private final QueueTokenService queueTokenService;
    private final QueueTokenRepository queueTokenRepository;

    private static long MAX_QUEUE_PASS_CAPACITY = 50L;

    @Scheduled(initialDelay = 1000, fixedDelay = 10000)
    public void getQueuePosition() {

        Stream<QueueToken> tokens = queueTokenService.getProcessedTokens().stream();
        long remainingSeats = MAX_QUEUE_PASS_CAPACITY - tokens.count();

        if (remainingSeats <= MAX_QUEUE_PASS_CAPACITY) {

            List<QueueToken> queueTokens = queueTokenRepository.getAll();
            long nextProcessQueuePosition = tokens
                    .max(Comparator.comparing(QueueToken::getQueuePosition))
                    .map(QueueToken::getQueuePosition)
                    .map(maxPosition -> maxPosition + 1L)
                    .orElse(1L);

            for (QueueToken queueToken : queueTokens) {

                if (nextProcessQueuePosition == queueToken.getQueuePosition()) {

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
