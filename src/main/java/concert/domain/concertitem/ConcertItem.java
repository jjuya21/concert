package concert.domain.concertitem;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "CONCERTITEM_TABLE")
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
