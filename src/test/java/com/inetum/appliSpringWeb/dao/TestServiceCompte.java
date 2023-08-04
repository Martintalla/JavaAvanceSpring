package com.inetum.appliSpringWeb.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.inetum.appliSpringWeb.doa.interfaces.IDaoCustomer;
import com.inetum.appliSpringWeb.entity.Compte;
import com.inetum.appliSpringWeb.entity.Customer;
import com.inetum.appliSpringWeb.service.IServiceCompte;

@SpringBootTest // classe interprétée par JUnit et SpringBoot
@ActiveProfiles({ "oracle", "perf" }) // application-oracle.properties
public class TestServiceCompte {

	Logger logger = LoggerFactory.getLogger(TestCustomerJpa.class);

	@Autowired
	private IServiceCompte serviceCompte; // à tester

	@Autowired
	private IDaoCustomer daoCustomerJpa;

	@Test
	public void testBonTransfert() {
		Customer customer1 = daoCustomerJpa.save(new Customer(null, "Martin", "Béni", "125o"));
		Customer customer2 = daoCustomerJpa.save(new Customer(null, "Jean", "Jacques", "125o"));
		Compte compteA = serviceCompte.saveOrUpdate(new Compte(null, "SG", 500.0, customer1));
		Compte compteB = serviceCompte.saveOrUpdate(new Compte(null, "Bousorama", 200.0, customer1));

		logger.trace("Avant bon virement : compteA de " + customer1.getFirstname() + " " + customer1.getLastname()
				+ " vaut : " + compteA.getSolde() + "et le compte B de = " + customer2.getFirstname() + " "
				+ customer2.getLastname() + " vaut : " + compteB.getSolde());

		serviceCompte.transferer(200.00, compteA.getNumero(), compteB.getNumero());

		Compte compteA_apres = serviceCompte.searchById(compteA.getNumero());
		Compte compteB_apres = serviceCompte.searchById(compteB.getNumero());

		logger.trace("Apres bon virement : compteA de " + customer1.getFirstname() + " " + customer1.getLastname()
				+ " vaut : " + compteA_apres.getSolde() + "et le compte B de = " + customer2.getFirstname() + " "
				+ customer2.getLastname() + " vaut : " + compteB_apres.getSolde());

		assertEquals(compteA.getSolde() - 200.0, compteA_apres.getSolde(), 0.00001);
		assertEquals(compteB.getSolde() + 200.0, compteB_apres.getSolde(), 0.00001);
	}

	@Test
	public void testTransfertAnnulePourSoldeInsuff() {
		Customer customer1 = daoCustomerJpa.save(new Customer(null, "Martin", "Béni", "125o"));
		Customer customer2 = daoCustomerJpa.save(new Customer(null, "Jean", "Jacques", "125o"));
		Compte compteA = serviceCompte.saveOrUpdate(new Compte(null, "BNP", 500.0, customer1));
		Compte compteB = serviceCompte.saveOrUpdate(new Compte(null, "CREDIT AGRICOLE", 200.0, customer1));

		logger.trace("Avant virement avec solde insuffisant : compteA de " + customer1.getFirstname() + " " + customer1.getLastname()
				+ " vaut : " + compteA.getSolde() + "et le compte B de = " + customer2.getFirstname() + " "
				+ customer2.getLastname() + " vaut : " + compteB.getSolde());

		try {
			serviceCompte.transferer(2000.0, compteA.getNumero(), compteB.getNumero());

			// -2 id d'un compte inexistant
		} catch (Exception e) {
			logger.trace("" + e.getMessage());
		}

		Compte compteA_apres = serviceCompte.searchById(compteA.getNumero());
		Compte compteB_apres = serviceCompte.searchById(compteB.getNumero());

		logger.trace("Apres virement avec solde insuffisant : compteA de " + customer1.getFirstname() + " " + customer1.getLastname()
				+ " vaut : " + compteA_apres.getSolde() + "et le compte B de = " + customer2.getFirstname() + " "
				+ customer2.getLastname() + " vaut : " + compteB_apres.getSolde());

		assertEquals(compteA.getSolde(), compteA_apres.getSolde(), 0.00001);
		assertEquals(compteB.getSolde(), compteB_apres.getSolde(), 0.00001);
	}

}
