package concert.infrastructure.outbox;

import concert.domain.outbox.Outbox;
import concert.domain.outbox.OutboxRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class OutboxRepositoryImpl implements OutboxRepository {

    private final OutboxJpaRepository jpaRepository;

    @Override
    public Outbox save(Outbox outbox) {
        return jpaRepository.save(outbox);
    }

    @Override
    public List<Outbox> findAll() {
        return jpaRepository.findAll();
    }
}
