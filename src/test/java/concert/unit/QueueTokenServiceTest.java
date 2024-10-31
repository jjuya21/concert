package concert.unit;

import concert.domain.queuetoken.QueueToken;
import concert.domain.queuetoken.QueueTokenRepository;
import concert.domain.queuetoken.TokenStatus;
import concert.domain.queuetoken.service.QueueTokenInfo;
import concert.domain.queuetoken.service.QueueTokenService;
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
