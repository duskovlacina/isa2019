package company.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class Invite implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@ManyToOne
	private Reservation reservation;
	
	@ManyToOne
	private User invitedUser;
	
	@OneToOne
	private Seat seat;
	
	@Column
	private boolean accepted;
	
	public Invite(){}
	
	public Invite(User invitedUser, Reservation reservation, Seat seat, boolean accepted){
		this.invitedUser = invitedUser;
		this.reservation = reservation;
		this.seat = seat;
		this.accepted = accepted;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getInvitedUser() {
		return invitedUser;
	}

	public void setInvitedUser(User invitedUser) {
		this.invitedUser = invitedUser;
	}

	public Seat getSeat() {
		return seat;
	}

	public void setSeat(Seat seat) {
		this.seat = seat;
	}

	public boolean isAccepted() {
		return accepted;
	}

	public void setAccepted(boolean accepted) {
		this.accepted = accepted;
	}

	public Reservation getReservation() {
		return reservation;
	}

	public void setReservation(Reservation reservation) {
		this.reservation = reservation;
	}
}
