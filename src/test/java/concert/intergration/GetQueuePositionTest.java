package concert.intergration;

import concert.application.createtoken.CreateTokenService;
import concert.application.getqueueposition.GetQueuePosition;
import concert.domain.queuetoken.QueueToken;
import concert.domain.queuetoken.QueueTokenRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class GetQueuePositionTest {

    @Autowired
    private CreateTokenService createTokenService;

    @Autowired
    private QueueTokenRepository queueTokenRepository;

    @Autowired
    private GetQueuePosition getQueuePosition;

    @DisplayName("대기 순번을 조회하면 현재 토큰의 순번을 반환해야한다.")
    @Test
    @Transactional
    public void getQueuePositionTest() throws Exception {
        // given
        String token = createTokenService.createToken().getToken();

        // when
        QueueToken queueToken = getQueuePosition.getQueuePosition(token);

        // then
        long queuePosition = queueTokenRepository.getWaitingQueue().size();

        Assertions.assertThat(queueToken).isNotNull();
        Assertions.assertThat(queueToken.getToken()).isEqualTo(token);
        Assertions.assertThat(queueToken.getQueuePosition()).isEqualTo(queuePosition);
    }
}
