package company.service;

import java.util.List;

import company.domain.Reservation;

public interface ReservationService {
	
	Reservation save(Reservation reservation);
	
	List<Reservation> getReservationsForUser(Long userId);
	
	List<Reservation> getVisitedReservationsForUser(Long userId);
	
	Reservation getReservation(Long resId);
	
	Reservation cancelReservation(Long resId);
	
	void checkVisits();
	
	void sendReservationMail(Long userId, Long reservationId);
	
	List<Reservation> getAll();
}
