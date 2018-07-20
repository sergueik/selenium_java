package jcucumberng.framework.exceptions;

/**
 * {@code LoggerConfigException} is thrown when an exception is encountered
 * while loading the log4j2 config file from a specified directory.
 * 
 * @author Kat Rollo <rollo.katherine@gmail.com>
 */
@SuppressWarnings("serial")
public class LoggerConfigException extends RuntimeException {
	public LoggerConfigException(String message) {
		super(message);
	}
}
