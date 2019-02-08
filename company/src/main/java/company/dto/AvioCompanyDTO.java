package company.dto;

import company.domain.AvioCompany;

public class AvioCompanyDTO {
	
	private Long id;
	private String name;
	private String address;
	private String description;
	private String averageGrade;
	
	public AvioCompanyDTO(AvioCompany avioCompany) {
		this.id = avioCompany.getId();
		this.name = avioCompany.getName();
		this.address = avioCompany.getAddress();
		this.description = avioCompany.getDescription();
		this.averageGrade = avioCompany.getAverageGrade();		
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAverageGrade() {
		return averageGrade;
	}

	public void setAverageGrade(String averageGrade) {
		this.averageGrade = averageGrade;
	}
	
	
}
