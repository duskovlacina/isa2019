package company.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import company.domain.DestinationTime;
import company.dto.DestinationTimeDTO;
import company.service.DestinationTimeService;

@RestController
@RequestMapping("/api/destinationTimes")
public class SearchController {
	
	@Autowired
	private DestinationTimeService dtservice;

	@RequestMapping(value="/searchDestination/{destination}/{date3}",method = RequestMethod.GET)
	public ResponseEntity<List<DestinationTimeDTO>> searchDestination(@PathVariable String destination, @PathVariable String date3){
		String startDateString = date3;
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd"); 
		Date startDate = new Date();
		try {
		    startDate = df.parse(startDateString);
		    String newDateString = df.format(startDate);
		    //System.out.println(newDateString);
		    System.out.println(startDate);
		} catch (ParseException e) {
		    e.printStackTrace();
		}
		List<DestinationTimeDTO> dtDTO = new ArrayList<DestinationTimeDTO>();
		for(DestinationTime dt : dtservice.searchDestination(destination, startDate)){
			dtDTO.add(new DestinationTimeDTO(dt));
		}
		return new ResponseEntity<List<DestinationTimeDTO>>(dtDTO,HttpStatus.OK);
	}
}
