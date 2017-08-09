package ar.com.todopago.api.exceptions;

public abstract class EmptyFieldException extends Exception {

	private static final long serialVersionUID = -6163260530840777576L;

	public EmptyFieldException() {
		super();
	}

	public EmptyFieldException(String message, Throwable cause) {
		super(message, cause);
	}

	public EmptyFieldException(String message) {
		super(message);
	}
}
