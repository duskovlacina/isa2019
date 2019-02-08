package company.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import company.domain.Invite;
import company.domain.Reservation;
import company.domain.User;
import company.dto.InviteDTO;
import company.dto.ReservationDTO;
import company.service.InviteService;
import company.service.ReservationService;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {
	
	@Autowired
	private InviteService inviteservice;
	
	@Autowired
	private ReservationService reservationservice;
	
	@RequestMapping(value="/{resId}/sendInvite/{userId}",method = RequestMethod.GET)
	public ResponseEntity<InviteDTO> sendInvite(@PathVariable Long resId,@PathVariable Long userId){
		Invite inv = inviteservice.sendInvite(resId, userId);
		InviteDTO inviteDTO = new InviteDTO(inv);
		inviteservice.sendInviteMail(userId, inv.getId());
		return new ResponseEntity<InviteDTO>(inviteDTO,HttpStatus.OK);
	}
	
	@RequestMapping(value="/getAllForLogged",method = RequestMethod.GET)
	public ResponseEntity<List<ReservationDTO>> getReservationsforLoggedUser(HttpServletRequest request){
		User logged = (User) request.getSession().getAttribute("loggedUser");
		List<Reservation> reservations = reservationservice.getReservationsForUser(logged.getId());
		List<ReservationDTO> reservationsDTO = new ArrayList<ReservationDTO>();
		for(Reservation res : reservations) {
			reservationsDTO.add(new ReservationDTO(res));
		}
		return new ResponseEntity<List<ReservationDTO>>(reservationsDTO,HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<ReservationDTO>> getAll(){
		
		List<Reservation> reservations = reservationservice.getAll();
		List<ReservationDTO> reservationsDTO = new ArrayList<ReservationDTO>();
		for(Reservation res : reservations) {
			reservationsDTO.add(new ReservationDTO(res));
		}
		return new ResponseEntity<List<ReservationDTO>>(reservationsDTO,HttpStatus.OK);
	}
	
	@RequestMapping(value="/getVisitsForLogged",method = RequestMethod.GET)
	public ResponseEntity<List<ReservationDTO>> getVisitsforLoggedUser(HttpServletRequest request){
		User logged = (User) request.getSession().getAttribute("loggedUser");
		List<Reservation> reservations = reservationservice.getVisitedReservationsForUser(logged.getId());
		List<ReservationDTO> reservationsDTO = new ArrayList<ReservationDTO>();
		for(Reservation res : reservations) {
			reservationsDTO.add(new ReservationDTO(res));
		}
		return new ResponseEntity<List<ReservationDTO>>(reservationsDTO,HttpStatus.OK);
	}
	
	@RequestMapping(value="/{resId}",method = RequestMethod.GET)
	public ResponseEntity<ReservationDTO> getReservation(@PathVariable Long resId){
		Reservation res = reservationservice.getReservation(resId);
		ReservationDTO resDTO = new ReservationDTO(res);
		return new ResponseEntity<ReservationDTO>(resDTO,HttpStatus.OK);
	}
	
	@RequestMapping(value="/cancel/{resId}",method = RequestMethod.DELETE)
	public ResponseEntity<ReservationDTO> cancelReservation(@PathVariable Long resId){
		Reservation deleted =reservationservice.cancelReservation(resId);
		if(deleted != null) {
			ReservationDTO deletedDTO = new ReservationDTO(deleted);
			return new ResponseEntity<ReservationDTO>(deletedDTO,HttpStatus.OK);
		}else {
			ReservationDTO resDTO = null;
			return new ResponseEntity<ReservationDTO>(resDTO,HttpStatus.BAD_REQUEST);
		}
	}
	
}
