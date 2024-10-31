package concert.domain.queuetoken;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "QUEUETOKEN_TABLE")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QueueToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(updatable = false, nullable = false, unique = true)
    private String token;

    @Column(name = "queue_position")
    private Long queuePosition;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TokenStatus status;

    @Column(name = "expiry_time", nullable = false)
    private LocalDateTime expiryTime;

    public boolean checkIsWait() {
        return this.status == TokenStatus.WAIT;
    }

    public boolean checkIsProcessed() {
        return this.status == TokenStatus.PROCESSED;
    }

    public void setStatus(TokenStatus status) {
        this.status = status;
    }

    public void setExpiryTime(LocalDateTime time) {
        this.expiryTime = time;
    }
}
