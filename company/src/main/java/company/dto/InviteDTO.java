package company.dto;

import company.domain.Invite;

public class InviteDTO {
	
	private Long id;
	private ReservationDTO reservation;
	private UserDTO invitedUser;
	private SeatDTO seat;
	private boolean accepted;
	
	public InviteDTO(Invite invite) {
		this.id = invite.getId();
		this.reservation = new ReservationDTO(invite.getReservation());
		this.invitedUser = new UserDTO(invite.getInvitedUser());
		this.seat = new SeatDTO(invite.getSeat());
		this.accepted = invite.isAccepted();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ReservationDTO getReservation() {
		return reservation;
	}

	public void setReservation(ReservationDTO reservation) {
		this.reservation = reservation;
	}

	public UserDTO getInvitedUser() {
		return invitedUser;
	}

	public void setInvitedUser(UserDTO invitedUser) {
		this.invitedUser = invitedUser;
	}

	public SeatDTO getSeat() {
		return seat;
	}

	public void setSeat(SeatDTO seat) {
		this.seat = seat;
	}

	public boolean isAccepted() {
		return accepted;
	}

	public void setAccepted(boolean accepted) {
		this.accepted = accepted;
	}
	
}
