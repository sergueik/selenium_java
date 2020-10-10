package filetreeviewsample;

import javafx.beans.property.BooleanProperty;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Dialog for confirm copying file
 * 
 * @author tomo
 */
public class CopyModalDialog {    
    public CopyModalDialog(Stage owner, final BooleanProperty replaceProp) {
        final Stage dialog = new Stage(StageStyle.UTILITY);
        dialog.initOwner(owner);
        dialog.initModality(Modality.APPLICATION_MODAL);
        GridPane root = new GridPane();
        root.setPadding(new Insets(30));
        root.setHgap(5);
        root.setVgap(10);
        Label label = new Label("The item already exists in this location. Do you want to replace it?");
        Button okButton = new Button("OK");
        okButton.setOnAction(event -> {
            replaceProp.set(true);
            dialog.hide();
        });
        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction(event -> {
            replaceProp.set(false);
            dialog.hide();
        });
        root.add(label, 0, 0, 2, 1);
        root.addRow(1, okButton, cancelButton);
        dialog.setScene(new Scene(root));
        dialog.show();
    }
}
