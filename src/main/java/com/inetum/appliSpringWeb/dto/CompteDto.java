package com.inetum.appliSpringWeb.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * DTO = Data Transfert Object CompteDto sera représentation partielle
 * simplifiée et adaptée de entity.Compte
 * 
 * CompteDto ici version "Essentiel/Basic"
 */
//@Getter
//@Setter
@NoArgsConstructor
//@ToString
@Data //Remplace tut ce qui est commenté au dessus
public class CompteDto {
	private Long numero;
	private String label;
	private Double solde;

	public CompteDto(Long numero, String label, Double solde) {
		super();
		this.numero = numero;
		this.label = label;
		this.solde = solde;
	}

}
