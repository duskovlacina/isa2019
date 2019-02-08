package company.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import company.domain.DestinationTime;
import company.domain.Invite;
import company.domain.Reservation;
import company.domain.User;
import company.repository.DestinationTimeRepository;
import company.repository.InviteRepository;
import company.repository.ReservationRepository;
import company.repository.UserRepository;

@Service
public class ReservationServiceImpl implements ReservationService {
	
	@Autowired
	private ReservationRepository reservationrepository;
	
	@Autowired
	private InviteRepository inviterepository;
	
	@Autowired
	private DestinationTimeRepository dtrepository;
	
	@Autowired
	private UserRepository userrepository;
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	@Autowired
	private Environment env;
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ, rollbackFor = Exception.class)
	public Reservation save(Reservation reservation) {
		return reservationrepository.save(reservation);
	}

	@Override
	public List<Reservation> getReservationsForUser(Long userId) {
		User user = userrepository.findOne(userId);
		return reservationrepository.findByOwnerAndVisited(user,false);
	}

	@Override
	public List<Reservation> getVisitedReservationsForUser(Long userId) {
		User user = userrepository.findById(userId);
		return reservationrepository.findByOwnerAndVisited(user, true);
	}

	@Override
	public Reservation getReservation(Long resId) {
		return reservationrepository.findOne(resId);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public Reservation cancelReservation(Long resId) {
		Date date = new Date();
		boolean invalid = false;
		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		Calendar cal3 = Calendar.getInstance();
		cal1.setTime(date);
		java.sql.Date sqlDateNow = new java.sql.Date(date.getTime());
		//
		Reservation res = reservationrepository.findOne(resId);
		java.sql.Date sqlDatePtime = new java.sql.Date(res.getDestinationTime().getTime().getTime());
		java.sql.Date sqlDatePdate = new java.sql.Date(res.getDestinationTime().getDestinationDate().getDestinationDate().getTime());
		cal2.setTime(sqlDatePtime);
		cal3.setTime(sqlDatePdate);
		System.out.println("Today: "+sqlDateNow +" Projection:"+sqlDatePdate +" Comparation"+calendarCompareSameDate(cal1, cal3));
		if(sqlDatePdate.compareTo(sqlDateNow) >= 0 || calendarCompareSameDate(cal1,cal3) == true) {
			System.out.println("Datum projekcije je posle danasnjeg dana");
			cal2.add(Calendar.MINUTE, -30);
			System.out.println("Oduzimanje 30 min: "+cal2.get(Calendar.HOUR_OF_DAY)+":"+cal2.get(Calendar.MINUTE));
			if(calendarCompareSameDate(cal1,cal3) == true) {
				if(cal1.get(Calendar.HOUR_OF_DAY) > cal2.get(Calendar.HOUR_OF_DAY)) {
					invalid = true;
				}else if(cal2.get(Calendar.HOUR_OF_DAY) == cal1.get(Calendar.HOUR_OF_DAY)) {
					if(cal2.get(Calendar.MINUTE) < cal1.get(Calendar.MINUTE)) {
						invalid = true;
					}
				}
			}
		}else {
			invalid = true;
		}
		
		if(!invalid) {
			DestinationTime dt = res.getDestinationTime();
			User user = userrepository.findOne(res.getOwner().getId());
			Hibernate.initialize(user.getReservations());
			Hibernate.initialize(res.getSeats());
			Hibernate.initialize(res.getInvites());
			Hibernate.initialize(dt.getTakenSeats());
			res.getInvites().clear();
			List<Invite> invites = inviterepository.findByReservation(res);
			inviterepository.delete(invites);
			dt.getTakenSeats().removeAll(res.getSeats());
			user.getReservations().remove(res);
			userrepository.save(user);
			reservationrepository.delete(resId);
			dtrepository.save(dt);
			return res;
		}else {
			return null;
		}
	}
	
	public boolean calendarCompareSameDate(Calendar c1, Calendar c2) {
		if(c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR) && c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH) && c1.get(Calendar.DAY_OF_YEAR) == c2.get(Calendar.DAY_OF_YEAR)) {
			return true;
		}
		return false;
	}

	@Override
	@Scheduled(cron = "*/10 * * * * *") 
	public void checkVisits() {
		Date date = new Date();
		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		Calendar cal3 = Calendar.getInstance();
		cal1.setTime(date);
		List<Reservation> reservations = reservationrepository.findAll();
		for(Reservation res : reservations) {
			boolean flag = false;
			cal2.setTime(res.getDestinationTime().getDestinationDate().getDestinationDate());
			cal3.setTime(res.getDestinationTime().getTime());
			cal3.add(Calendar.MINUTE, -30);
			if(res.isVisited() == false && (cal1.after(cal2) || calendarCompareSameDate(cal1,cal2)) ) {
				if(cal3.get(Calendar.HOUR_OF_DAY) == cal1.get(Calendar.HOUR_OF_DAY)) {
					if(cal3.get(Calendar.MINUTE) < cal1.get(Calendar.MINUTE)) {
						flag = true;
					}
				}else {
					flag = true;
				}
			}
			if(flag) {
				res.setVisited(true);
				//!!!!!!!!!!!!!!!!!!!!!!!!!!!! za +5 poena na rezervaciji!!!!!!!!!!!
 				/*User user = res.getOwner();
				user.setPoints(user.getPoints()+5);
				setMembership(user); ----dodaj kraj kooom!*/
				reservationrepository.save(res);
			}
		}
		
	}

	@Override
	@Async
	@Transactional
	public void sendReservationMail(Long userId, Long reservationId) {
		User user = userrepository.findOne(userId);
		Reservation reservation = reservationrepository.findOne(reservationId);
		SimpleMailMessage mail = new SimpleMailMessage();
		Hibernate.initialize(reservation.getSeats());
		mail.setTo(user.getEmail());
		mail.setFrom(env.getProperty("spring.mail.username"));
		mail.setSubject("Potvrda rezervacije");
		mail.setText("Pozdrav " + user.getName() +"\n\n Uspešno ste izvršili rezervaciju "+reservation.getSeats().size() +" sedišta za "+
		reservation.getDestinationTime().getDestinationDate().getDestination().getName()+
		"\n za let dana "+reservation.getDestinationTime().getDestinationDate().getDestinationDate() +" u "+reservation.getDestinationTime().getTime() +" časova."
				+ "\n\n Odustanak od rezervacije je moguć najkasnije 3 sata pre početka leta");
		javaMailSender.send(mail);
		
	}

	@Override
	public List<Reservation> getAll() {
		return reservationrepository.findAll();
	}


}
