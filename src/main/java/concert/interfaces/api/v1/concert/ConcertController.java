package concert.interfaces.api.v1.concert;


import concert.application.getconcerts.GetConcerts;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/v1/api/concert")
@RestController
@RequiredArgsConstructor
public class ConcertController {

    private final GetConcerts getConcerts;

    @GetMapping("/all")
    public ResponseEntity<List<ConcertResponse>> getAllConcert() {

        List<ConcertResponse> responses = getConcerts.getConcerts()
                .stream()
                .map(ConcertResponse::from)
                .toList();

        return ResponseEntity.ok(responses);
    }
}
