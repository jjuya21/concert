package concert.intergration;

import concert.application.createtoken.CreateToken;
import concert.application.getqueueposition.GetQueuePosition;
import concert.domain.queuetoken.QueueTokenRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class GetQueuePositionTest {

    @Value("${custom.queue.max-capacity}")
    private long maxQueuePassCapacity;

    @Autowired
    private CreateToken createToken;

    @Autowired
    private QueueTokenRepository queueTokenRepository;

    @Autowired
    private GetQueuePosition getQueuePosition;

    @DisplayName("대기 순번을 조회하면 현재 자신의 순번을 반환해야한다.")
    @Test
    @Transactional
    public void getQueuePositionTest() throws Exception {
        // given
        String token;

        // 55개의 토큰 생성
        for (int i = 0; i < 55; i++) {
            createToken.createToken();
        }

        // 마지막 토큰 생성 후 가져오기
        token = createToken.createToken().getToken();

        // when
        long queuePosition = getQueuePosition.getQueuePosition(token);

        // then
        Assertions.assertThat(queuePosition).isEqualTo(queueTokenRepository.getAll().size() - maxQueuePassCapacity);  // 값 비교는 isEqualTo로 변경
    }
}
