
package com.customer.api.entity;


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


@Entity


@Table(name = "region")
public class Region {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	//La columna a la que hace referencia de la tabla referenciada.
	@Column(name = "region_id")
	private Integer region_id;

	@NotNull
	@Column(name = "region")
	private String region;
	
	@Min(value = 0, message = "El status debe ser 0 o 1")
	@Max(value = 1, message = "El status debe ser 0 o 1")
	@Column(name = "status")
	@JsonIgnore
	private Integer status;
	
	
	
	public Region() {
		
	}

	
	public Integer getRegion_id() {
		return region_id;
	}


	public void setRegion_id(Integer region_id) {
		this.region_id = region_id;
	}


	public String getRegion() {
		return region;
	}


	public void setRegion(String region) {
		this.region = region;
	}


	public Integer getStatus() {
		return status;
	}


	public void setStatus(Integer status) {
		this.status = status;
	}


	@Override
	public String toString() {
		return "Region [region_id=" + region_id + ", region=" + region + ", status=" + status + "]";
	}
	
}
