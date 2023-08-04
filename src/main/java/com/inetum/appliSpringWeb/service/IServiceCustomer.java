package com.inetum.appliSpringWeb.service;

import java.util.List;

import com.inetum.appliSpringWeb.dto.CustomerDto;
import com.inetum.appliSpringWeb.entity.Compte;
import com.inetum.appliSpringWeb.entity.Customer;

public interface IServiceCustomer extends IGenericService<Customer, Long, CustomerDto>{
	// METHODES SPECIFIQUES AU METIER DE LA BANQUE
	boolean checkCustomerPassword(Long customerId, String password);

	String resetCustomerPassword(Long customerId); // genere et affecte un nouveau mot de passe temporaire
	List<Compte> rechercherCustomerAvecComptesParNumero(Long idCustomer); // Sans lazy exception
	List<Customer> rechercherCustomerSelonPrenomEtNom(String prenom, String nom);


}
