package concert.interfaces.api.v1.concertitem;

import concert.domain.concertitem.ConcertItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
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
