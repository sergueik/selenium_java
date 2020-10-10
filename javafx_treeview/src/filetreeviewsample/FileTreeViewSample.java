package filetreeviewsample;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyCode;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

public class FileTreeViewSample extends Application {

    private ExecutorService service;
    private Task watchTask;
    private SearchTask searchTask;
    private TextField rootDirText;
    private Path rootPath;
    private Button dispBtn;
    private Text messageText;
    private StringProperty messageProp= new SimpleStringProperty();
    private TreeView<PathItem> fileTreeView;
    private CheckBox watchChkbox;
    private TextArea watchText;
    private TextField patternText;
    private Button searchBtn;
    private ListView<String> searchListView;
    private ObservableList<String> searchListItem;
    private StringProperty searchProp = new SimpleStringProperty();
    private List<String> searchList = new ArrayList<>();
    private Label searchCountLabel;

    public FileTreeViewSample() {
        fileTreeView = new TreeView<>();
        fileTreeView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        service = Executors.newFixedThreadPool(3);
    }

    @Override
    public void start(final Stage stage) {
        VBox root = new VBox();
        root.setPadding(new Insets(10));
        root.setSpacing(10);
        // root Directory
        HBox rootHbox = getRootHbox(stage);
        messageText = new Text();        
        messageText.yProperty().set(30);
        messageText.setFill(Color.RED);
        messageText.textProperty().bind(messageProp);
        TitledPane titledPane = new TitledPane("File Tree View", fileTreeView);
        titledPane.setPrefHeight(300);
        // Watch Directory
        watchChkbox = new CheckBox("Watch Directory");
        watchChkbox.setDisable(true);
        watchText = new TextArea(); // watch result
        watchText.setPrefHeight(50);
        // search
        HBox searchHbox = getSearchHbox();
        searchListView = new ListView<>(); // search result
        searchListView.setPrefHeight(100);
        searchListItem = FXCollections.observableArrayList();
        searchListView.setItems(searchListItem);
        setEventHandler(stage);
        root.getChildren().addAll(rootHbox, titledPane, messageText, watchChkbox, watchText, searchHbox, searchListView);
        Scene scene = new Scene(root, 800, 600);
        stage.setTitle("File Tree View Sample");
        stage.setScene(scene);
        stage.show();
        Platform.runLater(() -> rootDirText.requestFocus());
    }

    private HBox getRootHbox(final Stage stage) {
        HBox hbox = new HBox();
        hbox.setSpacing(10);
        Label label = new Label("root Directory:");
        rootDirText = new TextField();
        rootDirText.setPrefWidth(300);
        final DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle("select Directory");
        Image image = new Image(getClass().getResourceAsStream("OpenDirectory.png"));
        Button chooserBtn = new Button("", new ImageView(image));
        chooser.setTitle("Select Root Directory");
        chooserBtn.setOnAction(event -> {
            File selDir = chooser.showDialog(stage);
            if (selDir != null) {
                rootDirText.setText(selDir.getAbsolutePath());
            }
        });
        dispBtn = new Button("Display File Tree");
        hbox.getChildren().addAll(label, rootDirText, chooserBtn, dispBtn);
        return hbox;
    }

    private HBox getSearchHbox() {
        HBox hBox = new HBox();
        hBox.setSpacing(10);
        Label searchLabel = new Label("search:");
        patternText = new TextField();
        Image image = new Image(getClass().getResourceAsStream("Search.png"));
        searchBtn = new Button("", new ImageView(image));
        searchBtn.setDisable(true);
        searchCountLabel = new Label();
        searchCountLabel.setPrefWidth(200);
        searchCountLabel.setAlignment(Pos.CENTER_LEFT);
        hBox.getChildren().addAll(searchLabel, patternText, searchBtn, searchCountLabel);
        return hBox;
    }

