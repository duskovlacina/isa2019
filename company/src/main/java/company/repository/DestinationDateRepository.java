package company.repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import company.domain.Destination;
import company.domain.DestinationDate;

@Repository
public interface DestinationDateRepository extends JpaRepository<DestinationDate, Long>{

	List<DestinationDate> findByDestination(Destination destination);
	
	List<DestinationDate> findByDestinationDateContaining(Date date);
	
}