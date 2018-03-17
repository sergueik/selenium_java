package example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Category;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

@SuppressWarnings("restriction")
// see also: https://github.com/4ntoine/JavaFxDialog/wiki
public class ChoicesDialog extends Stage {

	public static Stage stage;
	private Scene scene;
	private Image image;
	private String message;
	private List<ChoiceItem> choices = new ArrayList<>();

	static final Category logger = Category.getInstance(ChoicesDialog.class);

	public void addChoice(String text, int index) {
		this.choices.add(new ChoiceItem(text, index));
	}

	public Image getImage() {
		return image;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String data) {
		this.message = data;
	}

	// https://stackoverflow.com/questions/12830402/javafx-2-buttons-size-fill-width-and-are-each-same-width
	@SuppressWarnings("serial")
	public ChoicesDialog(Stage stage, Map<String, Integer> choicesMap) {
		super();
		Iterator<Entry<String, Integer>> choiceIterator = choicesMap.entrySet()
				.iterator();
		while (choiceIterator.hasNext()) {
			Map.Entry<String, Integer> choiceEntry = choiceIterator.next();
			this.addChoice(choiceEntry.getKey(), choiceEntry.getValue());
		}
		ChoicesDialog.stage = stage;
		initOwner(stage);
		setTitle("title");
		initModality(Modality.APPLICATION_MODAL);
		// https://stackoverflow.com/questions/8341305/how-to-remove-javafx-stage-buttons-minimize-maximize-close
		this.initStyle(StageStyle.UNDECORATED);
		Group root = new Group();
		HBox outerHBox = new HBox();
		outerHBox.setAlignment(Pos.CENTER);

		scene = new Scene(root, 300, 200);
		setScene(scene);

		// passing parameters is messy
		// https://stackoverflow.com/questions/34118025/javafx-pass-values-from-child-to-parent
		// https://stackoverflow.com/questions/14187963/passing-parameters-javafx-fxml
		scene.setUserData(new HashMap<String, String>() {
			{
				put("result", null);
			}
		});
		VBox outerVBox = new VBox();
		outerHBox.getChildren().add(outerVBox);
		outerHBox.setSpacing(12);
		scene.setRoot(outerHBox);

		HBox labelHBox = new HBox();
		Label label = new Label();
		label.setText("Do yo relly want to exit?");
		labelHBox.setHgrow(label, Priority.ALWAYS);
		labelHBox.getChildren().add(label);
		outerVBox.setSpacing(12);
		outerVBox.getChildren().addAll(labelHBox);

		int index = 0;
		for (ChoiceItem item : this.choices) {
			index++;
			HBox buttonHBox = new HBox();
			Button button = new Button();
			button.setText(item.getText());
			button.setMaxWidth(Double.MAX_VALUE);
			Map<String, String> userData = new HashMap<>();
			int itemIndex = item.getIndex();
			userData.put("index",
					String.format("%d", (itemIndex != 0 ? itemIndex : index)));
			// unlike SWT where supported widget data with every widget and offers
			// setData / getData methods to supply additional data that can later be
			// accessed
			// org.eclipse.swt.widgets.Widget.setData(String key, Object value)
			//
			// the almost all JavaFX objects javaFx only "recommends"
			// to limit it to controls which can be toggled between selected and
			// non-selected states.
			// it with stateful widgets
			// https://stackoverflow.com/questions/24615911/proper-uses-of-javafx-setuserdata
			//
			// https://docs.oracle.com/javase/8/javafx/api/javafx/scene/Scene.html

			button.setUserData(userData);
			button.setOnAction(new buttonHandler());
			buttonHBox.setHgrow(button, Priority.ALWAYS);
			buttonHBox.getChildren().add(button);
			// Exception in thread "JavaFX Application Thread"
			// java.lang.IllegalArgumentException: Children: duplicate children
			// added..

			outerVBox.getChildren().add(buttonHBox);
		}

		outerVBox.setSpacing(12);
	}

	private static class buttonHandler implements EventHandler<ActionEvent> {
		@SuppressWarnings("unchecked")
		public void handle(ActionEvent event) {
			Button button = (Button) event.getSource();

			Map<String, String> data = (Map<String, String>) button.getUserData();
			String eventData = null;
			try {
				eventData = data == null ? null : data.getOrDefault("index", null);
			} catch (Exception e) {
				System.err.println("Exception (ignored) " + e.toString());
				eventData = null;
			}
			logger.info(
					"Received: " + (eventData == null ? "no user data" : eventData));

			logger.info("Returned: " + eventData);
			Map<String, String> sceneData = new HashMap<>();
			sceneData.put("result", eventData);
			Scene scene = button.getScene();
			scene.setUserData(sceneData);
			stage.close();
			logger.info("Closing stage(*) :" + stage);
		}
	}

	// origin: https://github.com/prasser/swtchoices
	private static class ChoiceItem {

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
}
