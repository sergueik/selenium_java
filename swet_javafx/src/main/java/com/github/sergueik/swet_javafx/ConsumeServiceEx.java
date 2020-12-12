package com.github.sergueik.swet_javafx;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;

import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;

import javafx.scene.Node;
import javafx.scene.Scene;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.google.common.reflect.TypeToken;
import java.lang.reflect.Type;
import com.google.gson.Gson;

// https://gist.github.com/TheItachiUchiha/f5866c121571eb1fcf36
// https://stackoverflow.com/questions/30233068/javafx-consuming-rest-service-and-displaying-the-data-in-front-end
// https://www.oracle.com/webfolder/technetwork/tutorials/obe/java/javafx_json_tutorial/javafx_javaee7_json_tutorial.html
// https://info.michael-simons.eu/2014/10/22/getting-started-with-javafx-8-developing-a-rest-client-application-from-scratch/
// https://curbsidebanter.wordpress.com/2016/07/10/restful-client-with-javafx-ui/	
@SuppressWarnings("restriction")
public class ConsumeServiceEx extends Application {

	private final boolean debug = true;
	private ObservableList<PeopleOnSO> listOfPeople;
	private static final String JSON_URL = "http://echo.jsontest.com/name/john/like/chess/imageUrl/a"; // "https://api.myjson.com/bins/3jwmh";
	private static final String IMAGE_URL = "http://www.fontspring.com/presentation_20150512/images/ajax_loader_blue_512.gif";
	private final ExecutorService executorService = Executors.newCachedThreadPool();
	private Image loadImage;

	// class variable, eager
	@SuppressWarnings({ "unchecked", "serial" })
	private Task<List<PeopleOnSO>> fetchList = new Task() {
		@Override
		protected List<PeopleOnSO> call() throws Exception {
			List<PeopleOnSO> typeData = null;
			try {
				Gson gson = new Gson();
				// need mockup
				String dataMockup = "[ { \"like\": \"hockey\", \"imageUrl\": \"file:///c:/temp/background.jpg\", \"name\": \"john\" },"
						+ " { \"like\": \"chess\", \"imageUrl\": \"x\", \"name\": \"mike\" }, { \"like\": \"reading\", \"imageUrl\": \"x\", \"name\": \"nick\" }, { \"like\": \"hiking\", \"imageUrl\": \"x\", \"name\": \"pete\" }  ]";
				if (debug)
					System.err.println("Loading data: " + dataMockup);
				Type dataType = new TypeToken<List<PeopleOnSO>>() {
				}.getType();
				String rawData = readUrl(JSON_URL);
				rawData = dataMockup;
				typeData = new Gson().fromJson(rawData, dataType);
				// imitate the effort loading data
				sleep(100);
				if (debug)
					System.err.println(String.format("Loaded typed data %d entries.", typeData.size()));
			} catch (Exception e) {
				e.printStackTrace();
			}
			return typeData;
		}
	};

	private class FetchImage<V> extends Task<Image> {

		private String url;

		public FetchImage(String data) {
			url = data;
		}

		@Override
		protected Image call() throws Exception {
			Image image = new Image(url);
			return image;
		}

	}

