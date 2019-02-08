package company.dto;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Hibernate;

import company.domain.Reservation;

public class ReservationDTO {
	
	private Long id;
	private UserDTO owner;
	private DestinationTimeDTO destinationTime;
	private List<SeatDTO> seats;
	private double totalprice;
	private boolean visited;
	
	public ReservationDTO(Reservation reservation) {
		this.id = reservation.getId();
		this.owner = new UserDTO(reservation.getOwner());
		this.destinationTime = new DestinationTimeDTO(reservation.getDestinationTime());
		this.seats = new ArrayList<SeatDTO>();
		Hibernate.initialize(reservation.getSeats());
		for(int i=0;i<reservation.getSeats().size();i++){
			this.seats.add(new SeatDTO(reservation.getSeats().get(i)));
		}
		this.totalprice = reservation.getTotalprice();
		this.visited = reservation.isVisited();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public UserDTO getOwner() {
		return owner;
	}

	public void setOwner(UserDTO owner) {
		this.owner = owner;
	}

	public DestinationTimeDTO getDestinationTime() {
		return destinationTime;
	}

	public void setDestinationTime(DestinationTimeDTO destinationTime) {
		this.destinationTime = destinationTime;
	}

	public List<SeatDTO> getSeats() {
		return seats;
	}

	public void setSeats(List<SeatDTO> seats) {
		this.seats = seats;
	}

	public double getTotalprice() {
		return totalprice;
	}

	public void setTotalprice(double totalprice) {
		this.totalprice = totalprice;
	}

	public boolean isVisited() {
		return visited;
	}

	public void setVisited(boolean visited) {
		this.visited = visited;
	}
	
	

	
	
}
