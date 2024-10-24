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
    public long getQueuePosition(String token) {

        QueueToken queueToken = queueTokenService.getQueueToken(
                QueueTokenInfo.builder()
                        .token(token)
                        .build()
        );

        long queuePosition = queueToken.getQueuePosition();

        System.out.println(queuePosition);

        return queuePosition;
    }
}
