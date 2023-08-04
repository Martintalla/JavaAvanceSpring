package com.inetum.appliSpringWeb.service;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inetum.appliSpringWeb.Exception.BankException;
import com.inetum.appliSpringWeb.doa.interfaces.IDaoCompte;
import com.inetum.appliSpringWeb.doa.interfaces.IDaoOperation;
import com.inetum.appliSpringWeb.dto.CompteDto;
import com.inetum.appliSpringWeb.entity.Compte;
import com.inetum.appliSpringWeb.entity.Operation;

@Service // ou @Component
@Transactional // ici (sur une classe de service) en tant que bonne pratique
public class ServiceCompteImpl extends AbstractGenericService<Compte, Long, CompteDto> implements IServiceCompte {

	@Override
	public CrudRepository<Compte, Long> getdao() {
		return this.daoCompte;
	}
	
	@Override
	public Class<CompteDto> getDtoClass() {
		return CompteDto.class;
	}


	Logger logger = LoggerFactory.getLogger(ServiceCompteImpl.class);

	@Autowired
	private IDaoCompte daoCompte; // dao principal

	@Autowired
	private IDaoOperation daoOperation;// dao secondaire

	@Override
	// @Transactional //pas bon endroit, le mettre au dessus plutôt
	public void transferer(Double montant, Long numCptDeb, Long numCmptCred) throws BankException {
		try {

			this.debiterCompte(numCptDeb, montant, "debit suite au virement vers le compte " + numCmptCred);

			this.crediterCompte(numCmptCred, montant, "credit suite au virement depuis compte " + numCptDeb);

			// si @Transaction su classe de service (ce qui est le cas général, bonne
			// prtaique)
			// toutes les entités remontées par le DAOs à coup de .findBy..() sont à l'état
			// persistant(es)
			// et donc .save() autamatiqye en ca s de transaction réussie (sans exception)
			// et de "LazyException" à l'intérieur de la méthode service
		} catch (Exception e) {

			// e.printStackTrace();
			// logger.error("echec virement", e);
			logger.error("echec virement" + e.getMessage());
			throw new BankException("echec virement", e);
		}

	}

	@Override
	public void debiterCompte(Long numeroCompte, Double montant, String message) {
		Compte compteDeb = daoCompte.findById(numeroCompte).get();
		double nouveauSoldeTheoriqueApresDebit = compteDeb.getSolde() - montant;
		if (nouveauSoldeTheoriqueApresDebit >= Compte.getDecourvertAutotise()) {
			compteDeb.setSolde(nouveauSoldeTheoriqueApresDebit);
			daoCompte.save(compteDeb); // .save() facultatif à l'état persistant (effet de @Transactional)

			Operation opDebit = daoOperation.save(new Operation(null, -montant, message, new Date(), compteDeb));
		} else {
			throw new BankException("solde insuffisant vis à vis du découvertAutorise=" + Compte.getDecourvertAutotise()
					+ " pour effectuer un debit de " + montant);
		}
	}

	@Override
	public void crediterCompte(Long numeroCompte, Double montant, String message) {
		Compte compteCred = daoCompte.findById(numeroCompte).get();
		compteCred.setSolde(compteCred.getSolde() + montant);
		daoCompte.save(compteCred); // .save() facultatif à l'état persistant (effet de @Transactional)

		Operation opCredit = daoOperation.save(new Operation(null, montant, message, new Date(), compteCred));

	}

	
	@Override
	public List<Compte> rechercherSelonSoldeMini(Double soldeMini) {
		return daoCompte.findBySoldeGreaterThanEqual(soldeMini);
	}

	
	
}
