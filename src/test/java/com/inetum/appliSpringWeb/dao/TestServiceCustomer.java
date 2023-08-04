package com.inetum.appliSpringWeb.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.inetum.appliSpringWeb.entity.Compte;
import com.inetum.appliSpringWeb.entity.Customer;
import com.inetum.appliSpringWeb.service.IServiceCompte;
import com.inetum.appliSpringWeb.service.IServiceCustomer;

@SpringBootTest // classe interprétée par JUnit et SpringBoot
@ActiveProfiles({ "oracle" }) // application-oracle.properties
public class TestServiceCustomer {
	
	Logger logger = LoggerFactory.getLogger(TestServiceCustomer.class);

	@Autowired 
	private IServiceCompte serviceCompte;

	

	@Autowired
	private IServiceCustomer serviceCustomer;
	
	
	@Test
	public void rechercherCompteClientParId() {
		
		Customer customer1 = serviceCustomer.saveOrUpdate(new Customer(null, "Martin", "Béni", "125o"));
		serviceCompte.saveOrUpdate(new Compte(null, "compte_B", 80.0, customer1));
		
		Customer customer2 = serviceCustomer.saveOrUpdate(new Customer(null, "Jean", "Jacques", "125o"));
		Customer customer3 = serviceCustomer.saveOrUpdate(new Customer(null, "Jean", "Jacques", "125"));
		serviceCompte.saveOrUpdate(new Compte(null, "compte_C", 250.0, customer2));
		serviceCompte.saveOrUpdate(new Compte(null, "compte_D", 540.0, customer2));
		
		List<Compte> compteClient2Relu = serviceCustomer.rechercherCustomerAvecComptesParNumero(customer2.getId());
		
		logger.debug("Account(s) of " +customer2.getFirstname()+ " "+customer2.getLastname()+" :");
		for(Compte cmpt: compteClient2Relu) {
			logger.debug("\t" + cmpt.toString());
		}
		
		assertEquals(2,compteClient2Relu.size() );
		
		
		
		}
	@Test
	public void rechercherclientJeanJacques() {
		List<Customer> clientJeanJacques = serviceCustomer.rechercherCustomerSelonPrenomEtNom("Jean", "Jacques");
		
		
		for(Customer cust: clientJeanJacques) {
			logger.debug("\t" + cust.toString());
		}
		
		assertEquals(2,clientJeanJacques.size() );
				
		
	}
	
	@Test
	public void modifierLeMotDePasse() {
		Customer customer1 = serviceCustomer.saveOrUpdate(new Customer(null, "Jacque", "Prevert", "14eo"));
		serviceCustomer.resetCustomerPassword(customer1.getId());
		Customer customer1Relu = serviceCustomer.searchById(customer1.getId());
		logger.trace("Mote de passe temporaire: " +customer1Relu.getPassword());
	}

}
