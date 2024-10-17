package concert.infrastructure.payment;

import concert.domain.payment.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PaymentJpaRepository extends JpaRepository<Payment, Long> {

    Payment save(Payment payment);
}
