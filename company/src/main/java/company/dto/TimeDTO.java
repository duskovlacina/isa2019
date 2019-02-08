package company.dto;

import java.util.Date;

import company.domain.DestinationTime;

public class TimeDTO {

	private Date time;
	private Date arrivalTime;
	private Date arrivalDate;
	private double flightTime;
	private double flightLength;
	private int transferNo;
	private String transferPlace;
	private double price;
	
	public TimeDTO() {
		
	}
	
	public TimeDTO(DestinationTime destinationTime) {
		this.time = destinationTime.getTime();
		this.arrivalDate = destinationTime.getArrivalDate();
		this.arrivalTime = destinationTime.getArrivalTime();	
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
