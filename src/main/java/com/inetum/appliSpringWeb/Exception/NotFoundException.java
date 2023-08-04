package com.inetum.appliSpringWeb.Exception;


public class NotFoundException extends RuntimeException {
	//private Sring detail;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NotFoundException() {

	}

	public NotFoundException(String message) {
		super(message);
	}

	public NotFoundException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

}
