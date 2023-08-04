package com.inetum.appliSpringWeb.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NamedQuery(name = "Compte.findCompteWithOperationsById", query = "SELECT c FROM Compte c LEFT JOIN FETCH c.operations op WHERE c.numero = ?1")
@Getter
@Setter
@NoArgsConstructor
public class Compte {

	@Transient // @Transient (de javax.persistence) signifie ne pas sauvgarer l'attribut
				// en dessous en base
	@Getter
	private static Double decourvertAutotise = -500.0;// Static ça veut dire qu'il est partagé par tout le monde
	                                                  // vu que c'est un satic il faut mettre @Getter au dessus de classe
	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	// @GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long numero;

	private String label;

	private Double solde;

	@JsonIgnore // pour demander à ignorer la liste des opérations lorsque
	// l'objet courant de la classe COMPTE sera automatiquement convertie de JAVA en
	// JSON
	// AUTRE SOLUTION : n'utilser que

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "compte", cascade = CascadeType.ALL)
	// @OneToMany(fetch = FetchType.EAGER, mappedBy = "compte")
	private List<Operation> operations; // +get/set

	@ManyToOne
	@JoinColumn(name = "num_customer")
	private Customer customer;

	public Compte(Long numero, String label, Double solde, Customer customer) {
		super();
		this.numero = numero;
		this.label = label;
		this.solde = solde;
		this.customer = customer;
	}
	
	public Compte(Long numero, String label, Double solde) {
		super();
		this.numero = numero;
		this.label = label;
		this.solde = solde;
	}

	@Override
	public String toString() {
		return "Compte [numero=" + numero + ", label=" + label + ", solde=" + solde + "]";
	}

}