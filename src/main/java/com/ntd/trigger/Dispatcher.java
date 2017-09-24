package com.ntd.trigger;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Dispatcher {
	
	private static Dispatcher _dispatcher;
	
	private static ThreadPoolExecutor dispatchExecutor = new ThreadPoolExecutor(
			4, 32, 5, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(),
			new ThreadPoolExecutor.DiscardOldestPolicy());

	private static Logger logger = LoggerFactory.getLogger(Dispatcher.class);
	private EventTriggerSetting settingMap;
	
	public Dispatcher() {
		_dispatcher = this;
	}
	
	public void setEventTriggerSetting(EventTriggerSetting setting) {
		this.settingMap = setting;
	}
	
	public static void fire(Collection<Event> events) {
		if(events != null && !events.isEmpty()) {
			for(Event event : events) {
				fire(event);
			}
		}
	}

	public static void fire(final Event event) {
		List<Trigger> triggers = _dispatcher.settingMap.getTriggers(event.getType());
		if(triggers != null) {
			for(final Trigger trigger:triggers) {
				dispatchExecutor.execute(new Runnable() {
					
					@Override
					public void run() {
						try {
							logger.debug("excute trigger:" + trigger.getClass().getSimpleName());
							trigger.excute(event);
						}catch(Exception e) {
							logger.error(e.getMessage(), e);
						}
					}
					
				});
			}
		} else {
			logger.warn("no trigger for:" + event.getType().name());
		}
	}

}
