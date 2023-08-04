package com.inetum.appliSpringWeb.converter;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.inetum.appliSpringWeb.doa.interfaces.IDaoCompte;
import com.inetum.appliSpringWeb.dto.CompteDto;
import com.inetum.appliSpringWeb.entity.Compte;

public class DtoConverterCompte {

	@Autowired
	private IDaoCompte daoCompteJpa;

	public /* static */List<CompteDto> compteToCompteDto(List<Compte> entityList) {
		return entityList.stream().map((entity) -> compteToCompteDto(entity)).toList();
	};

	public /* static */CompteDto compteToCompteDto(Compte entity) {
		/*
		 * return new CompteDto(entity.getNumero(), entity.getLabel(),
		 * entity.getSolde());
		 */

		CompteDto compteDto = new CompteDto();
		compteDto.setNumero(entity.getNumero());
		compteDto.setLabel(entity.getLabel());
		compteDto.setSolde(entity.getSolde());

		// BeanUtils.copyProperties(entity, compteDto); //Moins rapide en terme de code
		// que la première
		return compteDto;
	}

	public /* static */Compte compteToCompteDto(CompteDto dto) {
		return new Compte(dto.getNumero(), dto.getLabel(), dto.getSolde());
	}

}
