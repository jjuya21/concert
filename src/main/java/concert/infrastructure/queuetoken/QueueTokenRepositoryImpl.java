package concert.infrastructure.queuetoken;

import concert.domain.queuetoken.QueueTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class QueueTokenRepositoryImpl implements QueueTokenRepository {

    private final ActiveTokenRepository activeTokenRepository;
    private final WaitingQueueRepository waitingQueueRepository;

    @Override
    public void enqueue(String token) {
        waitingQueueRepository.enqueue(token);
    }

    @Override
    public List<String> dequeue(long count) {
        return waitingQueueRepository.dequeue(count);
    }

    @Override
    public List<String> getWaitingQueue() {
        return waitingQueueRepository.getWaitingQueue();
    }

    @Override
    public Optional<Long> getRank(String token) {
        return waitingQueueRepository.getRank(token);
    }

    @Override
    public Optional<String> getActiveToken(String token) {
        return activeTokenRepository.getToken(token);
    }

    @Override
    public void deleteActiveToken(String token) {
        activeTokenRepository.deleteToken(token);
    }

    @Override
    public void saveActiveToken(String token, int expiry) {
        activeTokenRepository.saveToken(token, expiry);
    }
}
