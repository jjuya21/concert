package concert.intergration;

import concert.application.createtoken.CreateTokenService;
import concert.application.getqueueposition.GetQueuePositionService;
import concert.domain.queuetoken.QueueToken;
import concert.domain.queuetoken.QueueTokenRepository;
import concert.domain.queuetoken.service.QueueTokenRequest;
import concert.domain.queuetoken.service.QueueTokenService;
import jakarta.persistence.Column;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@SpringBootTest
public class GetQueuePositionServiceTest {

    @Autowired
    private CreateTokenService createTokenService;

    @Autowired
    private QueueTokenService queueTokenService;

    @Autowired
    private QueueTokenRepository queueTokenRepository;

    @Autowired
    private GetQueuePositionService getQueuePositionService;

    @Test
    @Transactional
    public void getQueuePosition() throws InterruptedException {
        // given
        UUID token;

        // 55개의 토큰 생성
        for (int i = 0; i < 55; i++) {
            createTokenService.createToken();
        }

        // 마지막 토큰 생성 후 가져오기
        token = createTokenService.createToken().getToken();

        // when
        long queuePosition = getQueuePositionService.getQueuePosition(token);

        // then
        Assertions.assertThat(queuePosition).isEqualTo(queueTokenRepository.getAll().size() - 50);  // 값 비교는 isEqualTo로 변경
    }
}
