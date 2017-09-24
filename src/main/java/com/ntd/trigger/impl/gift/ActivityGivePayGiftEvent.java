package com.ntd.trigger.impl.gift;

import com.ntd.trigger.Event;
import com.ntd.trigger.EventType;
import java.util.List;

public class ActivityGivePayGiftEvent implements Event {

	private List<Activity> activities;

	private GiveGiftInfo giveGiftInfo;

	public ActivityGivePayGiftEvent(List<Activity> activities,
									GiveGiftInfo giveGiftInfo) {
		super();
		this.setActivities(activities);
		this.giveGiftInfo = giveGiftInfo;
	}

	@Override
	public EventType getType() {
		return EventType.GIVE_PAY_GIFT;
	}

	public List<Activity> getActivities() {
		return activities;
	}

	public void setActivities(List<Activity> activities) {
		this.activities = activities;
	}

	public GiveGiftInfo getGiveGiftInfo() {
		return giveGiftInfo;
	}

	public void setGiveGiftInfo(GiveGiftInfo giveGiftInfo) {
		this.giveGiftInfo = giveGiftInfo;
	}

}
