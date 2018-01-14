package example;

public class Fruit {
	private String name;

	public Fruit(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public String toString() {
		return String.format("'%s'", this.name);
	}
}