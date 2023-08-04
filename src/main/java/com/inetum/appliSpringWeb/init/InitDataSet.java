package com.inetum.appliSpringWeb.init;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.inetum.appliSpringWeb.doa.interfaces.IDaoCompte;
import com.inetum.appliSpringWeb.doa.interfaces.IDaoCustomer;
import com.inetum.appliSpringWeb.entity.Compte;
import com.inetum.appliSpringWeb.entity.Customer;
import com.inetum.appliSpringWeb.service.IServiceCompte;
import com.inetum.appliSpringWeb.service.IServiceCustomer;

/**
 * classe utilitaire qui initialise un jeu de données au démarrage de l'applicaion
 * uile si developpement en mode -auto=create-drop
 */

@Component
@Profile("init")

public class InitDataSet {
	
	@Autowired
	private IDaoCompte daoCompteJpa;
	
	@Autowired
	private IDaoCustomer daoCustomerJpa;
	
	@Autowired 
	private IServiceCompte serviceCompte;

	

	@Autowired
	private IServiceCustomer serviceCustomer;
	
	@PostConstruct
	public void initData() {
		Customer customer1 = daoCustomerJpa.save(new Customer(null, "Martin", "Béni", "125o"));
		daoCompteJpa.save(new Compte(null,"compte_1" , 50.0, customer1));
    	daoCompteJpa.save(new Compte(null,"compte_2" , 80.0, customer1));
    	daoCompteJpa.save(new Compte(null,"compte_3" , 250.0, customer1));
    	Customer customer2 = daoCustomerJpa.save(new Customer(null, "Jean", "Jacques", "125o"));
    	daoCompteJpa.save(new Compte(null,"compte_4" , 540.0,customer2 ));
    	daoCompteJpa.save(new Compte(null,"compte_6" , 1000.0, customer2));
    	
    	Customer customer3 = serviceCustomer.saveOrUpdate(new Customer(null, "Henri", "Paul", "125o"));
		serviceCompte.saveOrUpdate(new Compte(null, "compte_B", 80.0, customer1));
		
		Customer customer4 = serviceCustomer.saveOrUpdate(new Customer(null, "Jean", "Jacques", "125"));
		serviceCompte.saveOrUpdate(new Compte(null, "compte_C", 250.0, customer2));
		serviceCompte.saveOrUpdate(new Compte(null, "compte_D", 540.0, customer2));
		
	}

}
