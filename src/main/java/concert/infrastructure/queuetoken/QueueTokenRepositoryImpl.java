package concert.infrastructure.queuetoken;

import concert.domain.queuetoken.QueueToken;
import concert.domain.queuetoken.QueueTokenRepository;
import concert.domain.queuetoken.TokenStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

import static org.antlr.v4.runtime.tree.xpath.XPath.findAll;

@Repository
@RequiredArgsConstructor
public class QueueTokenRepositoryImpl implements QueueTokenRepository {

    private final QueueTokenJpaRepository jpaRepository;

    @Override
    public QueueToken create(QueueToken queueToken) {
        return jpaRepository.save(queueToken);
    }

    @Override
    public QueueToken getByToken(UUID token) {

        return jpaRepository.findByToken(token);
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
