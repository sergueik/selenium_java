package jcucumberng.steps.pojos;

public class Income {

	private String name = null;
	private String amount = null;
	private String frequency = null;

	public Income(String name, String amount, String frequency) {
		this.name = name;
		this.amount = amount;
		this.frequency = frequency;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getFrequency() {
		return frequency;
	}

	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Income [name=");
		builder.append(name);
		builder.append(", amount=");
		builder.append(amount);
		builder.append(", frequency=");
		builder.append(frequency);
		builder.append("]");
		return builder.toString();
	}

}
