package example;

import java.util.concurrent.ArrayBlockingQueue;

public class SimpleBG {
	@SuppressWarnings("unused")
	private final SimpleBGProducer producer;
	@SuppressWarnings("unused")
	private final SimpleBGConsumer consumer;
	@SuppressWarnings("unused")
	private final SimpleBGSender sender;
	private final ArrayBlockingQueue<String> outboundMsgQueue;
	private final ArrayBlockingQueue<String> inboundMsgQueue;

	private final int QUEUE_DEPTH = 6;

	public SimpleBG(SimpleFXBGController argController) {
		inboundMsgQueue = new ArrayBlockingQueue<>(QUEUE_DEPTH, true);
		outboundMsgQueue = new ArrayBlockingQueue<>(QUEUE_DEPTH, true);
		consumer = new SimpleBGConsumer(inboundMsgQueue);
		sender = new SimpleBGSender(argController, outboundMsgQueue);
		producer = new SimpleBGProducer(outboundMsgQueue);
	}

	public int receiveMsg(String argInboundMsg) {
		synchronized (inboundMsgQueue) {
			int queueOccupancy;

			if ((queueOccupancy = inboundMsgQueue.size()) == QUEUE_DEPTH) {
				return -1;
			}

			inboundMsgQueue.add(argInboundMsg);
			return ++queueOccupancy;
		}
	}
}
