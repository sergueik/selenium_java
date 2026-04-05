package example.exception;

public class NihoniumException extends RuntimeException {

	public NihoniumException(String message) {
		super(message);
	}

	public NihoniumException(String message, Throwable cause) {
		super(message, cause);
	}

	public NihoniumException(Throwable cause) {
		super(cause);
	}
}
