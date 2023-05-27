package com.customer.api.repository;

import java.time.LocalDate;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.customer.api.entity.Customer;


public interface RepoCustomer extends JpaRepository<Customer, Integer>{

	/* Selecciona el usuario con rfc y con status s*/
	@Query(value = "SELECT * FROM customer WHERE status = :status AND rfc = :rfc", nativeQuery = true)
	Customer getCustomerByRFCAndStatus(@Param ("rfc") String rfc, @Param ("status") Integer status);
	
	@Transactional
	@Modifying
	@Query(value = "UPDATE customer SET "
			+ "name = :name, "
			+ "surname = :surname, "
			+ "date_birth = :date_birth, "
			+ "rfc = :rfc, "
			+ "mail = :mail, "
			+ "address = :address "
			+ "WHERE customer_id = :customer_id AND status = 1", nativeQuery = true)
	Integer updateCustomer(
			@Param("name") String name, 
			@Param("surname") String surname, 
			@Param("date_birth") LocalDate date_birth, 
			@Param("rfc") String rfc, 
			@Param("mail") String mail, 
			@Param("address") String address,
			@Param("customer_id") Integer customer_id);
	
	@Modifying
	@Transactional
	@Query (value = "UPDATE customer SET status = 0 WHERE customer_id = :customer_id AND status = 1", nativeQuery = true)
	Integer deleteByID(@Param ("customer_id") Integer customer_id);
	
	/* Actualiza la region de un customer*/
	@Modifying
	@Transactional
	@Query (value = "UPDATE customer SET region_id = :region_id WHERE customer_id= :customer_id AND status = 1", nativeQuery = true)
	Integer updateCustomerRegion(@Param ("region_id") Integer region_id, @Param ("customer_id") Integer customer_id);
}
