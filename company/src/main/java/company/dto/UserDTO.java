package company.dto;

import company.domain.User;
import company.domain.UserType;

public class UserDTO {
	private Long id;
	private String email;
	private String name;
	private String surname;
	private String password;
	private String city;
	private String phone;
	private UserType usertype;
	private boolean changedPassword;
	
	public UserDTO(User user){
		this.id = user.getId();
		this.email=user.getEmail();
		this.name = user.getName();
		this.surname = user.getSurname();
		this.password = user.getPassword();
		this.city = user.getCity();
		this.phone = user.getPhone();
		this.usertype = user.getUsertype();
		this.changedPassword = user.isChangedPassword();
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public boolean isChangedPassword() {
		return changedPassword;
	}

	public void setChangedPassword(boolean changedPassword) {
		this.changedPassword = changedPassword;
	}
	
	
}