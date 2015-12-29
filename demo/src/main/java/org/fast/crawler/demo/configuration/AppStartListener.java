package org.fast.crawler.demo.configuration;

import org.fast.crawler.core.manager.CrawlerManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class AppStartListener implements ApplicationListener<ContextRefreshedEvent> {

	@Autowired
	CrawlerManager crawlerManager;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if (event.getApplicationContext().getParent() == null) {
			crawlerManager.start();
		}
	}

}
