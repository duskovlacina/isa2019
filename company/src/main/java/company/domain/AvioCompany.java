package company.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
public class AvioCompany implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(nullable = false)
	private String name;
	
	@Column(nullable = false)
	private String address;
	
	@Column(nullable = true)
	private String description;
	
	@Column(nullable = true)
	private String averageGrade;
	
	@JsonIgnore
	@OneToMany(targetEntity = Flight.class)
	private List<Flight> flights;
	
	@OneToMany(targetEntity = Destination.class)
	private List<Destination> destinations;
	
	public AvioCompany(){
		
	}
	
	public AvioCompany(String name, String address, String description, String averageGrade){
		this.name = name;
		this.address = address;
		this.description = description;
		this.averageGrade = averageGrade;
		this.flights = new ArrayList<Flight>();
		this.destinations = new ArrayList<Destination>();
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

	public List<Flight> getFlights() {
		return flights;
	}

	public void setFlights(List<Flight> flights) {
		this.flights = flights;
	}

	public List<Destination> getDestinations() {
		return destinations;
	}

	public void setDestinations(List<Destination> destinations) {
		this.destinations = destinations;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	

}