    private void setEventHandler(final Stage stage) {
        dispBtn.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                dispBtn.fire();
            }
        });
        // Display File Tree Button
        dispBtn.setOnAction(event -> {
            messageProp.setValue(null);
            watchChkbox.setDisable(false);
            watchChkbox.setSelected(false);
            watchText.clear();
            searchBtn.setDisable(false);
            searchListItem.clear();
            searchProp.unbind();
            searchCountLabel.textProperty().unbind();
            searchCountLabel.setText(null);
            if (watchTask != null && watchTask.isRunning()) {
                watchTask.cancel();
            }
            if (searchTask != null && searchTask.isRunning()) {
                searchTask.cancel();
            }
            rootPath = Paths.get(rootDirText.getText());
            PathItem pathItem = new PathItem(rootPath);
            fileTreeView.setRoot(createNode(pathItem));
            fileTreeView.setEditable(true);
            fileTreeView.setCellFactory((TreeView<PathItem> p) -> {
                final PathTreeCell cell = new PathTreeCell(stage, messageProp);
                setDragDropEvent(stage, cell);
                return cell;
            });
        });
        // Watch Directory CheckBox
        watchChkbox.selectedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean oldVal, Boolean newVal) -> {
            if (newVal) {
                watchTask = new WatchTask(rootPath);
                service.submit(watchTask);
                watchText.textProperty().bind(watchTask.messageProperty());
            } else {
                if (watchTask != null && watchTask.isRunning()) {
                    watchTask.cancel();
                    watchText.textProperty().unbind();
                }
            }
        });
        // search button
        searchBtn.setOnAction(event -> {
            searchListItem.clear();
            searchListView.getItems().clear();
            searchTask = new SearchTask(rootPath, patternText.getText());
            searchCountLabel.textProperty().bind(searchTask.messageProperty());
            searchList.clear();
            searchProp.bind(searchTask.getResultString());
            searchTask.setOnSucceeded((WorkerStateEvent stateEvent) -> {
                searchListItem.addAll(searchList);
            });
            service.submit(searchTask);
        });
        // *** binding to search task result one by one ***
        searchProp.addListener((ObservableValue<? extends String> ov, String oldVal, String newVal) -> {
            if (newVal != null) {
                searchList.add(newVal);
            }
        });
    }

    private void setDragDropEvent(Stage stage, final PathTreeCell cell) {
        // The drag starts on a gesture source
        cell.setOnDragDetected(event -> {
            TreeItem<PathItem> item = cell.getTreeItem();
            if (item != null && item.isLeaf()) {
                Dragboard db = cell.startDragAndDrop(TransferMode.COPY);
                ClipboardContent content = new ClipboardContent();
                List<File> files = Arrays.asList(cell.getTreeItem().getValue().getPath().toFile());
                content.putFiles(files);
                db.setContent(content);
                event.consume();
            }
        });
        // on a Target
        cell.setOnDragOver(event -> {
            TreeItem<PathItem> item = cell.getTreeItem();
            if ((item != null && !item.isLeaf()) &&
                    event.getGestureSource() != cell &&
                    event.getDragboard().hasFiles()) {
                Path targetPath = cell.getTreeItem().getValue().getPath();
                PathTreeCell sourceCell = (PathTreeCell) event.getGestureSource();
                final Path sourceParentPath = sourceCell.getTreeItem().getValue().getPath().getParent();
                if (sourceParentPath.compareTo(targetPath) != 0) {
                    event.acceptTransferModes(TransferMode.COPY);
                }
            }
            event.consume();
        });
        // on a Target
        cell.setOnDragEntered(event -> {
            TreeItem<PathItem> item = cell.getTreeItem();
            if ((item != null && !item.isLeaf()) &&
                    event.getGestureSource() != cell &&
                    event.getDragboard().hasFiles()) {
                Path targetPath = cell.getTreeItem().getValue().getPath();
                PathTreeCell sourceCell = (PathTreeCell) event.getGestureSource();
                final Path sourceParentPath = sourceCell.getTreeItem().getValue().getPath().getParent();
                if (sourceParentPath.compareTo(targetPath) != 0) {
                    cell.setStyle("-fx-background-color: powderblue;");
                }                
            }
            event.consume();
        });
        // on a Target
        cell.setOnDragExited(event -> {
            cell.setStyle("-fx-background-color: white");
            event.consume();
        });
        // on a Target
        cell.setOnDragDropped(event -> {
            Dragboard db = event.getDragboard();
            boolean success = false;
            if (db.hasFiles()) {
                final Path source = db.getFiles().get(0).toPath();
                final Path target = Paths.get(
                        cell.getTreeItem().getValue().getPath().toAbsolutePath().toString(),
                        source.getFileName().toString());
                if (Files.exists(target, LinkOption.NOFOLLOW_LINKS)) {
                    Platform.runLater(() -> {
                        BooleanProperty replaceProp = new SimpleBooleanProperty();
                        CopyModalDialog dialog = new CopyModalDialog(stage, replaceProp);
                        replaceProp.addListener((ObservableValue<? extends Boolean> ov, Boolean oldValue, Boolean newValue) -> {
                            if (newValue) {
                                FileCopyTask task = new FileCopyTask(source, target);
                                service.submit(task);
                            }
                        });
                    });
                } else {
                    FileCopyTask task = new FileCopyTask(source, target);
                    service.submit(task);
                    task.setOnSucceeded(value -> {
                        Platform.runLater(() -> {
                            TreeItem<PathItem> item = PathTreeItem.createNode(new PathItem(target));
                            cell.getTreeItem().getChildren().add(item);
                        });
                    });
                }
                success = true;
            }
            event.setDropCompleted(success);
            event.consume();
        });
        // on a Source
        cell.setOnDragDone(event -> {
            ;
        });
    }

    private TreeItem<PathItem> createNode(PathItem pathItem) {
        return PathTreeItem.createNode(pathItem);
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void stop() throws Exception {
        service.shutdownNow();
    }
}
