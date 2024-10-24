package concert.infrastructure.queuetoken;

import concert.domain.queuetoken.QueueToken;
import concert.domain.queuetoken.QueueTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class QueueTokenRepositoryImpl implements QueueTokenRepository {

    private final QueueTokenJpaRepository jpaRepository;

    @Override
    public QueueToken create(QueueToken queueToken) {
        return jpaRepository.save(queueToken);
    }

    @Override
    public Optional<QueueToken> getByToken(String token) {

        return jpaRepository.findByToken(token);
    }

    @Override
    public Optional<QueueToken> getByQueuePosition(long queuePosition) {
        return jpaRepository.findByQueuePosition(queuePosition);
    }

    @Override
    public QueueToken update(QueueToken queueToken) {
        return jpaRepository.save(queueToken);
    }

    @Override
    public List<QueueToken> getAll() {
        return jpaRepository.findAll();
    }

    @Override
    public void expiry(QueueToken queueToken) {
        jpaRepository.delete(queueToken);
    }


}
