package company.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import company.domain.Destination;

@Repository
public interface DestinationRepository extends JpaRepository<Destination,Long>{
	
	Destination findById(Long id);
	
	List<Destination> findByNameContaining(String name);
}
