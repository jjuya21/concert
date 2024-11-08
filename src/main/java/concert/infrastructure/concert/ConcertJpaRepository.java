package concert.infrastructure.concert;

import concert.domain.concert.Concert;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConcertJpaRepository extends JpaRepository<Concert, Long> {

    List<Concert> findAll();
}
