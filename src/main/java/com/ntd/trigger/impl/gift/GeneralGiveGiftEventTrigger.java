package com.ntd.trigger.impl.gift;

import com.ntd.trigger.Event;
import com.ntd.trigger.Trigger;
import com.ntd.trigger.impl.gift.handle.Handler;
import java.util.List;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GeneralGiveGiftEventTrigger implements Trigger {
	
	private static final Logger logger = LoggerFactory.getLogger(GeneralGiveGiftEventTrigger.class);
	
	@Autowired
	private List<Handler> handlers;

	@Override
	public void excute(Event event) {
		if(event instanceof GeneralGiveGiftEvent) {
			GeneralGiveGiftEvent generalGiveGiftEvent = (GeneralGiveGiftEvent) event;
			if (CollectionUtils.isNotEmpty(handlers)) {
				for (Handler handler : handlers) {
					handler.handle(generalGiveGiftEvent);
				}
			}
		}
	}
}
