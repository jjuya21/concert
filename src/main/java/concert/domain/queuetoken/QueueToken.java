package concert.domain.queuetoken;

import concert.domain.payment.PaymentStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "QUEUETOKEN_TABLE")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QueueToken {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(updatable = false, nullable = false)
    private UUID token;

    @Column(name = "queue_position")
    private Long queuePosition;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TokenStatus status;

    @Column(name = "expiry_time", nullable = false)
    private LocalDateTime expiryTime;

    public void setStatus(TokenStatus status) {
        this.status = status;
    }

    public void setExpiryTime(LocalDateTime time) {
        this.expiryTime = time;
    }
}
