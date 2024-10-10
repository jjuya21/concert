package concert.interfaces;

import concert.interfaces.concertitem.ConcertItemResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("api/concert-item")
@RestController
public class ConcertItemController {

    @GetMapping("/{concertId}/available-dates")
    public ResponseEntity<List<ConcertItemResponse>> getAvailableDates(@PathVariable("concertId") long concertId) {

        List<ConcertItemResponse> concertItemResponseList = new ArrayList<>();
        concertItemResponseList.add(new ConcertItemResponse(1L, "2024-10-15"));
        concertItemResponseList.add(new ConcertItemResponse(1L, "2024-10-15"));
        return ResponseEntity.ok(concertItemResponseList);
    }
}
