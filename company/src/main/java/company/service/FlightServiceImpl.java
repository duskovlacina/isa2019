package company.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import company.domain.Flight;
import company.repository.FlightRepository;

@Service
public class FlightServiceImpl implements FlightService {

	@Autowired
	private FlightRepository flightRepository;
	
	@Override
	public Flight save(Flight flight) {
		return flightRepository.save(flight);
	}

}
