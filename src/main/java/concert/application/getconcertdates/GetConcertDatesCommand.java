package concert.application.getconcertdates;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetConcertDatesCommand {

    private UUID token;
    private long concertId;
}
