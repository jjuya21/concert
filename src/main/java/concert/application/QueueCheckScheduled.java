package concert.application;

import concert.domain.queuetoken.QueueTokenRedisRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class QueueCheckScheduled {

    private final QueueTokenRedisRepository queueTokenRedisRepository;

    @Scheduled(initialDelay = 10000, fixedDelay = 10000)
    public void passNextInQueue() {

        List<String> nextTokens = queueTokenRedisRepository.dequeue(10L);
        int expiryTime = 30;

        for (String nextToken : nextTokens) {

            queueTokenRedisRepository.saveActiveToken(
                    nextToken, expiryTime
            );
        }
    }
}
