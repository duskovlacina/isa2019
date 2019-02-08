package company.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import company.domain.Invite;
import company.domain.Reservation;

@Repository
public interface InviteRepository extends JpaRepository<Invite,Long>{
	
	List<Invite> findByReservation(Reservation res);
	
}