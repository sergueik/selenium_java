package simplefxbg;

import java.util.concurrent.ArrayBlockingQueue;

public class SimpleBGConsumer implements Runnable {
	private final Thread simpleBGConsumerThread;
	private final ArrayBlockingQueue<String> inboundMsgQueue;

	public SimpleBGConsumer(ArrayBlockingQueue<String> argInboundMsgQueue) {
		inboundMsgQueue = argInboundMsgQueue;
		// Thread can block waiting for data
		simpleBGConsumerThread = new Thread(this);
		simpleBGConsumerThread.start();
	}

	@Override
	public void run() {
		String inboundMsg;
		String text;

		while (true) {
			try {
				inboundMsg = inboundMsgQueue.take();
				text = String.format("inbound message: '%s'", inboundMsg);
			}
			catch (InterruptedException e) {
				break;
			}
		}
	}
}
