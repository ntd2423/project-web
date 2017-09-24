package com.ntd.trigger.impl.other;

import com.ntd.trigger.Event;
import com.ntd.trigger.EventType;

public class UserChangeEvent implements Event {

	private long userId;
	private String fieldName;
	private String fieldValue;
	
	public UserChangeEvent(long userId, String fieldName, String fieldValue) {
		super();
		this.userId = userId;
		this.fieldName = fieldName;
		this.fieldValue = fieldValue;
	}

	@Override
	public EventType getType() {
		return EventType.USER_CHANGE;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getFieldValue() {
		return fieldValue;
	}

	public void setFieldValue(String fieldValue) {
		this.fieldValue = fieldValue;
	}

}
