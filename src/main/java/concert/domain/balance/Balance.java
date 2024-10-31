package concert.domain.balance;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

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
    private Long balance;

    @Version
    @Builder.Default
    private Integer version = 0;

    public void charge(long amount) throws Exception {
        if (amount < 0) {
            throw new Exception("충전 금액은 음수일 수 없습니다.");
        }

        this.balance = this.balance + amount;
    }

    public void use(long amount) throws Exception {
        if (amount > this.balance) {
            throw new Exception("잔액이 부족합니다.");
        }

        this.balance = this.balance - amount;
    }
}
