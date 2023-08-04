package com.inetum.appliSpringWeb.dao;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.inetum.appliSpringWeb.doa.interfaces.IDaoCompte;
import com.inetum.appliSpringWeb.doa.interfaces.IDaoCustomer;
import com.inetum.appliSpringWeb.doa.interfaces.IDaoOperation;
import com.inetum.appliSpringWeb.entity.Compte;
import com.inetum.appliSpringWeb.entity.Customer;
import com.inetum.appliSpringWeb.entity.Operation;

@SpringBootTest // classe interprétée par JUnit et SpringBoot
@ActiveProfiles({"oracle"}) //application-oracle.properties
public class TestCompteJpa {

	Logger logger = LoggerFactory.getLogger(TestCompteJpa.class);

	@Autowired // pour initialisation daoCompte
	// qui va référencer un composant Spring existant compatible
	// avec l'interface DaoCompte (DaoCompteJpa avec@Repository)
	private IDaoCompte daoCompteJpa;

	@Autowired
	private IDaoOperation daoOperationJpa;
	
	@Autowired
	private IDaoCustomer daoCustomerJpa;

	@Test

	public void testCompteAvecOperations() {
		Customer customer1 = daoCustomerJpa.save(new Customer(null, "Martin", "Béni", "125o"));
		 Compte compteAa = daoCompteJpa.save(new Compte(null,"compte_Aa" , 70.0, customer1));
			
			Operation op1CompteA = daoOperationJpa.save(
		    		new Operation(null,-3.2 , "achat bonbons" , new Date() , compteAa));
			Operation op2CompteA = daoOperationJpa.save(
		    		new Operation(null,-3.3 , "achat gateau" , new Date() , compteAa));
			
			Compte compteBb = daoCompteJpa.save(new Compte(null,"compte_Bbb" , 80.0, customer1));
			
			Customer customer2 = daoCustomerJpa.save(new Customer(null, "Jean", "Pierre", "125o"));
	    	
	    	Operation op1CompteB = daoOperationJpa.save(
		    		new Operation(null,-1.3 , "achat raisonnable" , new Date() , compteBb));

		// Compte compteARelu =
		// daoCompteJpa.findById(compteAa.getNumero()).orElse(null);
		Compte compteARelu = daoCompteJpa.findCompteWithOperationsById(compteAa.getNumero()).orElse(null);
		logger.debug("compteARelu=" + compteARelu.toString());

		logger.debug("operations du compteARelu :");
		for (Operation op : compteARelu.getOperations()) {
			logger.debug("\t" + op.toString());
		}
	}

	@Test
	public void testQueries() {
		Customer customer1 = daoCustomerJpa.save(new Customer(null, "Martin", "Béni", "125o"));
		daoCompteJpa.save(new Compte(null, "compte_A", 50.0,customer1));
		daoCompteJpa.save(new Compte(null, "compte_B", 80.0, customer1));
		Customer customer2 = daoCustomerJpa.save(new Customer(null, "Jean", "Jacques", "125o"));
		daoCompteJpa.save(new Compte(null, "compte_C", 250.0, customer2));
		daoCompteJpa.save(new Compte(null, "compte_D", 540.0, customer2));

		List<Compte> comptesAvecSoldeMini100 = daoCompteJpa.findBySoldeLessThanEqual(100.0);
		assertTrue(comptesAvecSoldeMini100.size() >= 2);

		logger.trace("comptesAvecSoldeMini100=" + comptesAvecSoldeMini100);

		List<Compte> comptesAvecSoldeMaxi100 = daoCompteJpa.findBySoldeGreaterThanEqual(100.0);
		assertTrue(comptesAvecSoldeMaxi100.size() >= 2);

		logger.trace("comptesAvecSoldeMaxi100=" + comptesAvecSoldeMaxi100);
	}

}
