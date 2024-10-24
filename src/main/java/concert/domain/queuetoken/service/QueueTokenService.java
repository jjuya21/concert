package concert.domain.queuetoken.service;

import concert.domain.queuetoken.QueueToken;
import concert.domain.queuetoken.QueueTokenRepository;
import concert.domain.queuetoken.TokenStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QueueTokenService {

    private final QueueTokenRepository repository;

    public QueueToken getQueueToken(QueueTokenInfo info) {

        QueueToken queueToken = repository.getByToken(info.getToken());

        return queueToken;
    }

    public QueueToken updateStatus(QueueTokenInfo info) {

        QueueToken queueToken = getQueueToken(info);
        queueToken.setStatus(info.getStatus());

        queueToken = repository.update(QueueToken.builder()
                .id(queueToken.getId())
                .token(queueToken.getToken())
                .status(queueToken.getStatus())
                .queuePosition(queueToken.getQueuePosition())
                .expiryTime(queueToken.getExpiryTime())
                .build()
        );

        return queueToken;
    }

    public QueueToken updateExpiryTime(QueueTokenInfo info) {

        QueueToken queueToken = getQueueToken(info);
        queueToken.setExpiryTime(info.getExpiryTime());

        queueToken = repository.update(QueueToken.builder()
                .id(queueToken.getId())
                .token(queueToken.getToken())
                .queuePosition(queueToken.getQueuePosition())
                .status(queueToken.getStatus())
                .expiryTime(queueToken.getExpiryTime())
                .build()
        );

        return queueToken;
    }

    public QueueToken createQueuePosition(QueueTokenInfo info) {

        QueueToken queueToken = getQueueToken(info);

        long queuePosition = repository.getAll()
                .stream()
                .filter(token -> token.checkIsWait())
                .max(Comparator.comparing(QueueToken::getQueuePosition))
                .map(QueueToken::getQueuePosition)
                .map(maxPosition -> maxPosition + 1L)
                .orElse(1L);

        queueToken = repository.update(QueueToken.builder()
                .id(queueToken.getId())
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
