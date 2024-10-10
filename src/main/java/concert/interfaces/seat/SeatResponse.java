package concert.interfaces.seat;


import lombok.AllArgsConstructor;

@AllArgsConstructor
public class SeatResponse {

    long id;
    int seatNumber;
    int price;
    String status;
}
