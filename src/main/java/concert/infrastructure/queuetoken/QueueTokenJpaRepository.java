package concert.infrastructure.queuetoken;

import concert.domain.queuetoken.QueueToken;
import concert.domain.queuetoken.TokenStatus;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import java.util.List;
import java.util.UUID;

public interface QueueTokenJpaRepository extends JpaRepository<QueueToken, UUID> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    QueueToken findByToken(String token);

    QueueToken save(QueueToken queueToken);

    List<QueueToken> findByStatus(TokenStatus status);

    QueueToken findByQueuePosition(long queuePosition);
}
