package concert.domain.seat;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "SEAT_TABLE", indexes = {
        @Index(name = "idx_concert_item_id", columnList = "concert_item_id")
})
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(name = "concert_item_id", nullable = false)
    private Long concertItemId;

    @Column(name = "seat_no", nullable = false)
    private Long seatNo;

    @Column(nullable = false)
    private Long price;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SeatStatus status;

    @Column(name = "hold_expiry_time")
    private LocalDateTime holdExpiryTime;

    public void setStatus(SeatStatus status) {
        this.status = status;
    }
}