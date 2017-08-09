package ar.com.todopago.api.exceptions;

public class ConnectionException extends Exception{

	private static final long serialVersionUID = 810364209354623523L;

	public ConnectionException() {
		super();
	}

	public ConnectionException(String message, Throwable cause) {
		super(message, cause);
	}

	public ConnectionException(String message) {
		super(message);
	}
}
