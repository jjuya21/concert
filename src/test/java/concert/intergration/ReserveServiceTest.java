package concert.intergration;

import concert.application.payment.PaymentService;
import concert.application.reserve.ReserveCommand;
import concert.application.reserve.ReserveService;
import concert.domain.reservation.Reservation;
import concert.domain.reservation.ReservationRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@SpringBootTest
public class ReserveServiceTest {

    @Autowired
    private ReserveService reserveService;

    @Autowired
    ReservationRepository reservationRepository;

    private UUID token = UUID.fromString("a8098c1a-f86e-11da-bd1a-00112444be1e");

    @Test
    @Transactional
    void reserveTest() {
        // given
        ReserveCommand command = ReserveCommand.builder()
                .token(token)
                .seatId(1)
                .userId(1)
                .build();

        // when
        Reservation result = reserveService.reserve(command);

        // then
        Reservation reservation = reservationRepository.getReservation(result.getId());
        Assertions.assertThat(result).isEqualTo(reservation);
    }
}
