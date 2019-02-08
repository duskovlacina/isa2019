package company.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import company.domain.DestinationDate;
import company.domain.DestinationTime;
import company.domain.Reservation;
import company.domain.Seat;
import company.domain.User;
import company.dto.DestinationDateDTO;
import company.dto.DestinationTimeDTO;
import company.dto.ReservationDTO;
import company.dto.SeatDTO;
import company.service.DestinationTimeService;
import company.service.ReservationService;
import company.service.SeatService;

@RestController
@RequestMapping("/api/destinations/{destinationId}/destinationDates/{dateId}/destinationTimes")
public class DestinationTimeController {
	
	@Autowired
	private DestinationTimeService dtservice;
	
	@Autowired
	private ReservationService resservice;
	
	@Autowired
	private SeatService seatservice;
	
	/*@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<DestinationTimeDTO>> getTimesForProjection(@PathVariable Long destinationId, @PathVariable Long dateId){
		List<DestinationTimeDTO> destinationtimesDTO = new ArrayList<DestinationTimeDTO>();
		for(DestinationTime dt : dtservice.findByDestinationDate(destinationId)){
			//System.out.println("destination" + );
			destinationtimesDTO.add(new DestinationTimeDTO(dt));
		}
		return new ResponseEntity<List<DestinationTimeDTO>>(destinationtimesDTO,HttpStatus.OK);
	}*/
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<DestinationTimeDTO>> getDatesForDestinations(@PathVariable Long destinationId, @PathVariable Long dateId){
		List<DestinationTime> destinations = dtservice.getTimessForDestination(dateId);
		List<DestinationTimeDTO> destinationsDTO = new ArrayList<DestinationTimeDTO>();
		for(DestinationTime destination : destinations){
			//System.out.println("PRINTAAJ" + destination.getDestinationDate());
			destinationsDTO.add(new DestinationTimeDTO(destination));
		}
		return new ResponseEntity<List<DestinationTimeDTO>>(destinationsDTO,HttpStatus.OK);
	}
	
	@RequestMapping(value="/{dateId}/seats",method = RequestMethod.GET)
	public ResponseEntity<List<SeatDTO>> getSeatsForProjection(@PathVariable Long dateId){
		DestinationTime dt = dtservice.findOne(dateId);
		List<Seat> seats = seatservice.findByFlight(dt.getFlights().getId());
		List<SeatDTO> seatsDTO = new ArrayList<SeatDTO>();
		for(Seat seat : seats){
			seatsDTO.add(new SeatDTO(seat));
		}
		return new ResponseEntity<List<SeatDTO>>(seatsDTO,HttpStatus.OK);
	}
	
	@RequestMapping(value="/{dateId}/takenSeats",method = RequestMethod.GET)
	public ResponseEntity<List<SeatDTO>> getTakenSeats(@PathVariable Long dateId){
		List<Seat> takenSeats = dtservice.getTakenSeats(dateId);
		List<SeatDTO> tekenSeatsDTO = new ArrayList<SeatDTO>();
		for(Seat seat : takenSeats) {
			tekenSeatsDTO.add(new SeatDTO(seat));
		}
		return new ResponseEntity<List<SeatDTO>>(tekenSeatsDTO,HttpStatus.OK);
	}
	
	@RequestMapping(value="/{dateId}/seats",method = RequestMethod.POST)
	public ResponseEntity<ReservationDTO> reserveSeats(@RequestBody List<String> seatInfo, @PathVariable Long dateId, HttpServletRequest request){
		User loggedUser = (User) request.getSession().getAttribute("loggedUser");
		Reservation reservation = dtservice.reserveSeats(dateId, seatInfo, loggedUser.getId());
		if(reservation != null) {
			ReservationDTO reservationDTO = new ReservationDTO(reservation);
			resservice.sendReservationMail(loggedUser.getId(), reservation.getId());
			return new ResponseEntity<ReservationDTO>(reservationDTO,HttpStatus.OK);
		}
		ReservationDTO resDTO = null;
		return new ResponseEntity<ReservationDTO>(resDTO,HttpStatus.BAD_REQUEST);
	}

}
