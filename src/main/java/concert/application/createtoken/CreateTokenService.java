package concert.application.createtoken;

import concert.domain.queuetoken.*;
import concert.domain.queuetoken.service.QueueTokenRequest;
import concert.domain.queuetoken.service.QueueTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CreateTokenService {

    private final QueueTokenRepository queueTokenRepository;
    private final QueueTokenService queueTokenService;

    @Transactional
    public QueueToken createToken() {

        List<QueueToken> tokens = queueTokenService.getProcessedTokens();
        long remainingSeats = 50L - tokens.size();
        TokenStatus status = TokenStatus.WAIT;
        if (remainingSeats > 0L) {
            status = TokenStatus.PROCESSED;
        }
        QueueToken queueToken = queueTokenRepository.create(
                QueueToken.builder()
                        .queuePosition(0L)
                        .status(status)
                        .expiryTime(LocalDateTime.now().plusMinutes(30))
                        .build()
        );

        queueToken = queueTokenService.createQueuePosition(
                QueueTokenRequest.builder()
                        .token(queueToken.getToken())
                        .build()
        );

        return queueToken;
    }
}
