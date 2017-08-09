package ar.com.todopago.api.exceptions;

public class EmptyFieldUserException extends EmptyFieldException {

	private static final long serialVersionUID = -5726255761031659341L;

	public EmptyFieldUserException() {
		super();
	}

	public EmptyFieldUserException(String message, Throwable cause) {
		super(message, cause);
	}

	public EmptyFieldUserException(String message) {
		super(message);
	}
}
