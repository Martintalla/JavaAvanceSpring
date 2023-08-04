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
public class CustomerDto {
	
	private Long id;
	private String firstname;
	private String lastname;
	private String password;
	
	public CustomerDto(String firstname, String lastname, String password) {
		super();
		this.firstname = firstname;
		this.lastname = lastname;
		this.password = password;
	}

	

}
