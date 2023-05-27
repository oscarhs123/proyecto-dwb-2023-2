package com.customer.api.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;


@Entity
@Table(name = "customer")
public class DtoCustomerList {

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
	
	@Column(name = "rfc")
	@NotNull(message = "rfc is required")
	@JsonProperty("rfc")
	private String rfc;
	
	@Min(value = 0, message = "status must be 0 or 1")
	@Max(value = 1, message = "status must be 0 or 1")
	@Column(name = "status")
	@JsonIgnore
	private Integer status;
}
