package company.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import company.domain.Flight;
import company.domain.Seat;
import company.repository.FlightRepository;
import company.repository.SeatRepository;


@Service
public class SeatServiceImpl implements SeatService {

	@Autowired
	private SeatRepository seatrepository;
	
	@Autowired
	private FlightRepository flightrepository;
	
	@Override
	public Seat save(Seat seat) {
		return seatrepository.save(seat);
	}

	@Override
	public List<Seat> findByFlight(Long flightId) {
		Flight flight = flightrepository.findOne(flightId);
		return seatrepository.findByFlight(flight);
	}

	@Override
	public Seat findByFlightAndRowAndSeatInRow(Long flightId, int row, int seatInRow) {
		Flight flight = flightrepository.findOne(flightId);
		return seatrepository.findByFlightAndRowAndSeatInRow(flight, row, seatInRow);
	}

}
