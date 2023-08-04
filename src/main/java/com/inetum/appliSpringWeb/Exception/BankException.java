package com.inetum.appliSpringWeb.Exception;

//Developpeur faineant --> BankExeption
//Developpeur courageux --> VirementException +SoldeInsuffisantException
public class BankException extends RuntimeException {
	//private Sring detail;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BankException() {

	}

	public BankException(String message) {
		super(message);
	}

	public BankException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

}
