package com.sqlibri.presenter;

import com.sqlibri.App;
import com.sqlibri.control.editor.SQLEditor;
import com.sqlibri.model.Database;
import com.sqlibri.model.QueryResult;
import com.sqlibri.util.CSVParser;
import com.sqlibri.util.PrettyStatus;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Main Stage presenter responsible for handling event on main layout
 */
public class AppPresenter {

  private final String DB_ICON = "com/sqlibri/resources/image/database.png";
  private final String TABLE_ICON = "com/sqlibri/resources/image/table.png";
  private final String USER_GUIDE = "resources/layout/user-guide.fxml";
  private final String ABOUT = "resources/layout/about.fxml";

  private Stage window;

  private Database db;
  private QueryResult lastResult;

  @FXML private TreeView<String> dbStructure;
  @FXML private SQLEditor editor;
  @FXML private TableView<ObservableList<String>> table;
  @FXML private Label statusBar;
  @FXML private ComboBox<String> commands;


  /**
   * Setter for primary stage field,
   * which needed to hang event handler on it
   * Also, adds all shortcuts events to the given stage
   * @param primaryStage 
   */
  public void init(Stage primaryStage) {
    window = primaryStage;
    
    window.addEventHandler(KeyEvent.KEY_PRESSED, e -> {
      if (e.getCode() == KeyCode.F9)
        execute();
    });
    
    commands.valueProperty().addListener(e -> editor.pasteCode(commands.getSelectionModel().getSelectedItem()));
  }

  /**
   * Create database event handler [Database -> create or Ctrl+N]
   * Creates empty database file on the given path
   */
  @FXML public void createDb() {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Create Database File");
    File file = fileChooser.showSaveDialog(window);

    if (file == null) return;

    db = new Database(file);

    try {
      db.connect();
    } catch (SQLException e) {
      showErrorDialog("SQL ERROR:", e.getMessage());
    } catch (Exception e) {
      showErrorDialog("Unexpected ERROR:", e.getMessage());
    }

    loadTables(db.getFile());
  }

  /**
   * Drop database event handler [Database -> drop]
   * Drops opened database
   */
  @FXML public void dropDb() {
    if (db == null || db.getFile() == null) return;

    Alert alert = new Alert(AlertType.CONFIRMATION);
    alert.setTitle("DROP DATABASE");
    alert.setHeaderText("Do you really want to drop the database?");

    Optional<ButtonType> result = alert.showAndWait();
    if (result.get() == ButtonType.OK) {
      db.drop();
      clearTables();
    }

  }

  /**
   * Open database event handler [Database -> open or Ctrl+O]
   * Opens database file from given path and loads tables to database structure
   * tree view
   */
  @FXML public void openDb() {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Open Database File");
    File file = fileChooser.showOpenDialog(window);

    if (file != null) {
      db = new Database(file);
      loadTables(db.getFile());
    }
  }

  /**
   * Copy database event handler [Database -> copy]
   * Copies opened database to new database file
   */
  @FXML public void copyDb() {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Open Database File");
    File file = fileChooser.showSaveDialog(window);

    if (file == null) return;

    try {
      Files.copy(db.getFile().toPath(), file.toPath());
    } catch (IOException e) {
      showErrorDialog("FILE IO ERROR:", e.getMessage());
    } catch (Exception e) {
      showErrorDialog("Unexpected ERROR:", e.getMessage());
    }
  }

  /**
   * Save Query event handler [File -> Save Query or Ctrl+S]
   * Saves query from SQL editor to given file
   */
  @FXML public void saveQuery() {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Save Query");
    File file = fileChooser.showSaveDialog(window);

    if (file == null) return;

    try {
      Files.write(file.toPath(), editor.getCode().getBytes());
    } catch (IOException e) {
      showErrorDialog("FILE IO ERROR:", e.getMessage());
    } catch (Exception e) {
      showErrorDialog("Unexpected ERROR:", e.getMessage());
    }

  }

  /**
   * Load Query event handler [File -> Load Query or Ctrl+Shift+O]
   * Loads query from given file to SQL editor
   */
  @FXML public void loadQuery() {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Open Query File");
    File file = fileChooser.showOpenDialog(window);

    if (file == null) return;

    String query = "";

    try {
      query = Files.readAllLines(file.toPath()).stream().collect(Collectors.joining("\n"));
    } catch (IOException e) {
      showErrorDialog("FILE IO ERROR:", e.getMessage());
    } catch (Exception e) {
      showErrorDialog("Unexpected ERROR:", e.getMessage());
    }

    editor.pasteCode(query);
  }

  /**
   * Export As CSV event handler [File -> Export As CSV or Ctrl+E]
   * Export data from table view to csv file by given path
   */
  @FXML public void exportCSV() {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Export to CSV");
    File file = fileChooser.showSaveDialog(window);

    if (file == null)
      return;

    try {
      Files.write(file.toPath(),
        CSVParser.parseToCSV(lastResult.getColumnNames(),
        lastResult.getTableData()).getBytes());
    } catch (IOException e) {
      showErrorDialog("FILE IO ERROR:", e.getMessage());
    } catch (Exception e) {
      showErrorDialog("Unexpected ERROR:", e.getMessage());
    }

  }

