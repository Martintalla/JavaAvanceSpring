package com.inetum.appliSpringWeb.dao;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.inetum.appliSpringWeb.doa.interfaces.IDaoCompte;
import com.inetum.appliSpringWeb.doa.interfaces.IDaoCustomer;
import com.inetum.appliSpringWeb.entity.Compte;
import com.inetum.appliSpringWeb.entity.Customer;

@SpringBootTest // classe interprétée par JUnit et SpringBoot
@ActiveProfiles({ "oracle" }) // application-oracle.properties
public class TestCustomerJpa {

	Logger logger = LoggerFactory.getLogger(TestCustomerJpa.class);

	@Autowired // pour initialisation daoCompte
	// qui va référencer un composant Spring existant compatible
	// avec l'interface DaoCompte (DaoCompteJpa avec@Repository)
	private IDaoCompte daoCompteJpa;

	

	@Autowired
	private IDaoCustomer daoCustomerJpa;

	@Test
	public void testCostomer() {
		
		
		Customer customer1 = daoCustomerJpa.save(new Customer(null, "Martin", "Béni", "125o"));
		daoCompteJpa.save(new Compte(null, "compte_B", 80.0, customer1));
		
		Customer customer2 = daoCustomerJpa.save(new Customer(null, "Jean", "Jacques", "125o"));
		daoCompteJpa.save(new Compte(null, "compte_C", 250.0, customer2));
		daoCompteJpa.save(new Compte(null, "compte_D", 540.0, customer2));
		
		List<Compte> ReadCustomerAccount = daoCompteJpa.findByCustomerId(customer1.getId());

		List<Compte> comptesAvecSoldeMaxi100 = daoCompteJpa.findBySoldeGreaterThanEqual(100.0);
		assertTrue(comptesAvecSoldeMaxi100.size() >= 2);

		logger.trace("comptesAvecSoldeMaxi100=" + comptesAvecSoldeMaxi100);
		
		logger.debug("Account(s) of " +customer1.getFirstname()+ " "+customer1.getLastname()+" :");
		for (Compte cmpt : ReadCustomerAccount) {
			logger.debug("\t" + cmpt.toString());
	}

}
}
