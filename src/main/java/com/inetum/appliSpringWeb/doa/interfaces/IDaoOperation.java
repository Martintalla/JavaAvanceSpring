package com.inetum.appliSpringWeb.doa.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inetum.appliSpringWeb.entity.Operation;

/*
 * DAO = Data Access Object
 * avec méthode CRUD
 * et throws RuntimeException implicites
 */

public interface IDaoOperation extends JpaRepository<Operation, Long>{
	
     
}
