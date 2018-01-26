package example;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import org.controlsfx.ControlsFXSample;
import org.controlsfx.control.BreadCrumbBar;
import org.controlsfx.control.BreadCrumbBar.BreadCrumbActionEvent;
import org.controlsfx.samples.Utils;

public class HelloBreadCrumbBar extends Application {

    private BreadCrumbBar<String> sampleBreadCrumbBar;
    @SuppressWarnings("restriction")
		private final Label selectedCrumbLbl = new Label();
    
    private int newCrumbCount = 0;

    public String getSampleName() {
        return "BreadCrumbBar";
    }

    public String getJavaDocURL() {
        return Utils.JAVADOC_BASE + "org/controlsfx/control/BreadCrumbBar.html";
    }

    public String getSampleDescription() {
        return "The BreadCrumbBar provides an easy way to navigate hirarchical structures " +
                "such as file systems.";
    }

    @SuppressWarnings("restriction")
		public Node getPanel(final Stage stage) {
        VBox root = new VBox(10);
        root.setPadding(new Insets(10));

        sampleBreadCrumbBar = new BreadCrumbBar<>();
        resetModel();

        root.getChildren().add(sampleBreadCrumbBar);
        BorderPane.setMargin(sampleBreadCrumbBar, new Insets(20));

        sampleBreadCrumbBar.setOnCrumbAction(new EventHandler<BreadCrumbBar.BreadCrumbActionEvent<String>>() {
            @Override public void handle(BreadCrumbActionEvent<String> bae) {
                selectedCrumbLbl.setText("You just clicked on '" + bae.getSelectedCrumb() + "'!");
            }
        });
        
        root.getChildren().add(selectedCrumbLbl);

        return root;
    }

    @SuppressWarnings("restriction")
		public Node getControlPanel() {
        GridPane grid = new GridPane();
        grid.setVgap(10);
        grid.setHgap(10);
        grid.setPadding(new Insets(30, 30, 0, 30));

        int row = 0;

        // add crumb
        Label lblAddCrumb = new Label("Add crumb: ");
        lblAddCrumb.getStyleClass().add("property");
        grid.add(lblAddCrumb, 0, row);
        Button btnAddCrumb = new Button("Press");
        btnAddCrumb.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent ae) {
                // Construct a new leaf node and append it to the previous leaf
                TreeItem<String> leaf = new TreeItem<>("New Crumb #" + newCrumbCount++);
                sampleBreadCrumbBar.getSelectedCrumb().getChildren().add(leaf);
                sampleBreadCrumbBar.setSelectedCrumb(leaf);
            }
        });
        grid.add(btnAddCrumb, 1, row++);
        
        // reset
        Label lblReset = new Label("Reset model: ");
        lblReset.getStyleClass().add("property");
        grid.add(lblReset, 0, row);
        Button btnReset = new Button("Press");
        btnReset.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent ae) {
                resetModel();
            }
        });
        grid.add(btnReset, 1, row++);
        
        // auto navigation
        Label lblAutoNavigation = new Label("Enable auto navigation: ");
        lblAutoNavigation.getStyleClass().add("property");
        grid.add(lblAutoNavigation, 0, row);
        CheckBox chkAutoNav = new CheckBox();
        chkAutoNav.selectedProperty().bindBidirectional(sampleBreadCrumbBar.autoNavigationEnabledProperty());
        grid.add(chkAutoNav, 1, row++);

        return grid;
    }
    
    private void resetModel() {
        @SuppressWarnings("restriction")
				TreeItem<String> model = BreadCrumbBar.buildTreeModel("Hello", "World", "This", "is", "cool");
        sampleBreadCrumbBar.setSelectedCrumb(model);
    }


    public static void main(String[] args) {
    	Application.launch(args);
    }

		@Override
		public void start(Stage primaryStage) throws Exception {
			// TODO Auto-generated method stub
			
		}

}