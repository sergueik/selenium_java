package simplefxbg;

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
				boolean result = outboundMsgQueue.add(textToSend);
				// outboundMsgQueue is being monitored by the SimpleBGSender
				// the size processing added to SimpleBGSender will give a slice of time to see
				// the queue being non-empty
				if (result) {
					System.err.println("Outbound : size=" + outboundMsgQueue.size()
							+ " peek: " + outboundMsgQueue.peek());
				} else {
					System.out.println("Failed to add to the queue");

				}
			} catch (InterruptedException e) {
				break;
			} catch (Exception e) {
				System.err.println("Exception (ignored): " + e.toString());
				break;
			}
		}
	}
}
