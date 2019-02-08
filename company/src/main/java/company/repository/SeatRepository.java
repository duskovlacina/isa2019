package company.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import company.domain.Flight;
import company.domain.Seat;

@Repository
public interface SeatRepository extends JpaRepository<Seat,Long>{
	
	List<Seat> findByFlight(Flight flight);
	
	Seat findByFlightAndRowAndSeatInRow(Flight flight,int row, int seatInRow);

}
