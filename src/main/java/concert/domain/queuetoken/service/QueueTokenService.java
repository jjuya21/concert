package concert.domain.queuetoken.service;

import concert.domain.queuetoken.QueueToken;
import concert.domain.queuetoken.QueueTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QueueTokenService {

    private final QueueTokenRepository repository;

    public QueueToken getQueueToken(QueueTokenInfo info) throws Exception {

        String token = repository.getActiveToken(info.getToken())
                .orElseThrow(() -> new Exception("토큰이 존재하지 않습니다."));
        QueueToken queueToken = QueueToken.builder()
                .token(token)
                .build();

        return queueToken;
    }

    public QueueToken getQueuePosition(QueueTokenInfo info) throws Exception {
        long queuePosition = repository.getRank(info.getToken())
                .orElseThrow(() -> new Exception("대기열에 존재하지 않는 토큰입니다"));

        queuePosition++;

        QueueToken queueToken = QueueToken.builder()
                .token(info.getToken())
                .queuePosition(queuePosition)
                .build();

        return queueToken;
    }
}
