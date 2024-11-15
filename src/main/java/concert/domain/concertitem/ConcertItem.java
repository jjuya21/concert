package concert.domain.concertitem;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "CONCERTITEM_TABLE", indexes = {
        @Index(name = "idx_concertId", columnList = "concertId")
})
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConcertItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "concert_id", nullable = false)
    private Long concertId;

    @Column(name = "concert_date", nullable = false)
    private LocalDate concertDate;
}
