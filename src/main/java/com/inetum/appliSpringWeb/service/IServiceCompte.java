package com.inetum.appliSpringWeb.service;

import java.util.List;

import com.inetum.appliSpringWeb.Exception.BankException;
import com.inetum.appliSpringWeb.dto.CompteDto;
import com.inetum.appliSpringWeb.entity.Compte;

//Business service ou Service métier
//avec remontées desexceptions (héritant de RunTimeException)
public interface IServiceCompte extends IGenericService<Compte, Long, CompteDto>{

	// Méthode spécifique au métier de la banque
	void debiterCompte(Long numeroCompte, Double montant, String message);
	void crediterCompte(Long numeroCompte, Double montant, String message);

	void transferer(Double montant, Long numCptDeb, Long numCmptCred) throws BankException;

	// ...
	// méthodes délégués aux DAOs le CRUD:

	//Compte searchById(Long numeroCompte);

	//List<Compte> searchAll();
	
	

	// ..
	//Compte saveOrUpdate(Compte compte);

	// Customer sauvegarderCompte (Customer customer);
	//void deleteById(Long numeroCompte);

	//boolean existById(Long numeroCompte);
	
	List<Compte> rechercherSelonSoldeMini(Double soldeMini);
	

}
