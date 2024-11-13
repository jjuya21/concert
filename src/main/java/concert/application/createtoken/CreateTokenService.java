package concert.application.createtoken;

import concert.domain.queuetoken.QueueToken;
import concert.domain.queuetoken.QueueTokenRedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CreateTokenService {

    private final QueueTokenRedisRepository queueTokenRedisRepository;

    @Transactional
    public QueueToken createToken() {

        String token = UUID.randomUUID().toString();

        queueTokenRedisRepository.enqueue(token);

        QueueToken queueToken = QueueToken.builder()
                .token(token)
                .build();

        return queueToken;
    }
}
