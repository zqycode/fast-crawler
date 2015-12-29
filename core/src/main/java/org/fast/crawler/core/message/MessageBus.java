package org.fast.crawler.core.message;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class MessageBus {

	public static final String SUB_CRAWL_SITE_MSG = "SUB_CRAWL_SITE_MSG";

	private static Queue<Message> messageQueue = new ConcurrentLinkedQueue<Message>();
	private static Map<String, List<Listener>> listenerQueues = new HashMap<String, List<Listener>>();

	private static MessageBus messageBus = null;

	private static int MAX_THREAD = 2;

	private MessageBus() {
		for (int i = 0; i < MAX_THREAD; i++) {
			new Thread(new MessageConsumer()).start();
		}
	}

	public static MessageBus getInstance() {
		if (messageBus == null) {
			messageBus = new MessageBus();
		}
		return messageBus;
	}

	public void addMessage(String type, Object data) {
		Message message = new Message(type, data);
		messageQueue.add(message);
	}

	public void clearMessage() {
		messageQueue.clear();
	}

	public synchronized Message poll() {
		return messageQueue.poll();
	}

	public List<Listener> getListeners(String type) {
		return listenerQueues.get(type);
	}

	public void registListener(String type, Listener listener) {
		List<Listener> listeners = this.getListeners(type);
		if (listeners == null) {
			listeners = new ArrayList<Listener>();
		}
		for (Listener existListener : listeners) {
			if (existListener.getClass().getName().equals(listener.getClass().getName())) {
				return;
			}
		}
		listeners.add(listener);
		listenerQueues.put(type, listeners);
	}

	class Message {
		String type;
		Object data;

		Message(String type, Object data) {
			this.type = type;
			this.data = data;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public Object getData() {
			return data;
		}

		public void setData(Object data) {
			this.data = data;
		}
	}

	class MessageConsumer implements Runnable {
		@Override
		public void run() {
			while (true) {
				Message message = MessageBus.getInstance().poll();
				if (message != null) {
					List<Listener> listeners = MessageBus.getInstance().getListeners(message.type);
					for (Listener listener : listeners) {
						listener.handle(message);
					}
				} else {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

}
