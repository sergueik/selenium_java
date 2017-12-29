package org.jenkins.plugins.audit2db.pipeline;

public class Audit2DbPipelineStepException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public Audit2DbPipelineStepException() {
		super();
	}

	public Audit2DbPipelineStepException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public Audit2DbPipelineStepException(String message, Throwable cause) {
		super(message, cause);
	}

	public Audit2DbPipelineStepException(String message) {
		super(message);
	}

	public Audit2DbPipelineStepException(Throwable cause) {
		super(cause);
	}

}
