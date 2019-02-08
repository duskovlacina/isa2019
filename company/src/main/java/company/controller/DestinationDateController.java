package company.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import company.domain.DestinationDate;
import company.dto.DestinationDateDTO;
import company.service.DestinationDateService;

@RestController
@RequestMapping("/api/destination/{destinationId}/destDates")
public class DestinationDateController {

	@Autowired
	private DestinationDateService ddservice;
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<DestinationDateDTO>> getDatesForDestinations(@PathVariable Long destinationId){
		List<DestinationDate> destinations = ddservice.getDatesForDestination(destinationId);
		List<DestinationDateDTO> destinationsDTO = new ArrayList<DestinationDateDTO>();
		for(DestinationDate destination : destinations){
			//System.out.println("PRINTAAJ" + destination.getDestinationDate());
			destinationsDTO.add(new DestinationDateDTO(destination));
		}
		return new ResponseEntity<List<DestinationDateDTO>>(destinationsDTO,HttpStatus.OK);
	}
	
}
