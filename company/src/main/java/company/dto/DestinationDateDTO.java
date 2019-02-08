package company.dto;

import java.time.LocalDate;
import java.util.Date;

import company.domain.DestinationDate;

public class DestinationDateDTO {

	private Long id;
	private Date destinationDate;
	private DestinationDTO destination;
	
	public DestinationDateDTO(DestinationDate destinationDate) {
		this.id = destinationDate.getId();
		this.destinationDate = destinationDate.getDestinationDate();
		this.destination = new DestinationDTO(destinationDate.getDestination());
	}
    

	public DestinationDTO getDestination() {
		return destination;
	}


	public void setDestination(DestinationDTO destination) {
		this.destination = destination;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDestinationDate() {
		return destinationDate;
	}

	public void setProjectionDate(Date destinationDate) {
		this.destinationDate = destinationDate;
	}
	
	

}
