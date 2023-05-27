package com.customer.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.customer.api.dto.DtoCustomerList;



@Repository
public interface RepoCustomerList extends JpaRepository<DtoCustomerList, Integer>{

	@Query(value = "SELECT * FROM customer WHERE status = :status", nativeQuery = true)
	List<DtoCustomerList> getCustomers(@Param ("status") Integer status);
	
	List<DtoCustomerList> findByStatus(Integer id);
}
