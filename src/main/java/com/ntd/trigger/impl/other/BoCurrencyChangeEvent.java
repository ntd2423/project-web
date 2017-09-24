package com.ntd.trigger.impl.other;

import com.ntd.trigger.Event;
import com.ntd.trigger.EventType;

public class BoCurrencyChangeEvent implements Event {
	
	private long userId;
	private double amount;

	public BoCurrencyChangeEvent(long userId, double amount) {
		super();
		this.userId = userId;
		this.amount = amount;
	}

	@Override
	public EventType getType() {
		return EventType.BO_CURRENCY_CHANGE;
	}

	public long getUserId() {
		return userId;
	}

	public double getAmount() {
		return amount;
	}

}
