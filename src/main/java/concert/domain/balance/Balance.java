package concert.domain.balance;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;

@Entity
@Table(name = "BALANCE_TABLE")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Balance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(nullable = false)
    @ColumnDefault("0")
    private Long balance = 0L;

    public void charge(long amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Charge amount must be greater than zero.");
        }

        this.balance = this.balance + amount;
    }

    public void use(long amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Use amount must be greater than zero.");
        }

        if (amount <= this.balance) {
            throw new IllegalArgumentException("Use amount must be less than balance.");
        }

        this.balance = this.balance - amount;
    }
}
