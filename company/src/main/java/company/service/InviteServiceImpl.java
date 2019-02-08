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
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import company.domain.DestinationTime;
import company.domain.Invite;
import company.domain.Reservation;
import company.domain.Seat;
import company.domain.User;
import company.repository.InviteRepository;
import company.repository.ReservationRepository;
import company.repository.UserRepository;

@Service
public class InviteServiceImpl implements InviteService{
	
	@Autowired
	private InviteRepository inviterepository;
	
	@Autowired
	private ReservationRepository reservationrepository;
	
	@Autowired
	private UserRepository userrepository;
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	@Autowired
	private Environment env;
	
	@Override
	public Invite save(Invite invite) {
		return inviterepository.save(invite);
	}

	@Override
	public Invite sendInvite(Long reservationId, Long userId) {
		Reservation reservation = reservationrepository.findOne(reservationId);
		User receiver = userrepository.findOne(userId);
		Hibernate.initialize(reservation.getSeats());
		Hibernate.initialize(reservation.getInvites());
		List<Invite> invites = new ArrayList<Invite>();
		List<Seat> seats = new ArrayList<Seat>();
		invites = generateInvitesClone(reservationId);
		seats = generateSeatsClone(reservationId);
		for(int i=0 ; i<invites.size();i++){
			if(seats.contains(invites.get(i).getSeat())){
				seats.remove(invites.get(i).getSeat());
			}
		}
		if(seats.size()>0){
			Invite invite = new Invite(receiver,reservation,seats.get(0),false);
			inviterepository.save(invite);
			Hibernate.initialize(reservation.getInvites());
			reservation.getInvites().add(invite);
			reservationrepository.save(reservation);
			return invite;
		}
		return null;
	}

	@Override
	public Invite acceptInvite(Long inviteId) {
		Invite invite = inviterepository.findOne(inviteId);
		invite.setAccepted(true);
		inviterepository.save(invite);
		return invite;
	}

	@Override
	@Async
	public void sendInviteMail(Long userId, Long inviteId) {
		User user = userrepository.findOne(userId);
		Invite invite = inviterepository.findOne(inviteId);
		Reservation reservation = invite.getReservation();
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo(user.getEmail());
		mail.setFrom(env.getProperty("spring.mail.username"));
		mail.setSubject("Poziv na putovanje");
		mail.setText("Pozdrav " + user.getName() +"\n Vaš prijatelj "+reservation.getOwner().getName()+" "+reservation.getOwner().getSurname()+
		" Vam šalje poziv da mu se pridružite na putovanju: "+reservation.getDestinationTime().getDestinationDate().getDestination().getName()+
		"\n Da potvrdite polazak pritisnite na link"+",\n\n http://localhost:8008/api/invites/"+invite.getId()+"/accept"
				+ "\n\n Da odustanete od polazka pritisnite na link"+",\n\n http://localhost:8008/api/invites/"+invite.getId()+"/decline");
		javaMailSender.send(mail);
	}
	
	public List<Invite> generateInvitesClone(Long reservationId){
		Reservation reservation = reservationrepository.findOne(reservationId);
		Hibernate.initialize(reservation.getInvites());
		List<Invite> invites = new ArrayList<Invite>();
		for(int i = 0;i<reservation.getInvites().size();i++){
			invites.add(reservation.getInvites().get(i));
		}
		return invites;
	}
	
	public List<Seat> generateSeatsClone(Long reservationId){
		Reservation reservation = reservationrepository.findOne(reservationId);
		Hibernate.initialize(reservation.getSeats());
		List<Seat> seats = new ArrayList<Seat>();
		for(int i = 0;i<reservation.getSeats().size();i++){
			seats.add(reservation.getSeats().get(i));
		}
		return seats;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public Invite declineInvite(Long inviteId) {
		Invite invite = inviterepository.findOne(inviteId);
		Reservation reservation = invite.getReservation();
		DestinationTime dt = reservation.getDestinationTime();
		dt.getTakenSeats().remove(invite.getSeat());
		reservation.getSeats().remove(invite.getSeat());
		reservation.getInvites().remove(invite);
		reservation.setTotalprice(reservation.getSeats().size()*dt.getPrice());
		inviterepository.delete(inviteId);
		//reservationrepository.save(reservation);
		return invite;
	}

	@Override
	public Invite findOne(Long inviteId) {
		return inviterepository.findOne(inviteId);
	}

}
