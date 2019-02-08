package company.service;

import java.util.List;

import company.domain.DestinationDate;

public interface DestinationDateService {

	DestinationDate save(DestinationDate destinationDate);
	
	List<DestinationDate> getDatesForDestination(Long destinationId);
	
}
