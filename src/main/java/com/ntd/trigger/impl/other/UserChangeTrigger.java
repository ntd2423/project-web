package com.ntd.trigger.impl.other;

import com.ntd.trigger.Event;
import com.ntd.trigger.Trigger;
import com.ntd.utils.HttpUtil;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.stereotype.Component;

@Component
public class UserChangeTrigger implements Trigger {

	@Override
	public void excute(Event event) {
		if(event instanceof UserChangeEvent) {
			UserChangeEvent userChangeEvent = (UserChangeEvent) event;
			HttpUtil.postUrl(
					"/broadcast/userChange.do",
					new BasicNameValuePair("userId", String.valueOf(userChangeEvent.getUserId())),
					new BasicNameValuePair("field", userChangeEvent.getFieldName()),
					new BasicNameValuePair("value", userChangeEvent.getFieldValue()));
		}
	}

}
