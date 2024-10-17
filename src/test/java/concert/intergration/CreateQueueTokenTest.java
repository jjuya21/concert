package concert.intergration;

import concert.application.createtoken.CreateTokenService;
import concert.domain.queuetoken.QueueToken;
import concert.domain.queuetoken.QueueTokenRepository;
import concert.domain.queuetoken.service.QueueTokenRequest;
import concert.domain.queuetoken.service.QueueTokenService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@SpringBootTest
public class CreateQueueTokenTest {

    @Autowired
    private CreateTokenService createTokenService;

    @Autowired
    private QueueTokenService queueTokenService;

    @Test
    @Transactional
    public void createQueueToken() {
        // given
        UUID token = createTokenService.createToken().getToken();

        // when
        QueueToken queueToken = queueTokenService.getQueueToken(
                QueueTokenRequest.builder()
                        .token(token)
                        .build()
        );

        // then
        Assertions.assertThat(queueToken).isNotNull();
    }
}
