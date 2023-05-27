package com.invoice.api.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.invoice.api.dto.ApiResponse;
import com.invoice.api.dto.DtoProduct;
import com.invoice.api.entity.Cart;
import com.invoice.api.entity.Invoice;
import com.invoice.api.entity.Item;
import com.invoice.api.repository.RepoInvoice;
import com.invoice.api.repository.RepoItem;
import com.invoice.configuration.client.ProductClient;
import com.invoice.exception.ApiException;

@Service
public class SvcInvoiceImp implements SvcInvoice {

	@Autowired
	RepoInvoice repo;
	
	@Autowired
	RepoItem repoItem;
	
	@Autowired
	SvcCart svc;
	
	@Autowired
	ProductClient productClient;

	@Override
	public List<Invoice> getInvoices(String rfc) {
		return repo.findByRfcAndStatus(rfc, 1);
	}

	@Override
	public List<Item> getInvoiceItems(Integer invoice_id) {
		return repoItem.getInvoiceItems(invoice_id);
	}

	@Override
	public ApiResponse generateInvoice(String rfc) {
		/*
		 * Requerimiento 5
		 * Implementar el método para generar una factura 
		 */
		
		 List<Cart> products = svc.getCart(rfc);
		
		 if (products == null || products.isEmpty()) {
			 throw new ApiException(HttpStatus.NOT_FOUND, "cart has no items");
		 }
		 
		 List<Item> invoices = new ArrayList<Item>();
		 for (Cart p: products) {
			 DtoProduct product = productClient.getProduct(p.getGtin()).getBody();
			 double unit_price = product.getPrice();
			 double total = unit_price*p.getQuantity();
			 double taxes = total*(0.16);
			 double subtotal = total - taxes;
			 Item i = new Item();
			 i.setGtin(p.getGtin());
			 i.setQuantity(p.getQuantity());
			 i.setSubtotal(subtotal);
			 i.setTaxes(taxes);
			 i.setTotal(total);
			 i.setUnit_price(unit_price);
			 invoices.add(i);
			 productClient.updateProductStock(p.getGtin(), product.getStock()-p.getQuantity());
		 }
		 
		 Invoice factura = new Invoice();
		 double total = 0;
		 double taxes = 0;
		 double subtotal = 0;
		 for (Item i: invoices) {
			 total = total + i.getTotal();
			 taxes = taxes + i.getTotal();
			 subtotal = subtotal + i.getSubtotal();
		 }
		 LocalDateTime created_at = LocalDateTime.now();
		 
		 factura.setTotal(total);
		 factura.setCreated_at(created_at);
		 factura.setRfc(rfc);
		 factura.setSubtotal(subtotal);
		 factura.setTaxes(taxes);
		 repo.save(factura);
		 
		 for (Item i: invoices) {
			 i.setId_invoice(factura.getInvoice_id());
			 repoItem.save(i);
		 }
		 
		 svc.clearCart(rfc);
		 return new ApiResponse("invoice generated");
	 }
 
 }
 