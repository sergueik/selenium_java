package example;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

import java.sql.*;

import org.sqlite.SQLiteConfig;

import example.AddChangePasswAbstr.*;
import javafx.scene.Node;
import javafx.stage.Window;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;

@SuppressWarnings("restriction")

public class Controller {

	private CollectionEditable passbookimpl = new CollectionEditable();

	private Stage mainStage;

	@FXML
	public Button addButton;
	@FXML
	public Button changeButton;
	@FXML
	private Button delButton;

	@FXML
	private TableView tablePassBook;
	@FXML
	private TableColumn<Website, String> columnSite;
	@FXML
	private TableColumn<Website, String> columnSiteLogin;
	@FXML
	private TableColumn<Website, String> columnSitePass;
	@FXML
	private TableColumn<Website, String> columnFtp;
	@FXML
	private TableColumn<Website, String> columnFtpLogin;
	@FXML
	private TableColumn<Website, String> columnFtpPass;
	@FXML
	private TableColumn<Website, String> columnPort;
	@FXML
	private TableColumn<Website, String> columnNotes;
	@FXML
	private TextField filterField;

	private Parent fxmlEdit;
	private Parent fxmlEdit1;
	private FXMLLoader fxmlLoader = new FXMLLoader();
	private FXMLLoader fxmlLoader1 = new FXMLLoader();

	private Addpassw addpassw;
	private Editpassw editpassw;
	private Stage stage;
	private Stage changestage;

	private static Statement stat;
	private static Connection c;

	public void setMainStage(Stage mainStage) {
		this.mainStage = mainStage;
	}

	FilteredList<Website> filteredData = new FilteredList<>(
			passbookimpl.getPassbook(), p -> true);

