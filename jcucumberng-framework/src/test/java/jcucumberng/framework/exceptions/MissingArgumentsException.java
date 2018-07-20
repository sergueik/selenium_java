package jcucumberng.framework.exceptions;

/**
 * {@code MissingArgumentsException} is thrown when the arbitrary parameters or
 * varargs of a method is empty. All varargs in
 * {@code jcucumberng.framework.api.Selenium} require at least 1 parameter.
 * 
 * @author Kat Rollo <rollo.katherine@gmail.com>
 */
@SuppressWarnings("serial")
public class MissingArgumentsException extends RuntimeException {
	public MissingArgumentsException(String message) {
		super(message);
	}
}
