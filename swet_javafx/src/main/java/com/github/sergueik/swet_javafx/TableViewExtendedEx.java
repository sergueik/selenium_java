package com.github.sergueik.swet_javafx;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import example.CommandLineParser;
import javafx.application.Application;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Callback;

// NOTE: temporarily removed dependencies - reappear in the following commit
import org.apache.log4j.Category;
import example.CommandLineParser;

// based on: https://gist.github.com/haisi/0a82e17daf586c9bab52
// see also:
//
// https://docs.oracle.com/javase/8/javafx/api/javafx/scene/control/cell/ComboBoxTableCell.html
// https://stackoverflow.com/questions/22665322/change-itemlist-in-a-combobox-depending-from-another-combobox-choice/35590119#35590119
// https://stackoverflow.com/questions/35131428/combobox-in-a-tableview-cell-in-javafx
// https://gist.github.com/james-d/bf28c5d5343a8fd79b0e
// https://o7planning.org/en/11079/javafx-tableview-tutorial
// https://www.programcreek.com/java-api-examples/index.php?api=javafx.scene.control.cell.ComboBoxTableCell
// http://code.makery.ch/blog/javafx-8-event-handling-examples/
// http://spec-zone.ru/RU/Java/FX/8/docs/api/javafx/fxml/doc-files/introduction_to_fxml.html
// https://vk.com/doc-80984752_367966549?hash=516410f2641275bd32&dl=0b5022d49b8697a09f
// http://code.makery.ch/library/javafx-8-tutorial/ru/part1/
// https://stackoverflow.com/questions/7217625/how-to-add-checkboxs-to-a-tableview-in-javafx
// https://stackoverflow.com/questions/26631041/javafx-combobox-with-checkboxes
// https://stackoverflow.com/questions/52467979/checkbox-and-combobox-javafx/52469980

@SuppressWarnings({ "restriction", "unused" })

public class TableViewExtendedEx extends Application {

	// connect with other widgets in the bundle
	private Map<String, Object> inputs = new HashMap<>();
	private Scene parentScene = null;
	private Map<String, String> inputData = new HashMap<>();
	private static Map<String, String> inputJSONData = new HashMap<>();
	private static final StringBuffer dataDumper = new StringBuffer();
	@SuppressWarnings("unused")
	private static boolean debug = false;
	private static CommandLineParser commandLineParser;

	private TableView<Person> table = new TableView<>();
	private final ObservableList<Choice> choiceData = FXCollections
			.observableArrayList(new Choice("Yes"), new Choice("No"),
					new Choice("Maybe"), new Choice("Maybe not"));
	private final ObservableList<Person> data = FXCollections.observableArrayList(
			new Person("john", choiceData.get(0), new Date()),
			new Person("paul", choiceData.get(1), new Date()),
			new Person("george", choiceData.get(2), new Date()),
			new Person("ringo", choiceData.get(3), new Date()));

	final HBox hb = new HBox();

	@SuppressWarnings("deprecation")
	static final Category logger = Category
			.getInstance(TableViewExtendedEx.class);

	@SuppressWarnings("unchecked")
	private void loadInputData(Map<String, Object> inputs) {
		try {
			inputData = (Map<String, String>) inputs.get("inputs");
			if (inputData.keySet().size() == 0) {
				throw new IllegalArgumentException("You must provide data");
			}
			logger.info("Loaded input data: " + inputData.toString());
		} catch (ClassCastException e) {
			logger.info("Exception (ignored) " + e.toString());
		} catch (Exception e) {
			logger.info("Exception (rethrown) " + e.toString());
			throw e;
		}
	}

