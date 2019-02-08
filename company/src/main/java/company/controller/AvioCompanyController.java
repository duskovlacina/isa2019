package company.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import company.domain.AvioCompany;
import company.domain.Destination;
import company.dto.AvioCompanyDTO;
import company.dto.DestinationDTO;
import company.service.AvioCompanyService;



@RestController
@RequestMapping("/api/avioCompanies")
public class AvioCompanyController {
	
	@Autowired
	private AvioCompanyService avservice;
	
	@RequestMapping(method = RequestMethod.POST, 
			consumes = MediaType.APPLICATION_JSON_VALUE, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AvioCompanyDTO> save(@RequestBody AvioCompany avCompany) {
		System.out.println(avCompany.getName());
		AvioCompanyDTO culVenDTO = new AvioCompanyDTO(avservice.save(avCompany));
		return new ResponseEntity<AvioCompanyDTO>(culVenDTO,HttpStatus.OK);
	}
	
	@RequestMapping(value = "/{avId}",
			method = RequestMethod.PUT, 
			consumes = MediaType.APPLICATION_JSON_VALUE, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AvioCompanyDTO> modify(@RequestBody AvioCompany avCompany,@PathVariable Long avId) {
		System.out.println(avCompany.getName());
		AvioCompany av = avservice.modify(avCompany, avId);
		AvioCompanyDTO avDTO = new AvioCompanyDTO(av);
		return new ResponseEntity<AvioCompanyDTO>(avDTO,HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<AvioCompanyDTO>> getAll(){
		List<AvioCompanyDTO> avDTO = new ArrayList<AvioCompanyDTO>();
		for(AvioCompany companies : avservice.getAll()){
			avDTO.add(new AvioCompanyDTO(companies));
		}
		return new ResponseEntity<List<AvioCompanyDTO>>(avDTO,HttpStatus.OK);
	}
	
	@RequestMapping(value="/searchAVC/{name}/{address}",method = RequestMethod.GET)
	public ResponseEntity<List<AvioCompanyDTO>> searchAVC(@PathVariable String name, @PathVariable String address){
		List<AvioCompanyDTO> avCompanysDTO = new ArrayList<AvioCompanyDTO>();
		for(AvioCompany avCompany : avservice.searchAV(name, address)){
			avCompanysDTO.add(new AvioCompanyDTO(avCompany));
		}
		return new ResponseEntity<List<AvioCompanyDTO>>(avCompanysDTO,HttpStatus.OK);
	}
	
	@RequestMapping(value="/{avId}/getDestinations",method = RequestMethod.GET)
	public ResponseEntity<List<DestinationDTO>> getDestinationsForCompany(@PathVariable Long avId){
		List<DestinationDTO> destinationsDTO = new ArrayList<DestinationDTO>();
		for(Destination destination : avservice.getDestinations(avId)){
			destinationsDTO.add(new DestinationDTO(destination));
		}
		return new ResponseEntity<List<DestinationDTO>>(destinationsDTO,HttpStatus.OK);
	}

}
