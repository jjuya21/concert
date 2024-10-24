package concert.interfaces.api.v1.queuetoken;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
public class QueueTokenResponse {

    private UUID token;
    private long queuePosition;
}
