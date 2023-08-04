package com.inetum.appliSpringWeb.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.inetum.appliSpringWeb.converter.DtoConverterCompte;
import com.inetum.appliSpringWeb.dto.CompteDto;
import com.inetum.appliSpringWeb.dto.CustomerDto;
import com.inetum.appliSpringWeb.entity.Compte;
import com.inetum.appliSpringWeb.entity.Customer;
import com.inetum.appliSpringWeb.service.IServiceCustomer;
import com.inetum.appliSpringWeb.converter.GenericConverter;

@RestController
@RequestMapping(value = "/api-bank/customer", headers = "Accept=application/json")
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST })
public class CustomerResCtrl {

	// NB: cette version 1 n'utilise pas encore les DTOs

	@Autowired
	private IServiceCustomer serviceCustomer;

	private DtoConverterCompte dtoConverterCompte = new DtoConverterCompte();

	// exemple de fin d'URL: ./api-bank/customer/1
	@GetMapping("/{idClient}")
	public ResponseEntity<?> getCustomerByNumero(@PathVariable("idClient") Long idClient) {

		CustomerDto customerDto = serviceCustomer.searchDtoById(idClient);
		if (customerDto != null) {
			return new ResponseEntity<CustomerDto>(customerDto, HttpStatus.OK);

		} else
			return new ResponseEntity<String>(HttpStatus.NOT_FOUND);// 404
	}

	// exemple de fin d'URL: ./api-bank/customer/1
	// à déclencher en mode DELETE
	@DeleteMapping("/{idClient}")
	public ResponseEntity<?> deleteCustomerByNumero(@PathVariable("idClient") Long idClient) {

		if (!serviceCustomer.existById(idClient))
			return new ResponseEntity<String>("{ \"err\" : \"customer not found\"}", HttpStatus.NOT_FOUND); // NOT_FOUND
																											// =
		serviceCustomer.deleteById(idClient);
		return new ResponseEntity<String>("{ \"done\" : \"customer deleted\"}", HttpStatus.OK);
		// ou bien
		// return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	// exemple de fin d'URL: ./api-bank/customer
	@GetMapping("")
	public List<CustomerDto> getCustomers(@RequestParam(value = "Jean", required = false) String firstname,
			@RequestParam(value = "Jacques", required = false) String lastname) {
		if (firstname == null || lastname == null) {
			// return
			// dtoConverter.compteToCompteDto(serviceCompte.rechercherTousLesComptes());
			return serviceCustomer.searchAllDto();

		} else {
			return GenericConverter.map(serviceCustomer.rechercherCustomerSelonPrenomEtNom(firstname, lastname),
					CustomerDto.class);
		}

	}

	// exemple de fin d'URL: ./api-bank/compte
	// appelé en mode POST avec dans la partie invisible "body" de la requête:
	// { "numero" : null , "lastname" : "firsname" , "password" : "pwd" }
	// ou bien { "lastname" : "firsname" , "password" : "pwd" }
	@PostMapping("")
	public CustomerDto postCustomer(@RequestBody CustomerDto newCustomer) {
		Customer customerSaved = serviceCustomer.saveOrUpdate(GenericConverter.map(newCustomer, Customer.class));

		return GenericConverter.map(customerSaved, CustomerDto.class);// on retourne le compte avec la clze primaire
																		// auro-incremenrée
	}

	// exemple de fin d'URL: ./api-bank/customer
	// ou bien ./api-bank/customer/5
	// appelé en mode PUT avec dans la partie invisible "body" de la requête:
	// { "numero" : 5 , "lastname" : "firsname" , "password" : "pwd"}
	// ou bien { "lastname" : "firsname" , "password" : "pwd" }
	@PutMapping({ "", "/{idClient}" })
	public ResponseEntity<?> postCustomerToUpdate(@RequestBody CustomerDto customerdto,
			@PathVariable("idClient") Long idClient) {
		Long IdCustomerToUpdate = (idClient != null) ? idClient : customerdto.getId();

		if (!serviceCustomer.existById(IdCustomerToUpdate))

			return new ResponseEntity<String>("{\"err\": \"CUSTOMER NOT FOUND\"}", HttpStatus.NOT_FOUND);// NOT_FOUND
																											// 404

		if (customerdto.getId() == null)
			customerdto.setId(IdCustomerToUpdate);
		serviceCustomer.saveOrUpdate(GenericConverter.map(customerdto, Customer.class));
		return new ResponseEntity<Customer>(GenericConverter.map(customerdto, Customer.class), HttpStatus.OK);

	}

	@PutMapping({"/{idCustomer}" })
	public ResponseEntity<?> putResetPwd(@PathVariable("idCustomer") Long idCustomer) {

		serviceCustomer.resetCustomerPassword(idCustomer);
		
		return new ResponseEntity<String>("{ \"done\" : \"password changed with sucess\"}", HttpStatus.OK);

	}

}