  /**
   * Exit event handler [File -> Exit or Ctrl+X]
   * Exits from the application
   */
  @FXML public void exit() {
    Platform.exit();
  }

  /**
   * Execute button event handler [F9]
   * Sends query from SQL editor to SQLLite database and returns database response
   * if it's data then renders it to table view
   * if it's error message then shows error dialog
   * else updates just executes query and updates status bar
   */
  @FXML public void execute() {
    if (db == null || db.getFile() == null)
      return;

    String query = editor.getCode();
    try {
      addToCommandsHistory(query);
      lastResult = db.executeQuery(query);
    } catch (SQLException e) {
      showErrorDialog("SQL ERROR", e.getMessage());
      statusBar.setText(PrettyStatus.error(query));
      return;
    } catch (Exception e) {
      showErrorDialog("Unexpected ERROR:", e.getMessage());
      return;
    }

    if (lastResult != null) {
      loadTables(db.getFile());
      if (lastResult.getTableData() != null) {
        loadTableView(lastResult);
      }
      if (lastResult.getExecutionInfo() != null) {
        statusBar.setText(lastResult.getExecutionInfo());
        showQueryExecutionNotification();
      }
    }
  }

  /**
   * User Guide event handler [Help -> User Guide or Ctrl+Shift+H]
   * Opens User Guide stage
   */
  @FXML public void showUserGuide() {
    Stage userGuide = new Stage();
    Accordion accordion = null;
    try {
      accordion = FXMLLoader.load(App.class.getResource(USER_GUIDE));
    } catch (IOException e) {
      e.printStackTrace();
    }
    userGuide.setScene(new Scene(accordion));
    userGuide.setTitle("User Guide");
    userGuide.show();
  }

  /**
   * About event handler [Help -> About]
   * Opens Help stage
   */
  @FXML public void showAbout() {
    Stage about = new Stage();
    AnchorPane aboutPane = null;
    try {
      aboutPane = FXMLLoader.load(App.class.getResource(ABOUT));
    } catch (IOException e) {
      e.printStackTrace();
    }
    about.setScene(new Scene(aboutPane));
    about.setTitle("About");
    about.setResizable(false);
    about.show();
  }

  // Shows up error dialog with the following message
  private void showErrorDialog(String title, String content) {
    Alert alert = new Alert(AlertType.ERROR);
    alert.setTitle(title);
    alert.setHeaderText(content);
    alert.getDialogPane().setMaxSize(320, 640);
    alert.showAndWait();
  }

  // Loads tables to the database structure tree view
  private void loadTables(File database) {
    Image rootImg = new Image(DB_ICON, 16, 16, false, false);
    TreeItem<String> rootItem = new TreeItem<>(database.getName(), new ImageView(rootImg));
    rootItem.setExpanded(true);
    Image tableImg = new Image(TABLE_ICON, 16, 16, false, false);
    List<String> tables = new Database(database).getAllTables();

    List<TreeItem<String>> tablesList = tables
        .stream()
        .map(table -> new TreeItem<>(table, new ImageView(tableImg)))
        .collect(Collectors.toList());

    rootItem.getChildren().addAll(tablesList);
    dbStructure.setRoot(rootItem);
  }

  // Deletes all content from database structure tree view
  private void clearTables() {
    dbStructure.setRoot(null);
  }

  // Loads database data to table view
  private void loadTableView(QueryResult queryResult) {
    table.getColumns().clear();
    table.getItems().clear();

    if (queryResult.getTableData().size() <= 0) return;

    ObservableList<ObservableList<String>> data = FXCollections.observableArrayList();
    for (int column = 0; column < queryResult.getColumnCount(); column++) {
      final int j = column;
      TableColumn<ObservableList<String>, String> col = new TableColumn<>(queryResult.getColumnNames().get(j));
      col.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().get(j)));
      table.getColumns().addAll(col);
    }

    for (int row = 0; row < queryResult.getRowCount(); row++) {
      ObservableList<String> rowData = FXCollections.observableArrayList();
      for (int column = 0; column < queryResult.getColumnCount(); column++) {
        rowData.add(queryResult.getTableData().get(row).get(column));
      }
      data.add(rowData);
    }

    table.setItems(data);
  }

  // shows notification of query execution
  private void showQueryExecutionNotification() {
    final Tooltip tooltip = new Tooltip();
    final String notification = statusBar.getText();
    if(notification != null && !notification.isEmpty()) {
      tooltip.setAutoHide(true);
      tooltip.setText(notification);
      double xPosition = window.getX() + 5;
      double yPosition = window.getY() + window.getHeight() - 35;
      tooltip.show(window, xPosition, yPosition);
    }
  }


  // adds query to commands combo-box
  private void addToCommandsHistory(String command) {
    final ObservableList<String> listOfCommands = commands.getItems();
    if(!listOfCommands.contains(command.replaceAll("\\s*", ""))) {
      listOfCommands.add(0, command);
    }
  }

}