	@Override
	public void start(Stage stage) throws Exception {

		// ignore the IMAGE_URL
		loadImage = new Image(IMAGE_URL);
		loadImage = new Image(getClass().getClassLoader().getResourceAsStream("loading.jpg"));

		VBox root = new VBox();
		root.setAlignment(Pos.TOP_CENTER);
		root.setPadding(new Insets(20));
		root.setSpacing(20);
		// how to use http://echo.jsontest.com/key/value/one/two
		Button button = new Button("Fill GridPane");

		root.getChildren().addAll(button);

		button.setOnAction(e -> {
			ImageView loading = new ImageView(loadImage);
			loading.setFitWidth(60);
			loading.setFitHeight(60);
			root.getChildren().add(loading);
			executorService.submit(fetchList);
		});

		// https://www.codota.com/code/java/methods/javafx.concurrent.Task/setOnSucceeded
		fetchList.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
			@Override
			public void handle(WorkerStateEvent t) {

				if (debug)
					System.err.println("Begin the result processing.");

				List<PeopleOnSO> value = fetchList.getValue();
				if (value != null) {
					listOfPeople = FXCollections.observableArrayList(value);

					if (debug)
						System.err.println("Loading " + listOfPeople.size() + " entries");

					GridPane gridPane = createGridPane(listOfPeople);
					// Remove "Loading..." Image and add GridPane instead
					root.getChildren().remove(1);
					VBox.setVgrow(gridPane, Priority.ALWAYS);
					root.getChildren().add(gridPane);
					stage.sizeToScene();
				}

			}
		});

		ScrollPane scrollPane = new ScrollPane(root);
		Scene scene = new Scene(scrollPane, 600, 500);
		stage.setScene(scene);
		stage.setTitle("Load Data Callbacks");
		stage.show();

		stage.setOnCloseRequest(e -> {
			executorService.shutdown();
		});
	}

	@SuppressWarnings("unchecked")
	public GridPane createGridPane(ObservableList<PeopleOnSO> listOfPeople) {
		GridPane gridPane = new GridPane();
		gridPane.setAlignment(Pos.CENTER);
		gridPane.setGridLinesVisible(true);
		gridPane.setPadding(new Insets(20));
		gridPane.setMinHeight(500);
		gridPane.setMaxWidth(500);

		Label nameHeading = new Label("Name");
		nameHeading.setStyle("-fx-font-weight: bold");
		Label likeHeading = new Label("Likes");
		likeHeading.setStyle("-fx-font-weight: bold");
		Label imageHeading = new Label("Image");
		imageHeading.setStyle("-fx-font-weight: bold");

		gridPane.add(nameHeading, 0, 0);
		gridPane.add(likeHeading, 1, 0);
		gridPane.add(imageHeading, 2, 0);

		alignElements(nameHeading, likeHeading, imageHeading);

		for (int i = 0; i < 3; i++) {
			ColumnConstraints column = new ColumnConstraints(150);
			// column.setPercentWidth(80);
			gridPane.getColumnConstraints().add(column);
		}

		for (int i = 0; i < listOfPeople.size(); i++) {

			PeopleOnSO people = listOfPeople.get(i);
			try {
				System.err.println(String.format("Entry %d: %s", i, people.toString()));
				Label nameLabel = new Label(people.getName());
				Label likeLabel = new Label(people.getLike());
				ImageView imageView = new ImageView(loadImage);
				imageView.setFitHeight(60);
				imageView.setFitWidth(60);
				// Thread for loading images later
				FetchImage fetchImage = new FetchImage<Image>(people.getImageUrl());
				fetchImage.setOnSucceeded(worker -> {
					imageView.setImage((Image) fetchImage.getValue());
				});
				executorService.submit(fetchImage);

				// Adding to GridPane and necessary configuration
				gridPane.add(nameLabel, 0, i + 1);
				gridPane.add(likeLabel, 1, i + 1);
				gridPane.add(imageView, 2, i + 1);

				// Aligning at center
				alignElements(nameLabel, likeLabel, imageView);

				gridPane.getRowConstraints().add(new RowConstraints(80));
			} catch (NullPointerException e) {
				// ignore bad data
			}
		}
		return gridPane;
	}

	private void alignElements(Node... nodes) {
		for (Node node : nodes) {
			GridPane.setHalignment(node, HPos.CENTER);
			GridPane.setValignment(node, VPos.CENTER);
		}
	}

	private static String readUrl(String data) throws Exception {
		BufferedReader reader = null;
		try {
			URL url = new URL(data);
			reader = new BufferedReader(new InputStreamReader(url.openStream()));
			StringBuffer buffer = new StringBuffer();
			int read;
			char[] chars = new char[1024];
			while ((read = reader.read(chars)) != -1)
				buffer.append(chars, 0, read);

			return buffer.toString();
		} finally {
			if (reader != null)
				reader.close();
		}
	}

	private class PeopleOnSO {

		private final String name;
		private final String like;
		private final String imageUrl;

		public PeopleOnSO(String name, String like, String imageUrl) {
			this.name = new String(name);
			this.like = new String(like);
			this.imageUrl = new String(imageUrl);
		}

		public String toString() {
			return String.format("name: %s, like: %s", name, like);
		}

		public String getName() {
			return name;
		}

		public String getLike() {
			return like;
		}

		public String getImageUrl() {
			return imageUrl;
		}
	}

	private void sleep(long delay) {

		try {
			// imitate the effort loading data
			Thread.sleep(delay);
		} catch (InterruptedException e) {
		}

	}

	public static void main(String[] args) {
		launch(args);
	}
}
