package com.ntd.trigger.impl.other;

import com.ntd.trigger.Event;
import com.ntd.trigger.Trigger;
import com.ntd.utils.HttpUtil;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.stereotype.Component;

@Component
public class BoCurrencyChangeTrigger implements Trigger {

	@Override
	public void excute(Event event) {
		if (event instanceof BoCurrencyChangeEvent) {
			BoCurrencyChangeEvent boCurrencyChangeEvent = (BoCurrencyChangeEvent) event;
			HttpUtil.postUrl(
					"/broadcast/cCurrency.do",
					new BasicNameValuePair("userId", String
							.valueOf(boCurrencyChangeEvent.getUserId())));
		}
	}

}
