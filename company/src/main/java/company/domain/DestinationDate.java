package company.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="destinationDate")
public class DestinationDate {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@ManyToOne(targetEntity = Destination.class)
	private Destination destination;
	
	@Column(nullable = false)
	@Temporal(TemporalType.DATE)
	private Date destinationDate;
	
	@OneToMany
	private List<DestinationTime> destinationTime;
	
	public DestinationDate(){}
	
	public DestinationDate(Destination destination, Date pdate) {
		this.destination = destination;
		this.destinationDate = pdate;
		this.destinationTime = new ArrayList<DestinationTime>();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Destination getDestination() {
		return destination;
	}

	public void setDestination(Destination destination) {
		this.destination = destination;
	}

	public Date getDestinationDate() {
		return destinationDate;
	}

	public void setDestinationDate(Date destinationDate) {
		this.destinationDate = destinationDate;
	}

	public List<DestinationTime> getDestinationTime() {
		return destinationTime;
	}

	public void setDestinationTime(List<DestinationTime> destinationTime) {
		this.destinationTime = destinationTime;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	

}
