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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class QueueTokenServiceTest {

    @Value("${custom.token}")
    private String token;
    @Mock
    private QueueTokenRepository queueTokenRepository;
    @InjectMocks
    private QueueTokenService queueTokenService;

    @Test
    void getQueueTokenTest() {
        // given
        QueueToken queueToken = QueueToken.builder()
                .token(token)
                .build();

        given(queueTokenRepository.getByToken(token)).willReturn(queueToken);

        // when
        QueueToken result = queueTokenService.getQueueToken(QueueTokenInfo.builder().token(token).build());

        // then
        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(queueToken);
    }

    @Test
    void updateStatusTest() {
        // given
        QueueToken savedQueueToken = QueueToken.builder()
                .id(1L)
                .token(token)
                .status(TokenStatus.WAIT)
                .build();

        QueueToken updateQueueToken = QueueToken.builder()
                .id(1L)
                .token(token)
                .status(TokenStatus.PROCESSED)
                .build();

        QueueTokenInfo info = QueueTokenInfo.builder().token(token).build();

        given(queueTokenService.getQueueToken(info)).willReturn(savedQueueToken);
        given(queueTokenRepository.update(any(QueueToken.class))).willReturn(updateQueueToken);

        // when
        QueueToken result = queueTokenService.updateStatus(
                QueueTokenInfo.builder()
                        .token(token)
                        .status(TokenStatus.PROCESSED)
                        .build()
        );

        // then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(updateQueueToken.getId());
        assertThat(result.getStatus()).isEqualTo(TokenStatus.PROCESSED);
    }


    @Test
    void updateExpiryTimeTest() {
        // given
        LocalDateTime updateExpiryTime = LocalDateTime.now().plusMinutes(30);

        QueueToken savedQueueToken = QueueToken.builder()
                .id(1L)
                .token(token)
                .status(TokenStatus.WAIT)
                .expiryTime(LocalDateTime.now())
                .build();

        QueueToken updateQueueToken = QueueToken.builder()
                .id(1L)
                .token(token)
                .status(TokenStatus.WAIT)
                .expiryTime(updateExpiryTime)
                .build();

        QueueTokenInfo info = QueueTokenInfo.builder().token(token).build();

        given(queueTokenService.getQueueToken(info)).willReturn(savedQueueToken);
        given(queueTokenRepository.update(any(QueueToken.class))).willReturn(updateQueueToken);

        // when
        QueueToken result = queueTokenService.updateExpiryTime(
                QueueTokenInfo.builder()
                        .token(token)
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
    void createQueuePositionTest() {
        // given
        QueueToken savedQueueToken = QueueToken.builder()
                .id(1L)
                .token(token)
                .build();

        QueueToken updateQueueToken = QueueToken.builder()
                .id(1L)
                .token(token)
                .queuePosition(1L)
                .build();

        List<QueueToken> savedQueueTokens = List.of(savedQueueToken);

        QueueTokenInfo info = QueueTokenInfo.builder()
                .token(token)
                .build();

        given(queueTokenService.getQueueToken(info)).willReturn(savedQueueToken);
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
        QueueToken savedQueueToken = QueueToken.builder()
                .id(1L)
                .token(token)
                .status(TokenStatus.PROCESSED)
                .build();

        List<QueueToken> savedQueueTokens = List.of(savedQueueToken, savedQueueToken);

        given(queueTokenRepository.getAll()).willReturn(savedQueueTokens);

        // when
        List<QueueToken> result = queueTokenService.getProcessedTokens();

        // then
        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(savedQueueTokens.size());
    }
}
