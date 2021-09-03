package example;

// based on: https://stackoverflow.com/questions/44807580/javafx-access-parent-controller-class-from-fxml-child?rq=1
public class GlobalData {

	public static Controller controller;

	private GlobalData() {
	}

	public static Controller getController() {
		return controller;
	}

	public static void setController(Controller data) {
		controller = data;
	}
}