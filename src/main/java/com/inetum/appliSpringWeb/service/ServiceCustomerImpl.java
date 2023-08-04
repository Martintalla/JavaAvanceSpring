package com.inetum.appliSpringWeb.service;

import java.security.SecureRandom;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inetum.appliSpringWeb.doa.interfaces.IDaoCompte;
import com.inetum.appliSpringWeb.doa.interfaces.IDaoCustomer;
import com.inetum.appliSpringWeb.dto.CustomerDto;
import com.inetum.appliSpringWeb.entity.Compte;
import com.inetum.appliSpringWeb.entity.Customer;

@Service // ou @Component
@Transactional
public class ServiceCustomerImpl extends AbstractGenericService<Customer, Long, CustomerDto> implements IServiceCustomer {
	
	@Override
	public CrudRepository<Customer, Long> getdao() {
		return this.daoCustomer;
	}
	
	@Override
	public Class<CustomerDto> getDtoClass() {
		return CustomerDto.class;
	}


	Logger logger = LoggerFactory.getLogger(ServiceCompteImpl.class);

	@Autowired
	private IDaoCustomer daoCustomer;
	@Autowired
	private IDaoCompte daoCompte; // dao principal

	@Override
	public boolean checkCustomerPassword(Long customerId, String password) {
		Customer clientAVerifier = daoCustomer.findById(customerId).orElse(null);
		if (clientAVerifier.getPassword() == null || clientAVerifier == null) {
			return false;
		} else
			return password.equals(clientAVerifier.getPassword());

	}

	@Override

	public String resetCustomerPassword(Long customerId) {
		Customer customer = daoCustomer.findById(customerId).orElse(null);
		String password =this.generateRandomPassword(10, 48, 122);
		customer.setPassword(password);
		daoCustomer.save(customer);
		return password;
	}

	@Override
	public Customer searchById(Long idCustomer) {
		return daoCustomer.findById(idCustomer).orElse(null);
	}

	@Override
	public List<Compte> rechercherCustomerAvecComptesParNumero(Long idCustomer) {
		return daoCompte.findByCustomerId(idCustomer);
	}
	

	@Override
	public List<Customer> rechercherCustomerSelonPrenomEtNom(String prenom, String nom) {
		return daoCustomer.findByFirstnameAndLastname(prenom, nom);
	}
	
	public String generateRandomPassword(int len, int randNumOrigin, int randNumBound) {
        SecureRandom random = new SecureRandom();
        return random.ints(randNumOrigin, randNumBound + 1)
                .filter(i -> Character.isAlphabetic(i) || Character.isDigit(i))
                .limit(len)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint,
                        StringBuilder::append)
                .toString();
    }

}
