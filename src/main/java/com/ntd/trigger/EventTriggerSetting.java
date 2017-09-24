package com.ntd.trigger;

import java.util.List;

public interface EventTriggerSetting {
	
	public List<Trigger> getTriggers(EventType type);

}
