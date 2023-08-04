package com.inetum.appliSpringWeb.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * CompteDtoEx au sens "étendu/détaillé"
 */
@Data
@NoArgsConstructor
public class CompteDtoEx extends CompteDto {
	
	private Long numeroClient;
	//ou bien ClientDto client;
	
	
	//priavte List<OperationDto> operations; 

}
