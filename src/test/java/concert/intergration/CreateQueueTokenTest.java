package concert.intergration;

import concert.application.createtoken.CreateTokenService;
import concert.domain.queuetoken.QueueToken;
import concert.domain.queuetoken.service.QueueTokenInfo;
import concert.domain.queuetoken.service.QueueTokenService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class CreateQueueTokenTest {

    @Autowired
    private CreateTokenService createTokenService;

    @Autowired
    private QueueTokenService queueTokenService;

    @DisplayName("토큰을 생성하면 성공적으로 생성되어야한다")
    @Test
    @Transactional
    public void createQueueTokenTest() throws Exception {
        // given
        String token = createTokenService.createToken().getToken();

        // when
        QueueToken queueToken = queueTokenService.getQueuePosition(
                QueueTokenInfo.builder()
                        .token(token)
                        .build()
        );

        // then
        Assertions.assertThat(queueToken).isNotNull();
    }
}
