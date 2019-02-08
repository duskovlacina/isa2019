package company.dto;

import company.domain.Flight;

public class FlightDTO {

	private Long id;
	private int flightId;
	private int rows;
	private int seatsPerRow;
	private AvioCompanyDTO avioCompany;
	
	public FlightDTO(Flight flight) {
		this.id = flight.getId();
		this.flightId = flight.getFlightId();
		this.rows = flight.getRows();
		this.seatsPerRow = flight.getSeatsPerRow();
		this.avioCompany = new AvioCompanyDTO(flight.getAvioCompany());
	}
	
	

	public AvioCompanyDTO getAvioCompany() {
		return avioCompany;
	}



	public void setAvioCompany(AvioCompanyDTO avioCompany) {
		this.avioCompany = avioCompany;
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
	
	
}
