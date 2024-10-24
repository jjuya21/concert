package concert.infrastructure.queuetoken;

import concert.domain.queuetoken.QueueToken;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import java.util.Optional;
import java.util.UUID;

public interface QueueTokenJpaRepository extends JpaRepository<QueueToken, UUID> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<QueueToken> findByToken(String token);

    QueueToken save(QueueToken queueToken);

    Optional<QueueToken> findByQueuePosition(long queuePosition);
}
