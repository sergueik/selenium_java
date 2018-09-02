package screen.measurement;

public enum Scale {
	PLACE_START(400, 200), PLACE_CHILD(200, 100);

	public double width;
	public double height;

	Scale(double width, double height) {
		this.width = width;
		this.height = height;
	}

}