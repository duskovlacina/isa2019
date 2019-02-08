package company.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import company.domain.Invite;
import company.dto.InviteDTO;
import company.service.InviteService;

@RestController
@RequestMapping("/api/invites")
public class InviteController {
	
	@Autowired
	private InviteService inviteservice;
	
	@RequestMapping(value="/{invId}/accept",method = RequestMethod.GET)
	public ResponseEntity<String> acceptInvite(@PathVariable Long invId){
		Invite inv = inviteservice.acceptInvite(invId);
		InviteDTO inviteDTO = new InviteDTO(inv);
		if(inviteDTO.getId() != null){
			return new ResponseEntity<String>("invite accepted",HttpStatus.OK);
		}else{
			return new ResponseEntity<String>("error",HttpStatus.BAD_REQUEST);
		}
		
	}
	
	@RequestMapping(value="/{invId}/decline",method = RequestMethod.GET)
	public ResponseEntity<String> declineInvite(@PathVariable Long invId){
		Invite inv = inviteservice.declineInvite(invId);
		InviteDTO inviteDTO = new InviteDTO(inv);
		if(inviteDTO.getId() != null){
			return new ResponseEntity<String>("invite declined",HttpStatus.OK);
		}else{
			return new ResponseEntity<String>("error",HttpStatus.BAD_REQUEST);
		}
	}
	
}
