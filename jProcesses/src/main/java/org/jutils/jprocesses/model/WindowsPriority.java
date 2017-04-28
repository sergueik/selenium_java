package org.jutils.jprocesses.model;

/**
 * @author Javier Garcia Alonso
 */
public class WindowsPriority {

	public static final int IDLE = 64;
	public static final int BELOW_NORMAL = 16384;
	public static final int NORMAL = 32;
	public static final int ABOVE_NORMAL = 32768;
	public static final int HIGH = 128;
	public static final int REAL_TIME = 258;

	// Hide constructor
	private WindowsPriority() {
	}
}
