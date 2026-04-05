package example.exception;

public class BrowserLaunchException extends NihoniumException {

	public BrowserLaunchException(String message) {
		super(message);
	}

	public BrowserLaunchException(String message, Throwable cause) {
		super(message, cause);
	}
}
