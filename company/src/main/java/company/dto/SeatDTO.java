package company.dto;

import company.domain.Seat;

public class SeatDTO {
	private Long id;
	private int row;
	private int seatInRow;
	private FlightDTO flight;
	
	public SeatDTO(Seat seat){
		this.id = seat.getId();
		this.row = seat.getRow();
		this.seatInRow = seat.getSeatInRow();
		this.flight = new FlightDTO(seat.getFlight());
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

	public FlightDTO getFlight() {
		return flight;
	}

	public void setFlight(FlightDTO flight) {
		this.flight = flight;
	}
	
	
	
}
