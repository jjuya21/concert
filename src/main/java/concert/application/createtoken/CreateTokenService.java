package concert.application.createtoken;

import concert.domain.queuetoken.QueueToken;
import concert.domain.queuetoken.QueueTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CreateTokenService {

    private final QueueTokenRepository queueTokenRepository;

    @Transactional
    public QueueToken createToken() {

        String token = UUID.randomUUID().toString();

        queueTokenRepository.enqueue(token);

        QueueToken queueToken = QueueToken.builder()
                .token(token)
                .build();

        return queueToken;
    }
}
