package company.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import company.domain.DestinationDate;
import company.domain.DestinationTime;

@Repository
public interface DestinationTimeRepository extends JpaRepository<DestinationTime,Long>{
	
	List<DestinationTime> findByDestinationDate(DestinationDate destinationDate);
	
	
}
