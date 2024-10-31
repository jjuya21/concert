package concert.domain.queuetoken.service;

import concert.domain.queuetoken.TokenStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QueueTokenInfo {

    private String token;
    private TokenStatus status;
    private LocalDateTime expiryTime;
}
