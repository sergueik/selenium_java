package example;

public class ValidationResult {
	private final boolean valid;
	private final String message;

	public boolean isValid() {
		return valid;
	}

	public String getMessage() {
		return message;
	}

	public ValidationResult(boolean valid, String message) {
		super();
		this.valid = valid;
		this.message = message;
	}

}
