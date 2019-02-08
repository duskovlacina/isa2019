package company.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class DestinationTime implements Serializable {

private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@ManyToOne
	private DestinationDate destinationDate;
	
	@ManyToOne
	private Flight flights;
	
	@ManyToMany
	private List<Seat> takenSeats;
	
	@Column(nullable = false)
	@Temporal(TemporalType.TIME)
	private Date time;
	
	@Column(nullable = false)
	@Temporal(TemporalType.TIME)
	private Date arrivalTime;
	
	@Column(nullable = false)
	@Temporal(TemporalType.DATE)
	private Date arrivalDate;
	
	@Column(nullable = false)
	private double flightTime;
	
	@Column(nullable = false)
	private double flightLength;
	
	@Column(nullable = false)
	private int transferNo;
	
	@Column(nullable = false)
	private String transferPlace;
	
	@Column(nullable = false)
	private double price;
	
	public DestinationTime() {
		
	}
	
	public DestinationTime(DestinationDate destinationDate, Flight flight,Date time,Date arrivalTime, Date arrivalDate,
			double flightTime, double flightLength, int transferNo, String transferPlace, double price) {
		this.destinationDate = destinationDate;
	    this.flights = flight;
		this.time = time;
		this.arrivalTime = arrivalTime;
		this.arrivalDate = arrivalDate;
		this.flightTime = flightTime;
		this.flightLength = flightLength;
		this.transferNo = transferNo;
		this.transferPlace = transferPlace;
		this.price = price;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public DestinationDate getDestinationDate() {
		return destinationDate;
	}

	public void setDestinationDate(DestinationDate destinationDate) {
		this.destinationDate = destinationDate;
	}

	public Flight getFlights() {
		return flights;
	}

	public void setFlights(Flight flights) {
		this.flights = flights;
	}

	public List<Seat> getTakenSeats() {
		return takenSeats;
	}

	public void setTakenSeats(List<Seat> takenSeats) {
		this.takenSeats = takenSeats;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public Date getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(Date arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public Date getArrivalDate() {
		return arrivalDate;
	}

	public void setArrivalDate(Date arrivalDate) {
		this.arrivalDate = arrivalDate;
	}

	public double getFlightTime() {
		return flightTime;
	}

	public void setFlightTime(double flightTime) {
		this.flightTime = flightTime;
	}

	public double getFlightLength() {
		return flightLength;
	}

	public void setFlightLength(double flightLength) {
		this.flightLength = flightLength;
	}

	public int getTransferNo() {
		return transferNo;
	}

	public void setTransferNo(int transferNo) {
		this.transferNo = transferNo;
	}

	public String getTransferPlace() {
		return transferPlace;
	}

	public void setTransferPlace(String transferPlace) {
		this.transferPlace = transferPlace;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
	
		
}
