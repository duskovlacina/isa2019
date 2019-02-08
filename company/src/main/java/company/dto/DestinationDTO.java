package company.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import company.domain.Destination;
import company.domain.DestinationDate;
import company.domain.DestinationTime;

public class DestinationDTO {
	

	private Long id;
	private String name;
	private AvioCompanyDTO avioCompany;
	private List<Date> destDate; 
	private List<TimeDTO> destinationTime;
	
	public DestinationDTO(Destination destination) {
		this.id = destination.getId();
		this.name = destination.getName();
		this.avioCompany = new AvioCompanyDTO(destination.getAvioCompany());
		this.destDate = new ArrayList<>();
		this.destinationTime = new ArrayList<>();
		for(DestinationDate dd : destination.getDestDate()) {
			destDate.add(dd.getDestinationDate());
			for(DestinationTime dt : dd.getDestinationTime()) {
				destinationTime.add(new TimeDTO(dt));
			}
		}
	}


	public List<Date> getDestDate() {
		return destDate;
	}





	public void setDestDate(List<Date> destDate) {
		this.destDate = destDate;
	}





	public List<TimeDTO> getDestinationTime() {
		return destinationTime;
	}





	public void setDestinationTime(List<TimeDTO> destinationTime) {
		this.destinationTime = destinationTime;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
