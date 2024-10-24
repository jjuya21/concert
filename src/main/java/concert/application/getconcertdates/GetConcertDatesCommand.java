package concert.application.getconcertdates;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetConcertDatesCommand {

    private String token;
    private long concertId;
}
