package com.github.sergueik.jprotractor.scripts;

/**
 * Class thrown when a script failed to load.
 * @author Carlos Alexandro Becker (caarlos0@gmail.com)
 */
public final class ScriptLoadException extends RuntimeException {
	/**
	 * Exception message format.
	 */
	public static final String MESSAGE = "Failed to get script contents for file %s";

	/**
	 * Ctor.
	 * @param cause Cause of the error.
	 * @param filename Name of the file caused the failure.
	 */
	public ScriptLoadException(final Throwable cause, final String filename) {
		super(String.format(ScriptLoadException.MESSAGE, filename), cause);
	}

	/**
	 * Ctor.
	 * @param filename Name of the file caused the failure.
	 */
	public ScriptLoadException(final String filename) {
		super(String.format(ScriptLoadException.MESSAGE, filename));
	}

	private static final long serialVersionUID = 10;
}
