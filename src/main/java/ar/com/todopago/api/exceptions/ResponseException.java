package ar.com.todopago.api.exceptions;

public class ResponseException extends Exception {

	private static final long serialVersionUID = 8809848093225139396L;

	public ResponseException() {
		super();
	}

	public ResponseException(String message, Throwable cause) {
		super(message, cause);
	}

	public ResponseException(String message) {
		super(message);
	}
}
