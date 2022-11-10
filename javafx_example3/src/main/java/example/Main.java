package example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        Rectangle2D rectangleScreen = Screen.getPrimary().getVisualBounds();
        System.out.println(this.getClass().getResource("/view.fxml"));
        Parent root = FXMLLoader.load(this.getClass().getResource("/splash.fxml"));
        primaryStage.setTitle("TeachingEnglishApp");
        Scene scene = new Scene(root, rectangleScreen.getWidth()/2,rectangleScreen.getHeight()/2);
        scene.getStylesheets().add("/application.css");
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
