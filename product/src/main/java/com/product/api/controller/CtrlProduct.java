package com.product.api.controller;


import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.product.api.dto.ApiResponse;
import com.product.api.entity.Category;
import com.product.api.entity.Product;
import com.product.api.service.SvcProduct;
import com.product.exception.ApiException;

@RestController
@RequestMapping("/product")
public class CtrlProduct {

	@Autowired
	SvcProduct svc;
	
	@GetMapping("/category/{category_id}")
	public ResponseEntity<List<Product>> listProducts(@NotNull @PathVariable("category_id") Integer categoryId){
		return new ResponseEntity<>(svc.listProducts(categoryId), HttpStatus.OK);
	}

	@PutMapping("/product/{gtin}/category")
	public ResponseEntity<ApiResponse> updateProductCategory(@PathVariable("gtin") String gtin, @RequestBody Category category){
		return new ResponseEntity<>(svc.updateProductCategory(category, gtin), HttpStatus.OK);
	}

	@GetMapping("/{gtin}")
	public ResponseEntity<Product> getProduct(@PathVariable String gtin){
		return new ResponseEntity<>(svc.getProduct(gtin), HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<ApiResponse> createProduct(@Valid @RequestBody Product in, BindingResult bindingResult){
		if(bindingResult.hasErrors())
			throw new ApiException(HttpStatus.BAD_REQUEST, bindingResult.getAllErrors().get(0).getDefaultMessage());
		return new ResponseEntity<>(svc.createProduct(in),HttpStatus.OK);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<ApiResponse> updateProduct(@PathVariable("id") Integer id, @Valid @RequestBody Product in, BindingResult bindingResult){
		if(bindingResult.hasErrors())
			throw new ApiException(HttpStatus.BAD_REQUEST, bindingResult.getAllErrors().get(0).getDefaultMessage());
		return new ResponseEntity<>(svc.updateProduct(in, id),HttpStatus.OK);
	}
	
	@PutMapping("/{gtin}/stock/{stock}")
	public ResponseEntity<ApiResponse> updateProductStock(@PathVariable String gtin, @Valid @RequestBody Integer stock, BindingResult bindingResult){
		if(bindingResult.hasErrors())
			throw new ApiException(HttpStatus.BAD_REQUEST, bindingResult.getAllErrors().get(0).getDefaultMessage());
		return new ResponseEntity<>(svc.updateProductStock(gtin, stock),HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse> deleteProduct(@PathVariable("id") Integer id){
		return new ResponseEntity<>(svc.deleteProduct(id), HttpStatus.OK);
	}
}
