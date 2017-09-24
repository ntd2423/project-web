package com.ntd.trigger.impl.gift.handle;

import com.ntd.trigger.impl.gift.GeneralGiveGiftEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class TaskHandler implements Handler {
	private static final Logger logger = LoggerFactory.getLogger(TaskHandler.class);

	@Override
	public void handle(GeneralGiveGiftEvent generalGiveGiftEvent) {

	}

}
