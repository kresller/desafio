package br.com.kresller.desafio.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.validator.constraints.NotEmpty;

import br.com.kresller.desafio.util.Constants;

/**
 * Classe responsavel pela entidade Car
 */
@Entity
public class Car {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private int year;
	
	@NotEmpty(message = Constants.ERROR_MISSING_FIELDS)
	private String licensePlate;
	
	@NotEmpty(message = Constants.ERROR_MISSING_FIELDS)
	private String model;
	
	@NotEmpty(message = Constants.ERROR_MISSING_FIELDS)
	private String color;
	
	@Column(name="USER_ID")
	private int userId;
	
	private int qtdUso;
	
	public int getQtdUso() {
		return qtdUso;
	}
	public void setQtdUso(int qtdUso) {
		this.qtdUso = qtdUso;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	@ManyToOne( fetch = FetchType.EAGER )
	@JoinColumn(name="USER_ID",insertable=false,updatable=false)
	private User user;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public String getLicensePlate() {
		return licensePlate;
	}
	public void setLicensePlate(String licensePlate) {
		this.licensePlate = licensePlate;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
}
