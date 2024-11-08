package concert.interfaces.api.v1.queuetoken;

import concert.application.createtoken.CreateTokenService;
import concert.application.getqueueposition.GetQueuePosition;
import concert.domain.queuetoken.QueueToken;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/v1/api/queue-token")
@RestController
@RequiredArgsConstructor
public class QueueTokenController {

    private final GetQueuePosition getQueuePosition;
    private final CreateTokenService queueTokenService;

    @PostMapping
    public ResponseEntity<QueueTokenResponse> createToken() throws Exception {

        QueueToken queueToken = queueTokenService.createToken();
        QueueTokenResponse response = new QueueTokenResponse(queueToken.getToken(), queueToken.getQueuePosition());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/position")
    public ResponseEntity<QueueTokenResponse> getPosition(HttpServletRequest request) throws Exception {

        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new Exception("토큰이 존재하지 않습니다.");
        }

        String token = authorizationHeader.substring(7);

        QueueToken queueToken = getQueuePosition.getQueuePosition(token);
        QueueTokenResponse response = new QueueTokenResponse(
                token, queueToken.getQueuePosition()
        );
        return ResponseEntity.ok(response);
    }
}
