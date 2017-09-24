package com.ntd.trigger.impl.gift;

import com.ntd.trigger.Event;
import com.ntd.trigger.EventType;

public class GeneralGiveGiftEvent implements Event {

	@Override
	public EventType getType() {
		return EventType.GENERAL_GIVE_GIFT;
	}

}
