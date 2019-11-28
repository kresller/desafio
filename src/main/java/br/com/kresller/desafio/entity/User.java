package br.com.kresller.desafio.entity;


import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import br.com.kresller.desafio.util.Constants;

/**
 * Classe responsavel pela entidade User
 */
@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@NotEmpty(message = Constants.ERROR_MISSING_FIELDS)
	private String firstName;
	
	@NotEmpty(message = Constants.ERROR_MISSING_FIELDS)
	private String lastName;
	
	@NotEmpty(message = Constants.ERROR_MISSING_FIELDS)
	private String email;
	
	@NotNull(message = Constants.ERROR_MISSING_FIELDS)
	@Temporal(TemporalType.DATE )
	@DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
	private Date birthday;
	
	@NotEmpty(message = Constants.ERROR_MISSING_FIELDS)
	private String login;
	
	@NotEmpty(message = Constants.ERROR_MISSING_FIELDS)
	private String password;
	
	@NotEmpty(message = Constants.ERROR_MISSING_FIELDS)
	private String phone;
	
	@OneToMany(mappedBy="user")
	//@OneToMany(cascade= {CascadeType.ALL})
    //@JoinColumn(name="USER_ID")
	private List<Car> cars;
	
	@Temporal(TemporalType.DATE )
	@DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
	private Date createdAt;
	
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
	private Date lastLogin;

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public List<Car> getCars() {
		return cars;
	}

	public void setCars(List<Car> cars) {
		this.cars = cars;
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
}
