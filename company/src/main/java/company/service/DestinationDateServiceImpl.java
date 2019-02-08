package company.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import company.domain.Destination;
import company.domain.DestinationDate;
import company.repository.DestinationDateRepository;
import company.repository.DestinationRepository;

@Service
public class DestinationDateServiceImpl implements DestinationDateService {

	@Autowired
	private DestinationDateRepository destDateRep;
	
	@Autowired
	private DestinationRepository destinationRep;
	
	@Override
	public DestinationDate save(DestinationDate destinationDate) {
		return destDateRep.save(destinationDate);
	}

	@Override
	public List<DestinationDate> getDatesForDestination(Long destinationId) {
		Destination destination = destinationRep.findOne(destinationId);		
		return destDateRep.findByDestination(destination);
	}

}
