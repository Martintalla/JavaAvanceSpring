package com.inetum.appliSpringWeb.service;

import java.util.List;

import com.inetum.appliSpringWeb.Exception.NotFoundException;

public interface IGenericService<E,ID,DTO> {
	public E searchById(ID id);
	public DTO searchDtoById(ID id) throws NotFoundException;
	//variante à prévoir en version spécifique : 
    //public DTO_EX searchDtoWithDetailsById(ID id);
	public E saveOrUpdate(E entity);
	public void deleteById(ID id) throws NotFoundException;
	void shouldExistById(ID id) throws NotFoundException;
	public boolean existById(ID id);
	List<E> searchAll();
	public List<DTO> searchAllDto();
	

}
