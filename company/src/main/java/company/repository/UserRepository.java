package company.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import company.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User,Long>{
	
	User findById(Long id);
	
	User findByEmail(String email);
	
	List<User> findByName(String name);
	
	List<User> findBySurname(String surname);
	
	List<User> findByNameAndSurname(String name, String surname);
	
	List<User> findByNameContaining(String name);
	
	List<User> findBySurnameContaining(String surname);
	
	List<User> findByNameContainingAndSurnameContaining(String name, String surname);
}

