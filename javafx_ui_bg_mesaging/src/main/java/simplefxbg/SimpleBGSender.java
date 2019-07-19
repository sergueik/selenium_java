package simplefxbg;

import java.util.concurrent.ArrayBlockingQueue;
import javafx.application.Platform;

@SuppressWarnings("restriction")
public class SimpleBGSender implements Runnable {

	private final SimpleFXBGController controller;
	private final Thread simpleBGSenderThread;
	private final ArrayBlockingQueue<String> outBoundMsgQueue;

	public SimpleBGSender(SimpleFXBGController argController,
			ArrayBlockingQueue<String> argOutboundMsgQueue) {
		outBoundMsgQueue = argOutboundMsgQueue;
		controller = argController;
		// Thread may block when there is no data available for sending
		simpleBGSenderThread = new Thread(this);
		simpleBGSenderThread.start();
	}

	public void run() {
		String textToSend;

		while (true) {
			try {
				// Wait for message to appear in the message queue
				int size = outBoundMsgQueue.size();
				if (size > 0) {
					textToSend = outBoundMsgQueue.take();
					System.err.println("Taken message from the queue: " + textToSend);
					sendMsgToFX(textToSend);
				}
			} catch (InterruptedException e) {
				break;
			}
		}
	}

	@SuppressWarnings({ "restriction" })
	private void sendMsgToFX(final String message) {

		// Append message text in the list box on the UI
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				controller.onMessage(message);
			}
		});
	}
	/*
	  @Override
	  public void run() {
	javafx.application.Platform.runLater(() -> {
		controller.onMessage(message);
	});
	}
	*/
}
