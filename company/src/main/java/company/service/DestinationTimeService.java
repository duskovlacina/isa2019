package company.service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import company.domain.DestinationTime;
import company.domain.Reservation;
import company.domain.Seat;


public interface DestinationTimeService {
	
    List<DestinationTime> findByDestinationDate(Long destinationDateId);
	
    DestinationTime save(DestinationTime destinationTime);
	
    DestinationTime findOne(Long dtId);
	
	Reservation reserveSeats(Long destinationtimeId,List<String> seatinfo, Long userId);
	
	List<Seat> getTakenSeats(Long destinationtimeId);
	
	List<DestinationTime> getTimessForDestination(Long destinationId);
	
	List<DestinationTime> searchDestination(String destination, Date date);
}
