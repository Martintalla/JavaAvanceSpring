package com.inetum.appliSpringWeb.doa.interfaces;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inetum.appliSpringWeb.entity.Customer;

/*
 * DAO = Data Access Object
 * avec méthode CRUD
 * et throws RuntimeException implicites
 */

public interface IDaoCustomer extends JpaRepository<Customer,Long> {
	
	List<Customer> findByFirstnameAndLastname(String firstname, String lastname);
	
}
