package concert.interfaces.api.v1.concertitem;

import concert.domain.concertitem.ConcertItem;
import concert.domain.seat.Seat;
import concert.interfaces.api.v1.seat.SeatResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.time.LocalDate;

@Builder
@AllArgsConstructor
public class ConcertItemResponse {

    private long concertItemId;
    private LocalDate date;

    public static ConcertItemResponse from(ConcertItem concertItem) {

        return ConcertItemResponse.builder()
                .concertItemId(concertItem.getId())
                .date(concertItem.getConcertDate())
                .build();
    }
}
