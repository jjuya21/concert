package concert.application.getqueueposition;

import concert.domain.queuetoken.QueueToken;
import concert.domain.queuetoken.service.QueueTokenInfo;
import concert.domain.queuetoken.service.QueueTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GetQueuePosition {

    private final QueueTokenService queueTokenService;

    @Transactional
    public QueueToken getQueuePosition(String token) throws Exception {

        QueueToken queuePosition = queueTokenService.getQueuePosition(
                QueueTokenInfo.builder()
                        .token(token)
                        .build()
        );

        return queuePosition;
    }
}
