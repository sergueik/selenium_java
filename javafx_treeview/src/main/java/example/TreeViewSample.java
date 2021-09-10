package example;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * based on Oracle's example at
 * http://docs.oracle.com/javafx/2/ui_controls/tree-view.htm
 */

@SuppressWarnings("restriction")
public class TreeViewSample extends Application {
	private static CommandLineParser commandLineparser = new DefaultParser();
	private static CommandLine commandLine = null;
	private final static Options options = new Options();
	private static final Node rootIcon = new ImageView(
			new Image(TreeViewSample.class.getResourceAsStream("/zoom.png")));
	private final Image depIcon = new Image(getClass().getResourceAsStream("/zip.png"));

	private static List<Employee> employees = new ArrayList<>();

	private static TreeItem<String> rootNode = new TreeItem<>("RRD Resource Directory", rootIcon);

	public static void main(String[] args) {
		options.addOption("h", "help", false, "Help");
		options.addOption("d", "debug", false, "Debug");
		options.addOption("r", "resources", true, "Resources");
		try {
			commandLine = commandLineparser.parse(options, args);
			if (commandLine.hasOption("h")) {
				help();
			}
			String resources = commandLine.getOptionValue("resources");
			if (resources == null) {
				System.err.println("Missing required argument: resources");
				return;
			}
			rootNode = new TreeItem<>("RRD Resource Directory", rootIcon);
			for (String resource : resources.split(",")) {
				String[] details = resource.split(":");
				String file = details[2];
				String dir = details[1];
				employees.add(new Employee(file, dir));
			}
			launch(args);
		} catch (ParseException e) {
		}
	}

	@Override
	public void start(Stage stage) {
		rootNode.setExpanded(true);
		for (Employee employee : employees) {
			TreeItem<String> empLeaf = new TreeItem<>(employee.getName());
			boolean found = false;
			for (TreeItem<String> depNode : rootNode.getChildren()) {
				if (depNode.getValue().contentEquals(employee.getDepartment())) {
					depNode.getChildren().add(empLeaf);
					found = true;
					break;
				}
			}
			if (!found) {
				TreeItem<String> depNode = new TreeItem<>(employee.getDepartment(), new ImageView(depIcon));
				rootNode.getChildren().add(depNode);
				depNode.getChildren().add(empLeaf);
			}
		}

		stage.setTitle("Tree View Sample");
		VBox box = new VBox();
		final Scene scene = new Scene(box, 400, 300);
		scene.setFill(Color.LIGHTGRAY);

		TreeView<String> treeView = new TreeView<>(rootNode);
		treeView.setEditable(true);
		treeView.setCellFactory(e -> new TextFieldTreeCellImpl());
		box.getChildren().add(treeView);
		stage.setScene(scene);
		stage.show();
	}

	public static class Employee {
		private final SimpleStringProperty name;
		private final SimpleStringProperty department;

		public Employee(String name, String department) {
			this.name = new SimpleStringProperty(name);
			this.department = new SimpleStringProperty(department);
		}

		public String getName() {
			return name.get();
		}

		public SimpleStringProperty nameProperty() {
			return name;
		}

		public void setName(String data) {
			this.name.set(data);
		}

		public String getDepartment() {
			return department.get();
		}

		public SimpleStringProperty departmentProperty() {
			return department;
		}

		public void setDepartment(String data) {
			this.department.set(data);
		}
	}

	private final class TextFieldTreeCellImpl extends TreeCell<String> {

		private TextField textField;
		private ContextMenu addMenu = new ContextMenu();

		public TextFieldTreeCellImpl() {
			MenuItem addMenuItem = new MenuItem("Add Employee");
			addMenu.getItems().add(addMenuItem);
			addMenuItem.setOnAction(event -> {
				TreeItem<String> newEmployee = new TreeItem<>("New Employee");
				getTreeItem().getChildren().add(newEmployee);
			});
		}

		private String getString() {
			return getItem() == null ? "" : getItem();
		}

		private void createTextField() {
			textField = new TextField(getString());
			textField.setOnKeyReleased(new EventHandler<KeyEvent>() {
				@Override
				public void handle(KeyEvent event) {
					if (event.getCode() == KeyCode.ENTER)
						commitEdit(textField.getText());
					else if (event.getCode() == KeyCode.ESCAPE)
						cancelEdit();
				}
			});
		}

		@Override
		public void startEdit() {
			super.startEdit();
			if (textField == null) {
				/*
				 * instantiate the textField with the curr text and install an EventHandler to
				 * have it commit on ENTER and cancel on ESCAPE
				 */
				createTextField();
			}
			setText(null); // get the text out of the way
			setGraphic(textField); // replace the text with the textField
			textField.selectAll(); // select the text inside the input box
		}

		@Override
		public void cancelEdit() {
			super.cancelEdit();
			/*
			 * set the Cell's Label to contain the content of its underlying
			 * TreeItem<String> item; property
			 */
			setText(getItem());
			/*
			 * Labelled Nodes have "graphic" properties that are located in a configurable
			 * location wrt to their text
			 */
			setGraphic(getTreeItem().getGraphic());
		}

		/**
		 * this gets called automagically by the framework THIS IS WHERE THE ORIGINAL
		 * TEXT GETS SET! if you don't override this baby right, everything goes haywire
		 * the most important thing is calling super.
		 */
		@Override
		protected void updateItem(String item, boolean empty) {
			super.updateItem(item, empty);
			if (!empty) {
				if (isEditing()) {
					if (textField != null)
						textField.setText(getString());
					setText(null);
					setGraphic(textField);
				} else {
					setText(getString());
					setGraphic(getTreeItem().getGraphic());
					if (!getTreeItem().isLeaf() && getTreeItem().getParent() != null)
						setContextMenu(addMenu);
				}
			} else {
				setText(null);
				setGraphic(null);
			}
		}

	}

	public static void help() {
		System.exit(1);
	}
}
