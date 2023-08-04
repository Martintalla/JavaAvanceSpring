package com.inetum.appliSpringWeb.controller;


/**
 * package "reste : @Restcontroller pour les WS REST et JSON
 * package "controller": @Controller pour vieux spring-MV avec .jsp
 */
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/calcul")
public class CalculController {
	
	@RequestMapping("/saisieHt")
	public String versSaisieTva(Model model) {
		return "declencherCalcul"; //..../jsp/declencherCalcul.jsp
	}
	
	//private ServiceCompte serviceCompte avec @Autowired possible ici
	
	//URL : http://localhost:8080/appliSpringWeb/calcul/tva?ht=200&tauxTvaPct=20
	
	//ou bien
	
	//http://localhost:8080/appliSpringWeb/calcul/tva avec données saisies dans le formulaire html/jsp et "submit"
	
	@RequestMapping("/tva")
	public String calcumerTva(Model model,@RequestParam(name="ht") double ht, 
			@RequestParam(name="tauxTvaPct") double tauxTvaPct) {
		//calcul des parties du resultat
		
		double tva = ht*tauxTvaPct/100;
		
		double ttc = ht+tva;
		
		model.addAttribute("tva", tva);
		model.addAttribute("ttc", ttc);
		return "resTva"; // on retourne le nom de la vue qui va devoir mettre  n  "page html" le résulats (ici "resTva.jsp")
	}

}
