package org.fast.crawler.core.message;

import org.fast.crawler.core.message.MessageBus.Message;

public interface Listener {

	public void handle(Message message);

}