	@SuppressWarnings("unchecked")
	public void setScene(Scene scene) {
		parentScene = scene;

		if (parentScene != null) {
			loadInputData((Map<String, Object>) parentScene.getUserData());
		}
		logger.info("Loaded " + inputData);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void start(Stage stage) {

		Scene scene = new Scene(new Group(), 480, 250, Color.WHITE);
		stage.setWidth(750);
		stage.setHeight(550);
		stage.setScene(scene);

		stage.setTitle(inputData.containsKey("title") ? inputData.get("title")
				: "Element Locators");
		// final StringBuffer dataDumper = new StringBuffer();

		Utils.readData(inputData.get("json"), Optional.of(inputJSONData));

		for (String dataKey : inputJSONData.keySet()) {
			if (!dataKey.equals(Utils.getDefaultKey())) {
				dataDumper.append(dataKey);
				dataDumper.append(" ");
			}
		}
		final Label label = new Label("Component Entry");
		// platform
		label.setFont(new Font("Arial", 20));
		logger.info("json:" + inputData.get("json"));
		label.setText("Component Entry " + dataDumper.toString());
		table.setEditable(true);
		Callback<TableColumn<Person, String>, TableCell<Person, String>> cellFactory = (
				TableColumn<Person, String> param) -> new EditingCell();
		Callback<TableColumn<Person, Date>, TableCell<Person, Date>> dateCellFactory = (
				TableColumn<Person, Date> param) -> new DateEditingCell();

		Callback<TableColumn<Person, Choice>, TableCell<Person, Choice>> comboBoxCellFactory = (
				TableColumn<Person, Choice> param) -> new ComboBoxEditingCell();

		TableColumn<Person, String> nameCol = new TableColumn<Person, String>(
				"Name");
		nameCol.setMinWidth(330);
		nameCol.setCellValueFactory(o -> o.getValue().firstNameProperty());
		nameCol.setCellFactory(cellFactory);
		// NOTE: dropped specifying parameter type
		// TableColumn.CellEditEvent<Person, String>
		nameCol.setOnEditCommit(o -> {
			((Person) o.getTableView().getItems().get(o.getTablePosition().getRow()))
					.setFirstName(o.getNewValue());

		});

		TableColumn<Person, Choice> choiceCol = new TableColumn<Person, Choice>(
				"Choice");
		choiceCol.setMinWidth(100);
		choiceCol.setCellValueFactory(o -> o.getValue().getChoiceProperty());
		choiceCol.setCellFactory(comboBoxCellFactory);
		// NOTE: dropped specifying parameter type
		// TableColumn.CellEditEvent<Person, Choice>
		choiceCol.setOnEditCommit(o -> {
			((Person) o.getTableView().getItems().get(o.getTablePosition().getRow()))
					.setChoiceObj(o.getNewValue());

		});

		TableColumn<Person, Date> dateCol = new TableColumn<Person, Date>("Date");
		dateCol.setMinWidth(200);
		dateCol.setCellValueFactory(
				cellData -> cellData.getValue().birthdayProperty());
		dateCol.setCellFactory(dateCellFactory);
		// NOTE: dropped specifying parameter type
		// TableColumn.CellEditEvent<Person, Data>
		dateCol.setOnEditCommit(o -> {
			((Person) o.getTableView().getItems().get(o.getTablePosition().getRow()))
					.setBirthday(o.getNewValue());

		});

		TableColumn<Person, Boolean> pickCol = new TableColumn<Person, Boolean>(
				"Include");
		pickCol.setMinWidth(30);
		pickCol.setCellValueFactory(o -> o.getValue().selectedProperty());
		pickCol.setCellFactory(o -> new CheckBoxTableCell<>());
		// no need to worry on custom editor

		table.setItems(data);
		table.getColumns().addAll(nameCol, choiceCol, dateCol, pickCol);

		final TextField addNameText = new TextField();
		addNameText.setPromptText("Name");
		addNameText.setMaxWidth(nameCol.getPrefWidth());

		final ComboBox<String> addChoiceComboBox = new ComboBox<String>();
		for (Choice choice : choiceData) {
			addChoiceComboBox.getItems().add(choice.getChoice());
		}
		// use default processing

		DatePicker addDateDatePicker = new DatePicker();

		final Button addButton = new Button("Add");
		addButton.setOnAction((ActionEvent e) -> {

			data.add(new Person(addNameText.getText(),
					new Choice(addChoiceComboBox.getValue().toString()),
					// see also
					// https://stackoverflow.com/questions/33066904/localdate-to-java-util-date-and-vice-versa-simplest-conversion
					// https://stackoverflow.com/questions/56380472/java-time-datetimeexception-invalid-id-for-region-based-zoneid-invalid-format
					(addDateDatePicker.getValue()) != null
							? Date.from(addDateDatePicker.getValue()
									.atStartOfDay(ZoneId.of("America/New_York")).toInstant())
							: new Date()));
			addNameText.clear();
			addChoiceComboBox.setValue("");
			addDateDatePicker.setValue(null);
		});

		final Button updateButton = new Button("Update");
		updateButton.setOnAction(new EventHandler<ActionEvent>() {
			@SuppressWarnings("deprecation")
			@Override
			public void handle(ActionEvent event) {

				final Set<Person> delSet = new HashSet<>();
				final Set<Person> selSet = new HashSet<>();
				for (final Person resource : table.getItems()) {
					if (!resource.selectedProperty().get()) {
						delSet.add(resource);
					} else {
						selSet.add(resource);
					}
				}
				table.getItems().removeAll(delSet);
				System.err.println("Processing selected rows:");
				for (final Person resource : selSet) {
					System.err.println(
							String.format("name: %s choice: %s date: %s included: %s",
									resource.getFirstName(), resource.getChoice(),
									resource.getBirthday().toLocaleString(),
									resource.selectedProperty().get()));
				}
				// sleep(500);
				// Stage stage = (Stage) ((Button)
				// (event.getSource())).getScene().getWindow();
				// stage.close();
			}
		});
		updateButton.setDefaultButton(true);
		// Cancel button
		final Button cancelButton = new Button("Cancel");
		cancelButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				System.err.println("Cancel");
				Stage stage = (Stage) ((Button) (event.getSource())).getScene()
						.getWindow();
				stage.close();
			}
		});

		hb.getChildren().addAll(addNameText, addChoiceComboBox, addDateDatePicker,
				addButton, updateButton, cancelButton);
		hb.setSpacing(3);

		final VBox vbox = new VBox();
		vbox.setSpacing(5);
		vbox.setPadding(new Insets(10, 0, 0, 10));
		vbox.getChildren().addAll(label, table, hb);

		((Group) scene.getRoot()).getChildren().addAll(vbox);

		stage.setScene(scene);
		stage.show();
	}

	public static void main(String[] args) {
		commandLineParser = new CommandLineParser();

		commandLineParser.parse(args);

		if (commandLineParser.hasFlag("debug")) {
			debug = true;
		}

		launch(args);
	}

	class EditingCell extends TableCell<Person, String> {

		private TextField textField;

		private EditingCell() {
		}

		@Override
		public void startEdit() {
			if (!isEmpty()) {
				super.startEdit();
				createTextField();
				setText(null);
				setGraphic(textField);
				textField.requestFocus();
           			// textField.selectAll(); commenting out this because
            			// JavaFX confuses the caret position.
            			// Seems to be a bug.
			}
		}

		@Override
		public void cancelEdit() {
			super.cancelEdit();

			setText((String) getItem());
			setGraphic(null);
		}

		@Override
		public void updateItem(String item, boolean empty) {
			super.updateItem(item, empty);

			if (empty) {
				setText(item);
				setGraphic(null);
			} else {
				if (isEditing()) {
					if (textField != null) {
						textField.setText(getString());
						// setGraphic(null);
					}
					setText(null);
					setGraphic(textField);
				} else {
					setText(getString());
					setGraphic(null);
				}
			}
		}

		private void createTextField() {

			textField = new TextField(getString());
			textField.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
			textField.setOnAction((e) -> commitEdit(textField.getText()));
			textField.focusedProperty()
					.addListener((ObservableValue<? extends Boolean> observable,
							Boolean oldValue, Boolean newValue) -> {
						if (!newValue) {
							System.out.println("Updating: " + textField.getText());
							commitEdit(textField.getText());
						}
					});
		}

		private String getString() {
			return getItem() == null ? "" : getItem();
		}
	}

	class DateEditingCell extends TableCell<Person, Date> {

		private DatePicker datePicker;

		private DateEditingCell() {
		}

		@Override
		public void startEdit() {
			if (!isEmpty()) {
				super.startEdit();
				createDatePicker();
				setText(null);
				setGraphic(datePicker);
			}
		}

		@Override
		public void cancelEdit() {
			super.cancelEdit();

			setText(getDate().toString());
			setGraphic(null);
		}

		@Override
		public void updateItem(Date item, boolean empty) {
			super.updateItem(item, empty);

			if (empty) {
				setText(null);
				setGraphic(null);
			} else {
				if (isEditing()) {
					if (datePicker != null) {
						datePicker.setValue(getDate());
					}
					setText(null);
					setGraphic(datePicker);
				} else {
					setText(getDate()
							.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)));
					setGraphic(null);
				}
			}
		}

		private void createDatePicker() {
			datePicker = new DatePicker(getDate());
			datePicker.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
			datePicker.setOnAction((e) -> {
				System.out.println("Committed: " + datePicker.getValue().toString());
				commitEdit(Date.from(datePicker.getValue()
						.atStartOfDay(ZoneId.systemDefault()).toInstant()));
			});
			// datePicker.focusedProperty().addListener((ObservableValue<? extends
			// Boolean> observable, Boolean oldValue, Boolean newValue) -> {
			// if (!newValue) {
			// commitEdit(Date.from(datePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
			// }
			// });
		}

		private LocalDate getDate() {
			return getItem() == null ? LocalDate.now()
					: getItem().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		}
	}

	class ComboBoxEditingCell extends TableCell<Person, Choice> {

		private ComboBox<Choice> comboBox;

		private ComboBoxEditingCell() {
		}

		@Override
		public void startEdit() {
			if (!isEmpty()) {
				super.startEdit();
				createComboBox();
				setText(null);
				setGraphic(comboBox);
			}
		}

		@Override
		public void cancelEdit() {
			super.cancelEdit();

			setText(getChoice().getChoice());
			setGraphic(null);
		}

		@Override
		public void updateItem(Choice item, boolean empty) {
			super.updateItem(item, empty);

			if (empty) {
				setText(null);
				setGraphic(null);
			} else {
				if (isEditing()) {
					if (comboBox != null) {
						comboBox.setValue(getChoice());
					}
					setText(getChoice().getChoice());
					setGraphic(comboBox);
				} else {
					setText(getChoice().getChoice());
					setGraphic(null);
				}
			}
		}

		private void createComboBox() {
			comboBox = new ComboBox<>(choiceData);
			comboBoxConverter(comboBox);
			comboBox.valueProperty().set(getChoice());
			comboBox.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
			comboBox.setOnAction((e) -> {
				System.out.println(
						"Committed: " + comboBox.getSelectionModel().getSelectedItem());
				commitEdit(comboBox.getSelectionModel().getSelectedItem());
			});
			// comboBox.focusedProperty().addListener((ObservableValue<? extends
			// Boolean> observable, Boolean oldValue, Boolean newValue) -> {
			// if (!newValue) {
			// commitEdit(comboBox.getSelectionModel().getSelectedItem());
			// }
			// });
		}

		private void comboBoxConverter(ComboBox<Choice> comboBox) {
			// Define rendering of the list of values in ComboBox drop down.
			comboBox.setCellFactory((c) -> {
				return new ListCell<Choice>() {
					@Override
					protected void updateItem(Choice item, boolean empty) {
						super.updateItem(item, empty);

						if (item == null || empty) {
							setText(null);
						} else {
							setText(item.getChoice());
						}
					}
				};
			});
		}

		private Choice getChoice() {
			return getItem() == null ? new Choice("") : getItem();
		}
	}

	public static class Choice {

		private final SimpleStringProperty choice;

		public Choice(String choice) {
			this.choice = new SimpleStringProperty(choice);
		}

		public String getChoice() {
			return choice.get();
		}

		public StringProperty choiceProperty() {
			return choice;
		}

		public void setChoice(String value) {
			choice.set(value);
		}

		@Override
		public String toString() {
			return choice.get();
		}

	}

	public static class Project {
		private final SimpleStringProperty name;
		private final SimpleListProperty<Person> persons;

		public Project(String name, List<Person> persons) {
			this.name = new SimpleStringProperty(name);

			this.persons = new SimpleListProperty<>();
			this.persons.setAll(persons);
		}

		public String getName() {
			return name.get();
		}

		public StringProperty nameProperty() {
			return this.name;
		}

		public void setName(String name) {
			this.name.set(name);
		}

		public List<Person> getPersons() {
			return this.persons.get();
		}

		public SimpleListProperty<Person> personsProperty() {
			return this.persons;
		}

		public void setPersons(List<Person> persons) {
			this.persons.setAll(persons);
		}

	}

	public static class Person {

		private final SimpleStringProperty firstName;
		private final SimpleObjectProperty<Choice> choice;
		private final SimpleObjectProperty<Date> birthday;
		private final SimpleBooleanProperty selected;

		public Person(String firstName, Choice choice, Date bithday) {
			this.firstName = new SimpleStringProperty(firstName);
			this.choice = new SimpleObjectProperty<Choice>(choice);
			this.selected = new SimpleBooleanProperty();
			this.birthday = new SimpleObjectProperty<Date>(bithday);
		}

		public Person(String firstName, Choice choice, Date bithday,
				boolean selected) {
			this.firstName = new SimpleStringProperty(firstName);
			this.choice = new SimpleObjectProperty<Choice>(choice);
			this.selected = new SimpleBooleanProperty(selected);
			this.birthday = new SimpleObjectProperty<Date>(bithday);
		}

		public String getFirstName() {
			return firstName.get();
		}

		public StringProperty firstNameProperty() {
			return firstName;
		}

		public void setFirstName(String value) {
			firstName.set(value);
		}

		public Choice getChoiceObj() {
			return choice.get();
		}

		public ObjectProperty<Choice> getChoiceProperty() {
			return choice;
		}

		public String getChoice() {
			return choice.getValue().toString();
		}

		public void setChoiceObj(Choice value) {
			choice.set(value);
		}

		public Date getBirthday() {
			return birthday.get();
		}

		public ObjectProperty<Date> birthdayProperty() {
			return birthday;
		}

		public void setBirthday(Date value) {
			birthday.set(value);
		}

		public BooleanProperty selectedProperty() {
			return selected;
		}

		public void setSelected(boolean value) {
			selected.set(value);
		}

	}

}
