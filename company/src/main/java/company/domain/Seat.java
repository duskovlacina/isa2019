package company.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Seat implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(nullable = false)
	private int row;
	
	@Column(nullable = false)
	private int seatInRow;
	
	@ManyToOne
	private Flight flight;
	
	public Seat(){}
	
	public Seat(int row, int seatInRow, Flight flight){
		this.row = row;
		this.seatInRow = seatInRow;
		this.flight = flight;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getSeatInRow() {
		return seatInRow;
	}

	public void setSeatInRow(int seatInRow) {
		this.seatInRow = seatInRow;
	}

	public Flight getFlight() {
		return flight;
	}

	public void setFlight(Flight flight) {
		this.flight = flight;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	

}
