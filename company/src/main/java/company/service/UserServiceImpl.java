package company.service;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import company.domain.User;
import company.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	@Autowired
	private Environment env;

	@Override
	public User save(User user) {
		User existing = userRepository.findByEmail(user.getEmail());
		if(existing == null){
			userRepository.save(user);
			return user;
		}
		return null;
	}

	@Override
	@Async
	public void sendVerificationMail(User user) {
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo(user.getEmail());
		mail.setFrom(env.getProperty("spring.mail.username"));
		mail.setSubject("Link za verifikaciju naloga");
		mail.setText("Pozdrav " + user.getName() + ",\n\n http://localhost:8008/api/users/verify/"+user.getId()+"");
		javaMailSender.send(mail);		
	}

	@Override
	public boolean verifyEmail(Long id) {
		User user = userRepository.findById(id);
		user.setVerified(true);
		userRepository.save(user);
		return true;
	}

	@Override
	public User signIn(User user) {
		User user2 = userRepository.findByEmail(user.getEmail());
		if(user2 != null) {
			if(user.getPassword().equals(user2.getPassword()) && user2.isVerified() == true) {
				return user2;
			}
		}
		return null;
	}

	@Override
	public User modifyUser(User user, Long id) {
		User old = userRepository.findById(id);
		if(user.getEmail() != null){
			old.setEmail(old.getEmail());
		}
		if(user.getName() != null){
			old.setName(user.getName());
		}
		if(user.getPassword() != null){
			old.setPassword(user.getPassword());
		}
		if(user.getSurname()!=null){
			old.setSurname(user.getSurname());
		}
		if(user.getCity()!=null){
			old.setCity(user.getCity());
		}
		if(user.getPhone()!=null){
			old.setPhone(user.getPhone());
		}
		if(user.getUsertype()!=null){
			old.setUsertype(user.getUsertype());
		}
		
		//userRepository.delete(old);
		return userRepository.save(old);
	}

	@Override
	public List<User> findAll() {
		return userRepository.findAll();
	}

	@Override
	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public User findById(Long id) {
		return userRepository.findById(id);
	}

	@Override
	public List<User> searchUsers(String name, String surname) {
		if(!name.equals("nema") && !surname.equals("nema")) {
			return userRepository.findByNameAndSurname(name, surname);
		}else if(name.equals("nema") && !surname.equals("nema")) {
			return userRepository.findBySurname(surname);
		}else if(!name.equals("nema") && surname.equals("nema")) {
			return userRepository.findByName(name);
		}else {
			return userRepository.findAll();
		}
	}

	@Override
	public List<User> searchUsersStartingWith(String name, String surname) {
		if(!name.equals("nema") && !surname.equals("nema")) {
			return userRepository.findByNameContainingAndSurnameContaining(name, surname);
		}else if(name.equals("nema") && !surname.equals("nema")) {
			return userRepository.findBySurnameContaining(surname);
		}else if(!name.equals("nema") && surname.equals("nema")) {
			return userRepository.findByNameContaining(name);
		}else {
			return userRepository.findAll();
		}
	}

	@Override
	public User firstTimeChangePass(String oldPass, String newPass, User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User sendFriendRequest(Long sender, Long receiver) {
		User user = userRepository.findById(receiver);
		User senderuser = userRepository.findById(sender);
		Hibernate.initialize(user.getReceivedRequests());
		if(user!=null && sender!=receiver) {
			for(User req : user.getReceivedRequests()) {
				if(req.getId() == sender) {
					return null;
				}
			}
			for(User req : getFriends(receiver)) {
				if(req.getId() == sender) {
					return null;
				}
			}
			user.getReceivedRequests().add(senderuser);
			userRepository.save(user);
			return user;
		}
		return null;
	}

	@Override
	public User approveFriendRequest(Long pending, Long userId) {
		User receiverUser = userRepository.findById(userId);
		User senderUser = userRepository.findById(pending);
		Hibernate.initialize(receiverUser.getReceivedRequests());
		Hibernate.initialize(senderUser.getReceivedRequests());
		Hibernate.initialize(receiverUser.getFriends());
		Hibernate.initialize(receiverUser.getFriendOf());
		Hibernate.initialize(senderUser.getFriends());
		Hibernate.initialize(senderUser.getFriendOf());
		boolean flag = false;
		for(User user:receiverUser.getReceivedRequests()) {
			if(user.getId() == pending) {
				flag =true;
			}
		}
		if(receiverUser != null && flag == true) {
			receiverUser.getReceivedRequests().remove(senderUser);
			senderUser.getReceivedRequests().remove(receiverUser);
			receiverUser.getFriends().add(senderUser);
			userRepository.save(receiverUser);
			return receiverUser;
		}
		return null;
	}

	@Override
	public User removeFriend(Long friendId, Long userId) {
		User logged = userRepository.findById(userId);
		User friend = userRepository.findById(friendId);
		Hibernate.initialize(logged.getFriends());
		Hibernate.initialize(logged.getFriendOf());
		List<User> joined = new ArrayList<User>();
		List<User> friends = logged.getFriends();
		List<User> friendof = logged.getFriendOf();
		joined.addAll(friends);
		joined.addAll(friendof);
		for(int i=0;i<friends.size();i++) {
			if(friends.get(i).getId() == friendId) {
				friends.remove(i);
			}
		}
		for(int i=0;i<friendof.size();i++) {
			if(friendof.get(i).getId() == friendId) {
				friendof.remove(i);
			}
		}
		userRepository.save(logged);
		userRepository.save(friend);
		return friend;
	}

	@Override
	public User declineFriendRequest(Long pending, Long userId) {
		User receiverUser = userRepository.findById(userId);
		User senderUser = userRepository.findById(pending);
		Hibernate.initialize(receiverUser.getReceivedRequests());
		receiverUser.getReceivedRequests().remove(senderUser);
		userRepository.save(receiverUser);
		return senderUser;
	}

	@Override
	public List<User> getFriends(Long id) {
		User user = userRepository.findById(id);
		Hibernate.initialize(user.getFriends());
		Hibernate.initialize(user.getFriendOf());
		if(user != null) {
			List<User> joined = new ArrayList<User>();
			List<User> friends = user.getFriends();
			List<User> friendof = user.getFriendOf();
			joined.addAll(friends);
			joined.addAll(friendof);
			return joined;
		}
		return null;
	}

	@Override
	public List<User> getFriendRequests(Long id) {
		User user = userRepository.findById(id);
		Hibernate.initialize(user.getReceivedRequests());
		if(user!=null) {
			List<User> requests = user.getReceivedRequests();
			return requests;
		}
		return null;
	}

	
}
