package concert.domain.queuetoken.service;

import concert.domain.queuetoken.QueueToken;
import concert.domain.queuetoken.QueueTokenRepository;
import concert.domain.queuetoken.TokenStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class QueueTokenService {

    private final QueueTokenRepository repository;

    public QueueToken getQueueToken(QueueTokenRequest request) {

        QueueToken queueToken = repository.getByToken(request.getToken());

        return queueToken;
    }

    @Transactional
    public QueueToken updateStatus(QueueTokenRequest request) {

        QueueToken queueToken = getQueueToken(request);
        queueToken.setStatus(request.getStatus());

        queueToken = repository.update(QueueToken.builder()
                .token(queueToken.getToken())
                .status(queueToken.getStatus())
                .queuePosition(queueToken.getQueuePosition())
                .expiryTime(queueToken.getExpiryTime())
                .build()
        );

        return queueToken;
    }

    public QueueToken updateExpiryTime(QueueTokenRequest request) {

        QueueToken queueToken = getQueueToken(request);
        queueToken.setExpiryTime(request.getExpiryTime());

        queueToken = repository.update(QueueToken.builder()
                .token(queueToken.getToken())
                .queuePosition(queueToken.getQueuePosition())
                .status(queueToken.getStatus())
                .expiryTime(queueToken.getExpiryTime())
                .build()
        );

        return queueToken;
    }

    public QueueToken createQueuePosition(QueueTokenRequest request) {

        QueueToken queueToken = getQueueToken(request);

        long queuePosition = repository.getAll()
                .stream()
                .filter(token -> token.getStatus() == TokenStatus.WAIT)
                .max(Comparator.comparing(QueueToken::getQueuePosition))
                .map(QueueToken::getQueuePosition)
                .map(maxPosition -> maxPosition + 1L)
                .orElse(1L);

        queueToken = repository.update(QueueToken.builder()
                .token(queueToken.getToken())
                .queuePosition(queuePosition)
                .status(queueToken.getStatus())
                .expiryTime(queueToken.getExpiryTime())
                .build()
        );

        return queueToken;
    }

    public List<QueueToken> getProcessedTokens() {

        List<QueueToken> tokens = repository.getAll().stream()
                .filter(token -> token.getStatus() == TokenStatus.PROCESSED).toList();

        return tokens;
    }
}
