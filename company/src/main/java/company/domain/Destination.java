package company.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Destination implements Serializable{

private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(nullable = false)
	private String name;
	
	@Column(nullable = true)
	private float averageRating;
	
	@ElementCollection
	private List<Integer> grades; 
	
	
	@OneToMany(targetEntity = DestinationDate.class)
	private List<DestinationDate> destDate;
	
	@ManyToOne(targetEntity = AvioCompany.class)
	private AvioCompany avioCompany;
	
	
	public Destination() {}
	
	
	public Destination(String name, float averageRating, AvioCompany avioCompany) {
		this.name = name;
		this.averageRating = averageRating;
		this.avioCompany = avioCompany;
		this.destDate = new ArrayList<DestinationDate>();
		this.grades = new ArrayList<Integer>();
	}
	
	

	
	public float getAverageRating() {
		return averageRating;
	}


	public void setAverageRating(float averageRating) {
		this.averageRating = averageRating;
	}


	public List<Integer> getGrades() {
		return grades;
	}


	public void setGrades(List<Integer> grades) {
		this.grades = grades;
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

	public List<DestinationDate> getDestDate() {
		return destDate;
	}

	public void setDestDate(List<DestinationDate> destDate) {
		this.destDate = destDate;
	}

	public AvioCompany getAvioCompany() {
		return avioCompany;
	}

	public void setAvioCompany(AvioCompany avioCompany) {
		this.avioCompany = avioCompany;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
}
