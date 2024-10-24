package concert.interfaces.api.v1.seat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SeatRequest {

    private long seatNo;
    private long concertItemId;
    private long price;
}
