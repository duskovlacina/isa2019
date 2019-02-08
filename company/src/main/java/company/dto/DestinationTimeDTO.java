package company.dto;

import java.util.Date;

import company.domain.DestinationTime;



public class DestinationTimeDTO {

	private Long id;
	private Date time;
	private Date arrivalTime;
	private Date arrivalDate;
	private double flightTime;
	private double flightLength;
	private int transferNo;
	private String transferPlace;
	private double price;
	private DestinationDateDTO destinationDate;
	private FlightDTO flights;
	
	public DestinationTimeDTO(DestinationTime destinationTime) {
		
		this.id = destinationTime.getId();
		this.time = destinationTime.getTime();
		this.arrivalTime = destinationTime.getArrivalTime();
		this.arrivalDate = destinationTime.getArrivalDate();
		this.flightTime = destinationTime.getFlightTime();
		this.flightLength = destinationTime.getFlightLength();
		this.transferNo = destinationTime.getTransferNo();
		this.transferPlace = destinationTime.getTransferPlace();
		this.price = destinationTime.getPrice();
		this.destinationDate = new DestinationDateDTO(destinationTime.getDestinationDate());
		this.flights = new FlightDTO(destinationTime.getFlights());

	}
	
	

	public DestinationDateDTO getDestinationDate() {
		return destinationDate;
	}



	public void setDestinationDate(DestinationDateDTO destinationDate) {
		this.destinationDate = destinationDate;
	}



	public FlightDTO getFlights() {
		return flights;
	}



	public void setFlights(FlightDTO flights) {
		this.flights = flights;
	}



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
	
	
}
