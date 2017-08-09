package ar.com.todopago.api.exceptions;

public class EmptyFieldPassException extends EmptyFieldException {

	private static final long serialVersionUID = -291250906064703272L;

	public EmptyFieldPassException() {
		super();
	}

	public EmptyFieldPassException(String message, Throwable cause) {
		super(message, cause);
	}

	public EmptyFieldPassException(String message) {
		super(message);
	}
}
