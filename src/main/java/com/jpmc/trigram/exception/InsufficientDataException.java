package com.jpmc.trigram.exception;

/**
 * This exception will be raised when there is 
 * insufficient data to perform trigram analysis
 * @author Kshitij_Limaye
 *
 */
public class InsufficientDataException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public InsufficientDataException(String message) {
		super(message);
	}

}
