package simplefxbg;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

@SuppressWarnings("restriction")
public class SimpleFXBGController implements Initializable {
	@FXML
	Button buttonSend;
	@FXML
	Button buttonStop;
	@FXML
	ListView<String> listView;
	@FXML
	TextField textToSend;
	private ObservableList<String> data;
	private SimpleBG simpleBG;

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		data = FXCollections.observableArrayList();
		// associate
		listView.setItems(data);
		simpleBG = new SimpleBG(this);
	}

	@FXML
	private void handleSendAction(ActionEvent event) {
		@SuppressWarnings("unused")
		int queueDepth = simpleBG.receiveMsg(textToSend.getText());
	}

	@FXML
	private void handleStopAction(ActionEvent event) {
		System.exit(0);
	}

	public void onMessage(String message) {
		data.add(message);
	}
}
