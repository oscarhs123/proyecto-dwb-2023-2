package com.customer.api.entity;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;


@Entity
@Table(name = "customer")
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonProperty("customer_id")
	@Column(name  = "customer_id")
	private Integer customer_id;
	
	@Column(name = "name")
	@NotNull(message = "name is required")
	@JsonProperty("name")
	private String name;
	
	@Column(name = "surname")
	@NotNull(message = "name is required")
	@JsonProperty("surname")
	private String surname;
	
	@Column(name = "date_birth")
	@NotNull(message = "date birth is required")
	@JsonProperty("date_birth")
	private LocalDate date_birth;
	
	@Column(name = "rfc")
	@NotNull(message = "rfc is required")
	@JsonProperty("rfc")
	private String rfc;
	
	@Column(name = "mail")
	@NotNull(message = "mail is required")
	@JsonProperty("mail")
	private String mail;
	
	@Column(name = "address")
	@JsonProperty("address")
	private String address;
	
	@Min(value = 0, message = "status must be 0 or 1")
	@Max(value = 1, message = "status must be 0 or 1")
	@Column(name = "status")
	@JsonIgnore
	private Integer status;
	
	
	@OneToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "region_id", referencedColumnName = "region_id")
	private Region region;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "customer_image_id", referencedColumnName = "customer_image_id")
	private CustomerImage customerImage;

	
	public Integer getCustomer_id() {
		return customer_id;
	}

	
	public void setCustomer_id(Integer customer_id) {
		this.customer_id = customer_id;
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

	
	public LocalDate getDate_birth() {
		return date_birth;
	}

	
	public void setDate_birth(LocalDate date_birth) {
		this.date_birth = date_birth;
	}

	
	public String getRfc() {
		return rfc;
	}

	
	public void setRfc(String rfc) {
		this.rfc = rfc;
	}

	
	public String getMail() {
		return mail;
	}

	
	public void setMail(String mail) {
		this.mail = mail;
	}

	
	public String getAdress() {
		return address;
	}

	
	public void setAdress(String adress) {
		this.address = adress;
	}

	
	public Integer getStatus() {
		return status;
	}

	
	public void setStatus(Integer status) {
		this.status = status;
	}

	
	public Region getRegion() {
		return region;
	}

	public void setRegion(Region region) {
		this.region = region;
	}

	
	public CustomerImage getCustomer_image() {
		return customerImage;
	}

	
	public void setCustomer_image(CustomerImage customer_image) {
		this.customerImage = customer_image;
	}
}
