package concert.interfaces.api.v1.concert;

import concert.domain.concert.Concert;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class ConcertResponse {

    private long concertId;
    private String title;

    public static ConcertResponse from(Concert concert) {

        return ConcertResponse.builder()
                .concertId(concert.getId())
                .title(concert.getTitle())
                .build();
    }
}
