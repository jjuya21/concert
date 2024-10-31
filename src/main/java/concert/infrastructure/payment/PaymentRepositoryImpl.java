package concert.infrastructure.payment;

import concert.domain.payment.Payment;
import concert.domain.payment.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PaymentRepositoryImpl implements PaymentRepository {

    private final PaymentJpaRepository jpaRepository;

    @Override
    public Payment create(Payment payment) {
        return jpaRepository.save(payment);
    }
}
