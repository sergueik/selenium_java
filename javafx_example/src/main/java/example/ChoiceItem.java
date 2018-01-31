package example;
// origin: https://github.com/prasser/swtchoices

public class ChoiceItem {

	private String text;
	private int index;

	public ChoiceItem(String text, int index) {
		if (text == null) {
			throw new IllegalArgumentException("Null is not a valid argument");
		}
		this.text = text;
		this.index = index;
	}

	public String getText() {
		return text;
	}

	public int getIndex() {
		return index;
	}
}