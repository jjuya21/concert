package concert.application.reserve;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReserveCommand {

    private UUID token;
    private long userId;
    private long seatId;
}
