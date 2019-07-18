package example;

import java.util.concurrent.ArrayBlockingQueue;

public class SimpleBGProducer implements Runnable {
	private final ArrayBlockingQueue<String> outboundMsgQueue;
	private final Thread simpleBGProducerThread;
	private final int FIVE_SECONDS = 5000;

	public SimpleBGProducer(ArrayBlockingQueue<String> argOutboundMsgQueue) {
		outboundMsgQueue = argOutboundMsgQueue;
		// Thread can block of Q to FX is full
		simpleBGProducerThread = new Thread(this);
		simpleBGProducerThread.start();
	}

	public void run() {
		String textToSend;
		int msgSendNum = 0;

		while (true) {
			try {
				// Generate new message every 5 seconds
				Thread.sleep(FIVE_SECONDS);
				textToSend = String.format("SM:Number %d", ++msgSendNum);
				// outboundMsgQueue.add(textToSend);
				outboundMsgQueue.put(textToSend);
				System.out.println("Outbound : " + outboundMsgQueue.peek());
			} catch (InterruptedException e) {
				break;
			}
		}
	}
}
