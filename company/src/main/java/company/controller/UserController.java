package company.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import company.domain.User;
import company.dto.UserDTO;
import company.service.UserService;


@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "/register",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE
			)
	public ResponseEntity<UserDTO> registerUser(@RequestBody User user) throws Exception{
		User registerUser = userService.save(user);
		System.out.println("sejvao");
		if(registerUser != null){
			User savedUser = userService.findByEmail(user.getEmail());
			System.out.println(savedUser);
			userService.sendVerificationMail(savedUser);
			UserDTO userdto = new UserDTO(user);
			return new ResponseEntity<UserDTO>(userdto, HttpStatus.CREATED);
		}
		UserDTO userDTO = null;
		return new ResponseEntity<UserDTO>(userDTO,HttpStatus.BAD_REQUEST);
	}
	
	@RequestMapping(value="/verify/{id}", method = RequestMethod.GET)
	public ResponseEntity<String> verifyUser(@PathVariable Long id) throws Exception{
		userService.verifyEmail(id);
		return new ResponseEntity<String>("verifikovan",HttpStatus.ACCEPTED);
	}
	
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<UserDTO>> getUsers(){
		List<User> users = userService.findAll();
		List<UserDTO> usersdto = new ArrayList<UserDTO>();
		for(User user : users) {
			usersdto.add(new UserDTO(user));
		}
		return new ResponseEntity<List<UserDTO>>(usersdto,HttpStatus.OK);
	}
	
	
	@RequestMapping(value="/{id}",method = RequestMethod.PUT)
	public ResponseEntity<UserDTO> modifyUser(@RequestBody User user, @PathVariable Long id){
		User modified = userService.modifyUser(user, id);
		return new ResponseEntity<UserDTO>(new UserDTO(modified),HttpStatus.OK);
	}
	
	
	@RequestMapping(value="/login", method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserDTO> signIn(@RequestBody User user,HttpSession session,HttpServletRequest request){
		User loggedUser = userService.signIn(user);
		if(loggedUser != null) {
		    HttpSession newSession = request.getSession();
		    newSession.setAttribute("loggedUser", loggedUser);
		    UserDTO logged = new UserDTO(loggedUser);
		    return new ResponseEntity<UserDTO>(logged,HttpStatus.OK);
		}
		UserDTO logged = null;
		return new ResponseEntity<UserDTO>(logged,HttpStatus.NOT_FOUND);
	}
	
	@RequestMapping(value="/logout",method = RequestMethod.GET)
	public ResponseEntity<String> signOut(HttpSession session, HttpServletRequest request){
		request.getSession().invalidate();
		return new ResponseEntity<String>("User logged out",HttpStatus.OK);
	}

	@RequestMapping(value="/isLoggedIn",method = RequestMethod.GET)
	public ResponseEntity<UserDTO> getLoggedIn(HttpServletRequest request){
		User logged = (User) request.getSession().getAttribute("loggedUser");
		if(logged!=null) {
			UserDTO loggedDTO = new UserDTO(logged);
			return new ResponseEntity<UserDTO>(loggedDTO,HttpStatus.OK);
		}
		UserDTO loggedDTO = null;
		return new ResponseEntity<UserDTO>(loggedDTO,HttpStatus.OK);
	}

	@RequestMapping(value="/firstTimeChangePass/{newPass}/{oldPass}",method = RequestMethod.PUT)
	public ResponseEntity<UserDTO> firstTimeChangePass(@PathVariable String newPass,@PathVariable String oldPass, HttpServletRequest request) {
	    User user = (User)request.getSession().getAttribute("loggedUser");
	    User user1 = userService.firstTimeChangePass(oldPass, newPass, user);
	    return new ResponseEntity<UserDTO>(new UserDTO(user1),HttpStatus.OK);
	}
	
	@RequestMapping(value="/search/{name}/{surname}")
	public ResponseEntity<List<UserDTO>> searchUsers(@PathVariable String name, @PathVariable String surname){
		List<User> searched = userService.searchUsersStartingWith(name, surname);
		List<UserDTO> searcheddto = new ArrayList<UserDTO>();
		for(User user : searched) {
			searcheddto.add(new UserDTO(user));
		}
		if(searched.size() != 0) {
			return new ResponseEntity<List<UserDTO>>(searcheddto,HttpStatus.OK);
		}else {
			return new ResponseEntity<List<UserDTO>>(searcheddto,HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value="/sendFriendRequest/{receiverId}",method = RequestMethod.GET)
	public ResponseEntity<String> sendFriendRequest(@PathVariable Long receiverId,HttpServletRequest request){
		User sender = (User) request.getSession().getAttribute("loggedUser");		
		User receiver = userService.sendFriendRequest(sender.getId(), receiverId);
		if(receiver!=null){
			return new ResponseEntity<String>("zahtev uspesno poslat",HttpStatus.ACCEPTED);
		}
		else{
			return new ResponseEntity<String>("vec poslat",HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value="/approveFriendRequest/{pendingId}",method = RequestMethod.GET)
	public ResponseEntity<String> approveFriendRequest(@PathVariable Long pendingId, HttpServletRequest request){
		User user = (User) request.getSession().getAttribute("loggedUser");
		userService.approveFriendRequest(pendingId, user.getId());
		return new ResponseEntity<String>("request approved",HttpStatus.ACCEPTED);
	}
	
	
	@RequestMapping(value="/getFriends/{id}" , method = RequestMethod.GET)
	public ResponseEntity<List<UserDTO>> getFriends(@PathVariable Long id){
		List<User> friends = userService.getFriends(id);
		List<UserDTO> friendsDTO = new ArrayList<UserDTO>();
		for(User friend : friends) {
			friendsDTO.add(new UserDTO(friend));
		}
		return new ResponseEntity<List<UserDTO>>(friendsDTO,HttpStatus.OK);
	}
	
	@RequestMapping(value="/getRequests/{id}",method=RequestMethod.GET)
	public ResponseEntity<List<UserDTO>> getRequests(@PathVariable Long id){
		List<User> requests = userService.getFriendRequests(id);
		List<UserDTO> requestsDTO = new ArrayList<UserDTO>();
		for(User request : requests) {
			requestsDTO.add(new UserDTO(request));
		}
		return new ResponseEntity<List<UserDTO>>(requestsDTO,HttpStatus.OK);
	}
	
	@RequestMapping(value="/declineRequest/{pendingId}", method=RequestMethod.GET)
	public ResponseEntity<UserDTO> declineRequest(@PathVariable Long pendingId,HttpServletRequest request){
		User user = (User) request.getSession().getAttribute("loggedUser");
		if(user != null) {
			User deleted = userService.declineFriendRequest(pendingId, user.getId());
			UserDTO deletedDTO = new UserDTO(deleted);
			return new ResponseEntity<UserDTO>(deletedDTO,HttpStatus.OK);
		}
		UserDTO deldto = null;
		return new ResponseEntity<UserDTO>(deldto,HttpStatus.BAD_GATEWAY);
	}
	
	@RequestMapping(value="/removeFriend/{friendId}",method=RequestMethod.GET)
	public ResponseEntity<UserDTO> removeFriend(@PathVariable Long friendId, HttpServletRequest request){
		User logged = (User) request.getSession().getAttribute("loggedUser");
		User removed = userService.removeFriend(friendId, logged.getId());
		UserDTO removedDTO = new UserDTO(removed);
		return new ResponseEntity<UserDTO>(removedDTO,HttpStatus.OK);
	}

	@RequestMapping(value = "/test/",
			method = RequestMethod.GET
			)
	public void Test() {
		System.out.println("testiraj");
	}
	
}
