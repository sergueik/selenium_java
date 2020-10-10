package filetreeviewsample;

import javafx.beans.property.ObjectProperty;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ModalDialog {

    public ModalDialog(Stage owner, final TreeItem<PathItem> treeItem, final ObjectProperty<TreeItem<PathItem>> prop) {
        final Stage dialog = new Stage(StageStyle.UTILITY);
        dialog.initOwner(owner);
        dialog.initModality(Modality.APPLICATION_MODAL);
        GridPane root = new GridPane();
        root.setPadding(new Insets(30));
        root.setHgap(5);
        root.setVgap(10);
        Label label = new Label("Are you sure?");
        Button okButton = new Button("OK");
        okButton.setOnAction(event -> {
            prop.set(treeItem);
            dialog.hide();
        });
        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction(event -> {
            dialog.hide();
        });
        root.add(label, 0, 0, 2, 1);
        root.addRow(1, okButton, cancelButton);
        dialog.setScene(new Scene(root));
        dialog.show();
    }
}
