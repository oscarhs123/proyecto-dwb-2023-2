package com.customer.api.service;

import java.util.List;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.customer.api.dto.ApiResponse;
import com.customer.api.dto.DtoCustomerList;
import com.customer.api.entity.Customer;
import com.customer.api.entity.CustomerImage;
import com.customer.api.entity.Region;
import com.customer.api.repository.RepoCustomer;
import com.customer.api.repository.RepoCustomerList;
import com.customer.exception.ApiException;


@Service
public class SvcCustomerImp implements SvcCustomer {


	@Autowired
	RepoCustomer repo;
	
	@Autowired
	RepoCustomerList repoList;
	

	@Override
	public List<DtoCustomerList> getCustomers() {
		return repoList.getCustomers(1);
	}

	@Override
	public Customer getCustomer(String rfc) {
		Customer customer = repo.getCustomerByRFCAndStatus(rfc, 1);
		if (customer == null)
			throw new ApiException(HttpStatus.NOT_FOUND, "No such rfc exists!");
		return customer;
	}


	@Override
	public ApiResponse createCustomer(Customer in) {
		in.setStatus(1);
		in.setCustomer_image(new CustomerImage());
		try {
			//Intentamos guardar todo el json con cada atributo en donde pertenece.
			System.out.println(":): \n\n");
			repo.save(in);
		} catch (ConstraintViolationException e){
			//si no, es porque hubo un error en la validacion. Solo rfc y mail tienen validación de ser únicos (faltan checar cosas)
			System.out.println("Error: " + e.getLocalizedMessage()); 
			if (e.getLocalizedMessage().contains("rfc"))
				throw new ApiException(HttpStatus.BAD_REQUEST, "customer rfc already exists!");
			if (e.getLocalizedMessage().contains("mail"))
				throw new ApiException(HttpStatus.BAD_REQUEST, "customer mail already exists!");
		} catch (DataIntegrityViolationException e){
			System.out.println("Error de data integrity\n" +e.getLocalizedMessage());
			throw new ApiException(HttpStatus.NOT_FOUND, "customer already exists!");
		} catch (Exception e) {
			System.out.println("Error1 " + e.getMessage());
		}
		return new ApiResponse("customer created!");
	}

	@Override
	public ApiResponse updateCustomer(Customer in, Integer id) {
		//Como vamos a actualizar solamente no debemos de asignar status ni crear imagenes. directamente actualizamos
		try {
			//Intentamos actualizar todo el json con cada atributo en donde pertenece. OJOOOOOOOO
			if (repo.updateCustomer(in.getName(), in.getSurname(), in.getDate_birth(), in.getRfc(), in.getMail(), in.getAdress(), id)== 0)
				throw new ApiException(HttpStatus.NOT_FOUND, "id doesnt exist");
		} catch (ConstraintViolationException e){
			//si no, es porque hubo un error en la validacion. Solo rfc y mail tienen validación de ser únicos (faltan checar cosas)
			if (e.getLocalizedMessage().contains("rfc"))
				throw new ApiException(HttpStatus.BAD_REQUEST, "customer rfc already exists!");
			if (e.getLocalizedMessage().contains("mail"))
				throw new ApiException(HttpStatus.BAD_REQUEST, "customer mail already exists!");
		}
		return new ApiResponse("customer updated!");
	}

	@Override
	public ApiResponse deleteCustomer(Integer id) {
		if (repo.deleteByID(id) <= 0)
			throw new ApiException(HttpStatus.BAD_REQUEST, "customer could not be removed");
		return new ApiResponse("customer deleted!");
	}

	@Override
	public ApiResponse updateCustomerRegion(Region region, Integer id) {
		try {
			if (repo.updateCustomerRegion(region.getRegion_id(), id) > 0)
				return new ApiResponse("customer region updated!");
			else
				throw new ApiException(HttpStatus.BAD_REQUEST, "customer region could not be updated");
		} catch (DataIntegrityViolationException e){
			throw new ApiException(HttpStatus.NOT_FOUND, "region not found");
		}
	}

}
