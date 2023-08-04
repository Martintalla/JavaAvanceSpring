package com.inetum.appliSpringWeb.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.inetum.appliSpringWeb.converter.DtoConverterCompte;
import com.inetum.appliSpringWeb.dto.CompteDto;
import com.inetum.appliSpringWeb.entity.Compte;
import com.inetum.appliSpringWeb.service.IServiceCompte;

@RestController
@RequestMapping(value = "/api-bank/compte", headers = "Accept=application/json")
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST })
public class CompteResCtrl {

	// NB: cette version 1 n'utilise pas encore les DTOs

	@Autowired
	private IServiceCompte serviceCompte;
	
	private DtoConverterCompte dtoConverterCompte = new DtoConverterCompte();

	// exemple de fin d'URL: ./api-bank/compte/1
	
/**
 *@GetMapping("/{numeroCompte}")
	public ResponseEntity<?> getCompteByNumero(@PathVariable("numeroCompte") Long numeroCompte) {
		CompteDto compteDto = serviceCompte.searchDtoById(numeroCompte);
		if (compteDto != null) {
			return new ResponseEntity<CompteDto>(compteDto, HttpStatus.OK);

		} else
			//return new ResponseEntity<String>(HttpStatus.NOT_FOUND);// 404
			return new ResponseEntity<ApiError>(
			           new ApiError(HttpStatus.NOT_FOUND ,"compte not found") ,
			           HttpStatus.NOT_FOUND); //NOT_FOUND = code http 404 
 */
	@GetMapping("/{numeroCompte}" )
	public CompteDto getCompteByNumero(@PathVariable("numeroCompte") Long numeroCompte) {
		    return serviceCompte.searchDtoById(numeroCompte);
		    //en cas de numero de compte pas trouvé
		    //l'exception "NotFoundException" remontée par l'appel à .searchDtoById(...)
		    //est automatiquement gérée par RestResponseEntityExceptionHandler
		    //et  celui ci construit et retourne automatiquement
		    //un ResponseEntity<ApiError> avec le bon status http et le bon message
	}

	// exemple de fin d'URL: ./api-bank/compte/1
	// à déclencher en mode DELETE
	@DeleteMapping("/{numeroCompte}" )
	public ResponseEntity<?> deleteCompteByNumero(@PathVariable("numeroCompte") Long numeroCompte) {
		    serviceCompte.deleteById(numeroCompte);//retournant NotFoundException
		    return new ResponseEntity<String>("{ \"done\" : \"compte deleted\"}" ,
		    		                          HttpStatus.OK); 
		}
	/**
	 * @DeleteMapping("/{numeroCompte}")
	public ResponseEntity<?> deleteCompteByNumero(@PathVariable("numeroCompte") Long numeroCompte) {
		
		if (!serviceCompte.existById(numeroCompte))
			return new ResponseEntity<String>("{ \"err\" : \"compte not found\"}", HttpStatus.NOT_FOUND); // NOT_FOUND =
			serviceCompte.deleteById(numeroCompte);
		    return new ResponseEntity<String>("{ \"done\" : \"compte deleted\"}", HttpStatus.OK);
		// ou bien
		// return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	 * @param numeroCompte
	 * @return
	 */
	

	@GetMapping("")
	public List<CompteDto> getComptes(@RequestParam(value = "soldeMini", required = false) Double soldeMini) {
		if (soldeMini == null) {
			//return dtoConverter.compteToCompteDto(serviceCompte.rechercherTousLesComptes());
			return serviceCompte.searchAllDto();

		} else {
			return dtoConverterCompte.compteToCompteDto(serviceCompte.rechercherSelonSoldeMini(soldeMini));
		}

	}

	// exemple de fin d'URL: ./api-bank/compte
	// appelé en mode POST avec dans la partie invisible "body" de la requête:
	// { "numero" : null , "label" : "compte_6" , "solde" : 50.0 }
	// ou bien { "label" : "compteQuiVaBien" , "solde" : 50.0 }
	@PostMapping("")
	public CompteDto postCompte(@RequestBody CompteDto nouveauCompte) {
		Compte compteEnregistreEnBase = serviceCompte.saveOrUpdate(dtoConverterCompte.compteToCompteDto(nouveauCompte));
		return dtoConverterCompte.compteToCompteDto(compteEnregistreEnBase);// on retourne le compte avec la clze primaire auro-incremenrée
	}

	// exemple de fin d'URL: ./api-bank/compte
	// ou bien ./api-bank/compte/5
	// appelé en mode PUT avec dans la partie invisible "body" de la requête:
	// { "numero" : 5 , "label" : "compte_6" , "solde" : 150.0 }
	// ou bien { "label" : "compte5QueJaime" , "solde" : 150.0 }
	@PutMapping({ "", "/{numeroCompte}" })
	public ResponseEntity<?> postCompteToUpdate(@RequestBody CompteDto comptedto,
			@PathVariable("numeroCompte") Long numeroCompte) {
		Long numCompteToUpdate = (numeroCompte != null) ? numeroCompte : comptedto.getNumero();

		if(!serviceCompte.existById(numCompteToUpdate))

		
			return new ResponseEntity<String>("{\"err\": \"ACCOUNT NOT FOUND\"}", HttpStatus.NOT_FOUND);// NOT_FOUND 404

		 if(comptedto.getNumero()==null)
		    	comptedto.setNumero(numCompteToUpdate);
			serviceCompte.saveOrUpdate(dtoConverterCompte.compteToCompteDto(comptedto));
		return new ResponseEntity<Compte>(dtoConverterCompte.compteToCompteDto(comptedto), HttpStatus.OK);

	}

}