package company.service;

import java.util.List;


import company.domain.User;

public interface UserService {
	
	User save(User user);
	
	void sendVerificationMail(User user);
	
	boolean verifyEmail(Long id);
	
	User signIn(User user);
	
	User modifyUser(User user, Long id);
	
	List<User> findAll();
	
	User findByEmail(String email);
	
	User findById(Long id);
	
	List<User> searchUsers(String name,String surname);
	
	
	User sendFriendRequest(Long sender,Long receiver);
	
	User approveFriendRequest(Long pending,Long userId);
	
	User removeFriend(Long friendId, Long userId);
	
	User declineFriendRequest(Long pending, Long userId);
	
	List<User> getFriends(Long id);
	
	List<User> getFriendRequests(Long id);
	
	List<User> searchUsersStartingWith(String name,String surname);
	
	User firstTimeChangePass(String oldPass,String newPass,User user);
}
