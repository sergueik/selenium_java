package example.applications.notaskbaricon;

/*
 * Copyright (c) 2021 Michael Sims, Dustin Redmond and contributors
 */
import javafx.application.Application;

public class Start {
	public static void main(String[] args) {
		System.setProperty("apple.awt.UIElement", "true");
		java.awt.Toolkit.getDefaultToolkit();

		Application.launch(Main.class, args);
	}

}