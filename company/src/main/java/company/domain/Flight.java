package company.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Flight implements Serializable{
	
private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(nullable = false)
	private int flightId;
	
	@Column(nullable = false)
	private int rows;
	
	@Column(nullable = false)
	private int seatsPerRow;
	
	@OneToMany(targetEntity = Seat.class)
	private List<Seat> seats;
	
	@ManyToOne(targetEntity = AvioCompany.class)
	private AvioCompany avioCompany;
	
	public Flight(){}
	
	public Flight(int flightId,int rows, int seatsPerRow, AvioCompany avioCompany){
		this.flightId = flightId;
		this.rows = rows;
		this.seatsPerRow = seatsPerRow;
		this.avioCompany = avioCompany;
		this.seats = new ArrayList<Seat>();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getFlightId() {
		return flightId;
	}

	public void setFlightId(int flightId) {
		this.flightId = flightId;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public int getSeatsPerRow() {
		return seatsPerRow;
	}

	public void setSeatsPerRow(int seatsPerRow) {
		this.seatsPerRow = seatsPerRow;
	}

	public List<Seat> getSeats() {
		return seats;
	}

	public void setSeats(List<Seat> seats) {
		this.seats = seats;
	}

	public AvioCompany getAvioCompany() {
		return avioCompany;
	}

	public void setAvioCompany(AvioCompany avioCompany) {
		this.avioCompany = avioCompany;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	


}