	@FXML
	private void initialize() {
		GlobalData.setController(this);
		columnSite
				.setCellValueFactory(new PropertyValueFactory<Website, String>("site"));
		columnSiteLogin.setCellValueFactory(
				new PropertyValueFactory<Website, String>("siteLogin"));
		columnSitePass.setCellValueFactory(
				new PropertyValueFactory<Website, String>("sitePass"));
		columnFtp
				.setCellValueFactory(new PropertyValueFactory<Website, String>("ftp"));
		columnFtpLogin.setCellValueFactory(
				new PropertyValueFactory<Website, String>("ftpLogin"));
		columnFtpPass.setCellValueFactory(
				new PropertyValueFactory<Website, String>("ftpPass"));
		columnPort
				.setCellValueFactory(new PropertyValueFactory<Website, String>("port"));
		columnNotes.setCellValueFactory(
				new PropertyValueFactory<Website, String>("notes"));

		try {
			@SuppressWarnings("unused")
			SQLiteConfig config = new SQLiteConfig();
			c = DB.getInstance().getConnection();
			stat = c.createStatement();
			stat.execute(
					"CREATE TABLE IF NOT EXISTS data(id INTEGER PRIMARY KEY AUTOINCREMENT, site VARCHAR(255), siteLogin VARCHAR(255), sitePass VARCHAR(255), ftp VARCHAR(255), ftpLogin VARCHAR(255), ftpPass VARCHAR(255), port VARCHAR(255),  notes VARCHAR(255));");
		} catch (SQLException ex) {
			System.out.println("test");
		}
		fillMainTable();

		tablePassBook.setItems(passbookimpl.getPassbook());
		tablePassBook.getSelectionModel().setCellSelectionEnabled(true);
		tablePassBook.setOnKeyPressed(new TableKeyEventHandler());
		tablePassBook.setOnMouseClicked(new TableMouseEventHandler());

		try {
			fxmlLoader.setLocation(getClass().getResource("/addpassw.fxml"));
			fxmlEdit = fxmlLoader.load();
			addpassw = fxmlLoader.getController();
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			fxmlLoader1.setLocation(getClass().getResource("/editpassw.fxml"));
			fxmlEdit1 = fxmlLoader1.load();
			editpassw = fxmlLoader1.getController();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void fillMainTable() {
		try {
			c = DB.getInstance().getConnection();
			ResultSet rs = c.createStatement().executeQuery(
					"SELECT id, site, siteLogin, sitePass, ftp, ftpLogin, ftpPass, port, notes FROM data");
			while (rs.next()) {
				int id;
				String site;
				String siteLogin;
				String sitePass;
				String ftp;
				String ftpLogin;
				String ftpPass;
				String port;
				String notes;
				id = rs.getInt("id");
				site = rs.getString("site");
				siteLogin = rs.getString("siteLogin");
				sitePass = rs.getString("sitePass");
				ftp = rs.getString("ftp");
				ftpLogin = rs.getString("ftpLogin");
				ftpPass = rs.getString("ftpPass");
				port = rs.getString("port");
				notes = rs.getString("notes");

				Website website = new Website(id, site, siteLogin, sitePass, ftp,
						ftpLogin, ftpPass, port, notes);
				// System.out.println(website);
				passbookimpl.getPassbook().add(website);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// https://stackoverflow.com/questions/44807580/javafx-access-parent-controller-class-from-fxml-child?rq=1
	// TODO:
	// https://stackoverflow.com/questions/35372236/how-to-pass-paremeter-with-an-event-in-javafx/35373298
	// swing:
	// http://www.java2s.com/Code/Java/Event/CreatingaCustomEvent.htm

	public void buttonPressed(ActionEvent actionEvent) {

		Object source = actionEvent.getSource();

		if (!(source instanceof Button)) {
			return;
		}

		Button button = (Button) source;
		Website selectedWebsite = (Website) tablePassBook.getSelectionModel()
				.getSelectedItem();
		Window parentWindow = ((Node) actionEvent.getSource()).getScene()
				.getWindow();

		switch (button.getId()) {
		case "addButton":
			addpassw.setWebsite(new Website());
			showDialog();
			passbookimpl.add(addpassw.getWebsite());
			break;
		case "changeButton":

			if (tablePassBook.getSelectionModel().getSelectedItem() != null) {
				editpassw.setWebsite(
						(Website) tablePassBook.getSelectionModel().getSelectedItem());
				changeDialog();
			}
			break;
		case "delButton":
			passbookimpl.delete(
					(Website) tablePassBook.getSelectionModel().getSelectedItem());
			try {
				c = DB.getInstance().getConnection();
				PreparedStatement statement = c
						.prepareStatement("DELETE FROM data WHERE id = ?");
				statement.setInt(1, selectedWebsite.getId());
				statement.executeUpdate();
			} catch (SQLException ex) {
				// System.out.println("test");
				ex.printStackTrace();
			}
			break;
		}
	}

	private void showDialog() {
		if (stage == null) {
			stage = new Stage();
			stage.setTitle("test");
			stage.setHeight(650);
			stage.setWidth(800);
			stage.setScene(new Scene(fxmlEdit));
			stage.initModality(Modality.WINDOW_MODAL);
			// stage.setResizable(false);
			stage.initOwner(mainStage);
		}
		stage.showAndWait();
	}

	private void changeDialog() {
		if (changestage == null) {
			changestage = new Stage();
			changestage.setTitle("test");
			changestage.setHeight(650);
			changestage.setWidth(800);
			changestage.setScene(new Scene(fxmlEdit1));
			changestage.initModality(Modality.WINDOW_MODAL);
			// stage.setResizable(false);
			changestage.initOwner(mainStage);
		}
		changestage.showAndWait();
	}

	@SuppressWarnings("unchecked")
	public void filterField(KeyEvent keyEvent) {
		filterField.textProperty().addListener((observable, oldValue, newValue) -> {

			filteredData.setPredicate(website -> {

				if (newValue == null || newValue.isEmpty()) {
					return true;
				}
				String lowerCaseFilter = newValue.toLowerCase();

				if (website.getSite().toLowerCase().contains(lowerCaseFilter)) {
					return true;
				} else if (website.getSiteLogin().toLowerCase()
						.contains(lowerCaseFilter)) {
					return true;
				} else if (website.getFtp().toLowerCase().contains(lowerCaseFilter)) {
					return true;
				} else if (website.getFtpLogin().toLowerCase()
						.contains(lowerCaseFilter)) {
					return true;
				} else if (website.getNotes().toLowerCase().contains(lowerCaseFilter)) {
					return true;
				}

				return false;
			});

		});

		SortedList<Website> sortedData = new SortedList<>(filteredData);
		sortedData.comparatorProperty().bind(tablePassBook.comparatorProperty());
		tablePassBook.setItems(sortedData);
	}

	public static class TableKeyEventHandler implements EventHandler<KeyEvent> {
		KeyCodeCombination copyKeyCodeCompination = new KeyCodeCombination(
				KeyCode.C, KeyCombination.CONTROL_ANY);

		public void handle(final KeyEvent keyEvent) {
			if (copyKeyCodeCompination.match(keyEvent)) {
				if (keyEvent.getSource() instanceof TableView) {
					copySelectionToClipboard((TableView<?>) keyEvent.getSource());
					System.out.println("test");
					keyEvent.consume();
				}
			}
		}
	}

	public static class TableMouseEventHandler
			implements EventHandler<MouseEvent> {

		public void handle(final MouseEvent mouseEvent) {
			if (mouseEvent.getClickCount() == 2) {
				if (mouseEvent.getSource() instanceof TableView) {
					copySelectionToClipboard((TableView<?>) mouseEvent.getSource());
					System.out.println("test");
					mouseEvent.consume();
				}
			}
		}

	}

	@SuppressWarnings("rawtypes")
	public static void copySelectionToClipboard(TableView<?> table) {
		StringBuilder clipboardString = new StringBuilder();
		ObservableList<TablePosition> positionList = table.getSelectionModel()
				.getSelectedCells();
		int prevRow = -1;
		for (TablePosition position : positionList) {
			int row = position.getRow();
			int col = position.getColumn();
			Object cell = (Object) table.getColumns().get(col).getCellData(row);
			if (cell == null) {
				cell = "";
			}
			if (prevRow == row) {
				clipboardString.append('\t');
			} else if (prevRow != -1) {
				clipboardString.append('\n');
			}
			String text = cell.toString();
			clipboardString.append(text);
			prevRow = row;
		}
		final ClipboardContent clipboardContent = new ClipboardContent();
		clipboardContent.putString(clipboardString.toString());
		Clipboard.getSystemClipboard().setContent(clipboardContent);
	}

}
