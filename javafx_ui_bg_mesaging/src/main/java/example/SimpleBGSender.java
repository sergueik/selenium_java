package example;

import java.util.concurrent.ArrayBlockingQueue;

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
				// Wait for message to be sent to FX
				textToSend = outBoundMsgQueue.take();
				sendMsgToFX(textToSend);
			} catch (InterruptedException e) {
				break;
			}
		}
	}

	@SuppressWarnings({ "restriction" })
	private void sendMsgToFX(final String argMsgToFX) {
		javafx.application.Platform.runLater(() -> {
			controller.onMessage(argMsgToFX);
		});
	}
}
