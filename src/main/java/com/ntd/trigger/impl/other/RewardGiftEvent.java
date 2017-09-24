package com.ntd.trigger.impl.other;

import com.ntd.trigger.Event;
import com.ntd.trigger.EventType;

public class RewardGiftEvent implements Event {
	
	private long userId;
	private int giftId;
	private int num;
	
	public RewardGiftEvent(long userId,int giftId,int num) {
		this.userId = userId;
		this.setGiftId(giftId);
		this.setNum(num);
	}

	@Override
	public EventType getType() {
		return EventType.REWARD_GIFT;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public int getGiftId() {
		return giftId;
	}

	public void setGiftId(int giftId) {
		this.giftId = giftId;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

}
