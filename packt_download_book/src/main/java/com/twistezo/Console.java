package com.twistezo;

import javafx.application.Platform;
import javafx.scene.control.TextArea;
import java.io.IOException;
import java.io.OutputStream;

public class Console extends OutputStream {
	private TextArea output;

	public Console(TextArea ta) {
		this.output = ta;
	}

	@Override
	public void write(final int i) throws IOException {
		Platform.runLater(new Runnable() {
			public void run() {
				output.appendText(String.valueOf((char) i));
			}
		});
	}
}
