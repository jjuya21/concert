package concert.interfaces.api.v1.queuetoken;

import concert.application.createtoken.CreateTokenService;
import concert.application.getqueueposition.GetQueuePositionService;
import concert.domain.queuetoken.QueueToken;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping("/v1/api/queue-token")
@RestController
@RequiredArgsConstructor
public class QueueTokenController {

    private final GetQueuePositionService getQueuePositionService;
    private final CreateTokenService queueTokenService;

    @PostMapping
    public ResponseEntity<QueueTokenResponse> createToken() {

        QueueToken queueToken = queueTokenService.createToken();
        QueueTokenResponse response = new QueueTokenResponse(queueToken.getToken(), queueToken.getQueuePosition());
        return ResponseEntity.ok(response);
    }

    @GetMapping("{token}")
    public ResponseEntity<QueueTokenResponse> getPosition(@PathVariable("token") UUID token) {

        long queuePosition = getQueuePositionService.getQueuePosition(token);
        QueueTokenResponse response = new QueueTokenResponse(
                token, queuePosition
        );
        return ResponseEntity.ok(response);
    }
}
