package company.service;

import java.util.List;

import company.domain.Seat;

public interface SeatService {
	
	Seat save(Seat seat);
	
	List<Seat> findByFlight(Long flightId);
	
	Seat findByFlightAndRowAndSeatInRow(Long flightId,int row, int seatInRow);
}
