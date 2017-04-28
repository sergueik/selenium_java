package org.jutils.jprocesses.info;

import org.jutils.jprocesses.util.OSDetector;

/*
* @author Javier Garcia Alonso
*/
public class ProcessesFactory {

	private ProcessesFactory() {
	}

	public static ProcessesService getService() {
		if (OSDetector.isWindows()) {
			return new WindowsProcessesService();
		} else if (OSDetector.isUnix()) {
			return new UnixProcessesService();
		}

		throw new UnsupportedOperationException(
				"Your Operating System is not supported by this library.");
	}
}
