package concert.infrastructure.outbox;

import concert.domain.outbox.Outbox;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OutboxJpaRepository extends JpaRepository<Outbox, Long> {

    List<Outbox> findAll();

    Outbox save(Outbox outbox);
}
