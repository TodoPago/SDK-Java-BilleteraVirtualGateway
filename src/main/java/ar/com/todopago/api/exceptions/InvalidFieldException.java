package ar.com.todopago.api.exceptions;

public class InvalidFieldException extends Exception{

	private static final long serialVersionUID = -6163260530840777576L;

	public InvalidFieldException() {
		super();
	}

	public InvalidFieldException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidFieldException(String message) {
		super(message);
	}
}