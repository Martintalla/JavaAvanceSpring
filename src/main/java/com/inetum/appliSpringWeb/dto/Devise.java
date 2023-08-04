package com.inetum.appliSpringWeb.dto;

import lombok.Getter;

import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * DTO = Data Transfert Object
 * Objet de données transferé à travers le réseau  ou entre couches logicielles
 */

@Getter @Setter @ToString @NoArgsConstructor
public class Devise {
	@Schema(description = "code de la devise", defaultValue = "EUR")

private String code; // ex : USD, EUR
private String nom; //: Dollar, euro
private Double change; // change pour unn euro


public Devise(String code, String nom, Double change) {
	super();
	this.code = code;
	this.nom = nom;
	this.change = change;
}




}
