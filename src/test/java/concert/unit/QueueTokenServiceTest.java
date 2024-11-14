package concert.unit;

import concert.domain.queuetoken.QueueToken;
import concert.domain.queuetoken.QueueTokenRepository;
import concert.domain.queuetoken.service.QueueTokenInfo;
import concert.domain.queuetoken.service.QueueTokenService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class QueueTokenServiceTest {

    private final String DEFAULT_TOKEN = "a8098c1a-f86e-11da-bd1a-00112444be1e";
    @Mock
    private QueueTokenRepository queueTokenRepository;
    @InjectMocks
    private QueueTokenService queueTokenService;

    @DisplayName("token에 맞는 토큰 정보가 조회되어야한다")
    @Test
    void getActiveTokenTest() throws Exception {
        // given
        given(queueTokenRepository.getActiveToken(DEFAULT_TOKEN)).willReturn(Optional.of(DEFAULT_TOKEN));

        // when
        QueueToken result = queueTokenService.getQueueToken(QueueTokenInfo.builder().token(DEFAULT_TOKEN).build());

        // then
        assertThat(result).isNotNull();
        assertThat(result.getToken()).isEqualTo(DEFAULT_TOKEN);
    }

    @DisplayName("토큰으로 대기순번을 조회할 수 있어야한다")
    @Test
    void getQueuePositionTest() throws Exception {
        // given
        given(queueTokenRepository.getRank(DEFAULT_TOKEN)).willReturn(Optional.of(2L));

        // when
        QueueToken result = queueTokenService.getQueuePosition(
                QueueTokenInfo.builder()
                        .token(DEFAULT_TOKEN)
                        .build()
        );

        // then
        assertThat(result).isNotNull();
        assertThat(result.getQueuePosition()).isEqualTo(3L);
    }
}
