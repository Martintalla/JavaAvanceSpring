package com.inetum.appliSpringWeb.doa.interfaces;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inetum.appliSpringWeb.entity.Compte;

import java.util.Optional;

/*
 * DAO = Data Access Object
 * avec méthode CRUD
 * et throws RuntimeException implicites
 */

public interface IDaoCompte extends JpaRepository<Compte,Long> {
	
	//Les deux méthodes suivantes seront codées automatiquement 
    
     List<Compte> findBySoldeLessThanEqual(double soldeMini); //List<Compte> findBySoldeMini(double soldeMini);
     List<Compte> findBySoldeGreaterThanEqual(double soldeMaxi); 
     Optional<Compte> findCompteWithOperationsById(Long numero);
   List<Compte> findByCustomerId(Long id);
     //findAll() , deleteById() , ... héritées de JpaRepository
}
