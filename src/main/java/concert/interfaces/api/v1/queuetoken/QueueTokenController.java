package concert.interfaces.api.v1.queuetoken;

import concert.application.createtoken.CreateToken;
import concert.application.getqueueposition.GetQueuePosition;
import concert.domain.queuetoken.QueueToken;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/v1/api/queue-token")
@RestController
@RequiredArgsConstructor
public class QueueTokenController {

    private final GetQueuePosition getQueuePosition;
    private final CreateToken queueTokenService;

    @PostMapping
    public ResponseEntity<QueueTokenResponse> createToken() {

        QueueToken queueToken = queueTokenService.createToken();
        QueueTokenResponse response = new QueueTokenResponse(queueToken.getToken(), queueToken.getQueuePosition());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/position")
    public ResponseEntity<QueueTokenResponse> getPosition(@RequestBody QueueTokenRequest request) {

        long queuePosition = getQueuePosition.getQueuePosition(request.getToken());
        QueueTokenResponse response = new QueueTokenResponse(
                request.getToken(), queuePosition
        );
        return ResponseEntity.ok(response);
    }
}
