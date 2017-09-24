package com.ntd.trigger.impl.other;

import com.ntd.trigger.Event;
import com.ntd.trigger.Trigger;
import com.ntd.utils.HttpUtil;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.stereotype.Component;

@Component
public class RewardGiftTrigger implements Trigger {

	@Override
	public void excute(Event event) {
		if (event instanceof RewardGiftEvent) {
			RewardGiftEvent rewardGiftEvent = (RewardGiftEvent) event;
			HttpUtil.postUrl("/broadcast/rewardGiftNumber.do",
					new BasicNameValuePair("userId", String.valueOf(rewardGiftEvent.getUserId())),
					new BasicNameValuePair("giftId", String.valueOf(rewardGiftEvent.getGiftId())),
					new BasicNameValuePair("num", String.valueOf(rewardGiftEvent.getNum())));
		}
	}

}
