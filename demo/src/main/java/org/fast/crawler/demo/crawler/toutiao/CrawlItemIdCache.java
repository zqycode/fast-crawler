package org.fast.crawler.demo.crawler.toutiao;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class CrawlItemIdCache {

	private static int MAX_NUMBER = 1000;
	private static Queue<Long> crawlItemIdQueue = new ConcurrentLinkedQueue<Long>();

	public static void put(Long value) {
		if (crawlItemIdQueue.contains(value))
			return;
		synchronized (crawlItemIdQueue) {
			if (crawlItemIdQueue.size() >= MAX_NUMBER) {
				crawlItemIdQueue.poll();
			}
			crawlItemIdQueue.add(value);
		}
	}

	public static boolean contains(Long value) {
		return crawlItemIdQueue.contains(value);
	}

}
