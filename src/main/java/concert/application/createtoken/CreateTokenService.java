package concert.application.createtoken;

import concert.domain.queuetoken.*;
import concert.domain.queuetoken.service.QueueTokenRequest;
import concert.domain.queuetoken.service.QueueTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CreateTokenService {

    private final QueueTokenRepository queueTokenRepository;
    private final QueueTokenService queueTokenService;

    @Transactional
    public QueueToken createToken() {

         QueueToken queueToken = queueTokenRepository.create(
                QueueToken.builder()
                        .status(TokenStatus.WAIT)
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
