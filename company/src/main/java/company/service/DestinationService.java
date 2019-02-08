package company.service;

import java.util.List;

import company.domain.Destination;

public interface DestinationService {

	Destination save(Destination destination);
	
	List<Destination> findAll();
	
	Destination findOne(Long id);
	
	Destination deleteDestination(Long id);
	
	Destination modify(Destination destination,Long id);
	
	Destination rateDestination(Long id, int grade);
	
	List<Destination> searchDestination(String name);
	
}
