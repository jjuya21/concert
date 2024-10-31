package concert.infrastructure.reservation;

import concert.domain.reservation.Reservation;
import concert.domain.reservation.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ReservationRepositoryImpl implements ReservationRepository {

    private final ReservationJpaRepository jpaRepository;

    @Override
    public Reservation reserve(Reservation reservation) {
        return jpaRepository.save(reservation);
    }

    @Override
    public Optional<Reservation> getReservation(long reservationId) {
        return jpaRepository.findById(reservationId);
    }

    @Override
    public Reservation updateReservation(Reservation reservation) {
        return jpaRepository.save(reservation);
    }
}
