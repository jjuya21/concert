package concert.unit;

import concert.domain.queuetoken.QueueToken;
import concert.domain.queuetoken.QueueTokenRepository;
import concert.domain.queuetoken.TokenStatus;
import concert.domain.queuetoken.service.QueueTokenInfo;
import concert.domain.queuetoken.service.QueueTokenService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class QueueTokenServiceTest {

    @Value("${custom.token}")
    private String DEFAULT_TOKEN;
    private final QueueToken DEFAULT_SAVED_TOKEN = QueueToken.builder()
            .id(1L)
            .token(DEFAULT_TOKEN)
            .status(TokenStatus.WAIT)
            .build();
    @Mock
    private QueueTokenRepository queueTokenRepository;
    @InjectMocks
    private QueueTokenService queueTokenService;

    @DisplayName("token에 맞는 토큰 정보가 조회되어야한다")
    @Test
    void getQueueTokenTest() throws Exception {
        // given
        given(queueTokenRepository.getByToken(DEFAULT_TOKEN)).willReturn(Optional.ofNullable(DEFAULT_SAVED_TOKEN));

        // when
        QueueToken result = queueTokenService.getQueueToken(QueueTokenInfo.builder().token(DEFAULT_TOKEN).build());

        // then
        assertThat(result).isNotNull();
        assertThat(result.getToken()).isEqualTo(DEFAULT_TOKEN);
    }

    @DisplayName("토큰의 상태값을 원하는 값으로 수정할 수 있어야한다")
    @Test
    void updateStatusTest() throws Exception {
        // given
        QueueToken updateQueueToken = QueueToken.builder()
                .id(1L)
                .token(DEFAULT_TOKEN)
                .status(TokenStatus.PROCESSED)
                .build();

        QueueTokenInfo info = QueueTokenInfo.builder().token(DEFAULT_TOKEN).build();

        given(queueTokenRepository.getByToken(DEFAULT_TOKEN)).willReturn(Optional.ofNullable(DEFAULT_SAVED_TOKEN));
        given(queueTokenRepository.update(any(QueueToken.class))).willReturn(updateQueueToken);

        // when
        QueueToken result = queueTokenService.updateStatus(
                QueueTokenInfo.builder()
                        .token(DEFAULT_TOKEN)
                        .status(TokenStatus.PROCESSED)
                        .build()
        );

        // then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(updateQueueToken.getId());
        assertThat(result.getStatus()).isEqualTo(TokenStatus.PROCESSED);
    }


    @DisplayName("토큰의 만료시간을 원하는 시간으로 수정이 성공되야한다.")
    @Test
    void updateExpiryTimeTest() throws Exception {
        // given
        LocalDateTime updateExpiryTime = LocalDateTime.now().plusMinutes(30);

        QueueToken updateQueueToken = QueueToken.builder()
                .id(1L)
                .token(DEFAULT_TOKEN)
                .status(TokenStatus.WAIT)
                .expiryTime(updateExpiryTime)
                .build();

        QueueTokenInfo info = QueueTokenInfo.builder().token(DEFAULT_TOKEN).build();

        given(queueTokenRepository.getByToken(DEFAULT_TOKEN)).willReturn(Optional.ofNullable(DEFAULT_SAVED_TOKEN));
        given(queueTokenRepository.update(any(QueueToken.class))).willReturn(updateQueueToken);

        // when
        QueueToken result = queueTokenService.updateExpiryTime(
                QueueTokenInfo.builder()
                        .token(DEFAULT_TOKEN)
                        .status(TokenStatus.WAIT)
                        .expiryTime(updateExpiryTime)
                        .build()
        );

        // then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(updateQueueToken.getId());
        assertThat(result.getExpiryTime()).isEqualTo(updateExpiryTime);
    }

    @DisplayName("토큰값과 대기 순번이 옳게 생성이 되어야한다.")
    @Test
    void createQueuePositionTest() throws Exception {
        // given
        QueueToken updateQueueToken = QueueToken.builder()
                .id(1L)
                .token(DEFAULT_TOKEN)
                .queuePosition(1L)
                .build();

        List<QueueToken> savedQueueTokens = List.of(DEFAULT_SAVED_TOKEN);

        QueueTokenInfo info = QueueTokenInfo.builder()
                .token(DEFAULT_TOKEN)
                .build();

        given(queueTokenRepository.getByToken(DEFAULT_TOKEN)).willReturn(Optional.of(DEFAULT_SAVED_TOKEN));
        given(queueTokenRepository.getAll()).willReturn(savedQueueTokens);
        given(queueTokenRepository.update(any(QueueToken.class))).willReturn(updateQueueToken);

        // when
        QueueToken result = queueTokenService.createQueuePosition(info);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(updateQueueToken.getId());
        assertThat(result.getQueuePosition()).isEqualTo(updateQueueToken.getQueuePosition());
    }

    @DisplayName("상태값이 PROCESSED인 토큰들을 조회하면 성공해야한다")
    @Test
    void getProcessedTokensTest() {
        // given
        QueueToken token_1 = QueueToken.builder()
                .id(1L)
                .token(DEFAULT_TOKEN)
                .status(TokenStatus.PROCESSED)
                .build();

        QueueToken token_2 = QueueToken.builder()
                .id(1L)
                .token(DEFAULT_TOKEN)
                .status(TokenStatus.PROCESSED)
                .build();

        List<QueueToken> savedQueueTokens = List.of(token_1, token_2);

        given(queueTokenRepository.getAll()).willReturn(savedQueueTokens);

        // when
        List<QueueToken> result = queueTokenService.getProcessedTokens();

        // then
        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(savedQueueTokens.size());
    }
}
