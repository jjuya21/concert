package concert.interfaces.api.v1.queuetoken;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class QueueTokenResponse {

    private String token;
    private long queuePosition;
}
