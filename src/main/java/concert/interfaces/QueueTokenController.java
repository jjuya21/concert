package concert.interfaces;

import concert.interfaces.queuetoken.QueueTokenResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/queue-token")
@RestController
public class QueueTokenController {

    @PostMapping("{userId}")
    public ResponseEntity<QueueTokenResponse> getQueueToken(@PathVariable("userId") long userId) {

        QueueTokenResponse queueTokenResponse = new QueueTokenResponse("afjewfafew.bsgrevearea.esarafaf");
        return ResponseEntity.ok(queueTokenResponse);
    }

    @GetMapping("{tokenId}")
    public ResponseEntity<QueueTokenResponse> validateToken(@PathVariable("tokenId") long tokenId) {

        QueueTokenResponse queueTokenResponse = new QueueTokenResponse("afjewfafew.grafafjnkafue.esarafaf");
        return ResponseEntity.ok(queueTokenResponse);
    }

    @Scheduled(fixedDelay = 5000)
    @GetMapping("{userId}")
    public ResponseEntity<QueueTokenResponse> getPosition(@PathVariable("userId") long userId) {

        QueueTokenResponse queueTokenResponse = new QueueTokenResponse("afjewfafew.fjwieaoafkbykr.esarafaf");
        return ResponseEntity.ok(queueTokenResponse);
    }
}
