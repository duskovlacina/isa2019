package company.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

@Entity
public class User implements Serializable {

	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(nullable = false,unique=true)
	private String email;
	
	@Column(nullable = false)
	private String password;
	
	@Column(nullable = true)
	private String name;
	
	@Column(nullable = true)
	private String surname;
	
	@Column(nullable = false)
	private boolean verified;
	
	@Column(nullable = true)
	private String city;
	
	@Column(nullable = true)
	private String phone;
	
	@Column(nullable = false)
	private UserType usertype;
	
	@Column
	private boolean changedPassword;
	
	@ManyToMany
    @JoinTable(name="friends", joinColumns=@JoinColumn(name="personId"), inverseJoinColumns=@JoinColumn(name="friendId"))
    private List<User> friends;

    @ManyToMany
    @JoinTable(name="friends", joinColumns=@JoinColumn(name="friendId"), inverseJoinColumns=@JoinColumn(name="personId"))
    private List<User> friendOf; 

    @ManyToMany(targetEntity = User.class)
    @JoinTable(name="requests", joinColumns=@JoinColumn(name="receiver"))
    public List<User> receivedRequests;
    
    @OneToMany
    private List<Reservation> reservations;
	
	public User() {
		
	}
	
	public User(String email, String password, String name, String surname, String city, String phone,UserType usertype) {
		super();
		this.email = email;
		this.password = password;
		this.name = name;
		this.surname = surname;
		this.city = city;
		this.phone = phone;
		this.verified = false;
		this.usertype = usertype;
		this.changedPassword = false;
	}
	
	
	
	public List<Reservation> getReservations() {
		return reservations;
	}

	public void setReservations(List<Reservation> reservations) {
		this.reservations = reservations;
	}

	public boolean isChangedPassword() {
		return changedPassword;
	}

	public void setChangedPassword(boolean changedPassword) {
		this.changedPassword = changedPassword;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public boolean isVerified() {
		return verified;
	}

	public void setVerified(boolean verified) {
		this.verified = verified;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public UserType getUsertype() {
		return usertype;
	}

	public void setUsertype(UserType usertype) {
		this.usertype = usertype;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public List<User> getFriends() {
		return friends;
	}

	public void setFriends(List<User> friends) {
		this.friends = friends;
	}

	public List<User> getFriendOf() {
		return friendOf;
	}

	public void setFriendOf(List<User> friendOf) {
		this.friendOf = friendOf;
	}

	public List<User> getReceivedRequests() {
		return receivedRequests;
	}

	public void setReceivedRequests(List<User> receivedRequests) {
		this.receivedRequests = receivedRequests;
	}
	
	
	
	
	
}
