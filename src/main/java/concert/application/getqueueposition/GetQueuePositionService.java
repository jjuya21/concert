package concert.application.getqueueposition;

import concert.domain.queuetoken.QueueToken;
import concert.domain.queuetoken.QueueTokenRepository;
import concert.domain.queuetoken.TokenStatus;
import concert.domain.queuetoken.service.QueueTokenRequest;
import concert.domain.queuetoken.service.QueueTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GetQueuePositionService {

    private final QueueTokenService queueTokenService;
    private final QueueTokenRepository queueTokenRepository;

    @Transactional
    public long getQueuePosition(UUID token) {

        QueueToken queueToken = queueTokenService.getQueueToken(
                QueueTokenRequest.builder()
                        .token(token)
                        .build()
        );

        long lastProcessedQueuePosition = queueTokenRepository.getAll().stream()
                .filter(qToken -> qToken.getStatus() == TokenStatus.PROCESSED)
                .max(Comparator.comparing(QueueToken::getQueuePosition))
                .map(QueueToken::getQueuePosition)
                .orElse(1L);

        long queuePosition = queueToken.getQueuePosition() - lastProcessedQueuePosition;

        return queuePosition;
    }
}
