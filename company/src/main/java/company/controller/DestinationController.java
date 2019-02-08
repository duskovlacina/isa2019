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

import company.domain.Destination;
import company.dto.DestinationDTO;
import company.service.DestinationService;

@RestController
@RequestMapping("/api/destinations")
public class DestinationController {
	
	@Autowired
	private DestinationService destinationservice;
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<DestinationDTO>> getAllDestinations(){
		List<Destination> destinations = destinationservice.findAll();
		List<DestinationDTO> destinationsDTO = new ArrayList<DestinationDTO>();
		for(Destination dest : destinations){
			destinationsDTO.add(new DestinationDTO(dest));
		}
		return new ResponseEntity<List<DestinationDTO>>(destinationsDTO,HttpStatus.OK);
	}
	
	@RequestMapping(value="/delete/{id}", method = RequestMethod.GET)
	public ResponseEntity<DestinationDTO> deleteEvents(@PathVariable Long id) {
		Destination destination = destinationservice.deleteDestination(id);
		DestinationDTO destinationDTO = new DestinationDTO(destination);	
		return new ResponseEntity<DestinationDTO>(destinationDTO,HttpStatus.OK);
	}
	
	@RequestMapping(value="/searchDestination/{name}",method = RequestMethod.GET)
	public ResponseEntity<List<DestinationDTO>> searchDestination(@PathVariable String name){
		List<DestinationDTO> destinationDTO = new ArrayList<DestinationDTO>();
		for(Destination dest : destinationservice.searchDestination(name)){
			destinationDTO.add(new DestinationDTO(dest));
		}
		return new ResponseEntity<List<DestinationDTO>>(destinationDTO,HttpStatus.OK);
	}
	
	@RequestMapping(value="/rate/{id}/{grade}", method = RequestMethod.GET)
	public ResponseEntity<DestinationDTO> rateDestination(@PathVariable Long id,@PathVariable int grade) {
		Destination destination = destinationservice.rateDestination(id, grade);
		DestinationDTO destination1 = new DestinationDTO(destination);
		return new ResponseEntity<DestinationDTO>(destination1,HttpStatus.OK);
	}
	
}
