package com.ntd.trigger.impl.gift;

import com.ntd.trigger.Event;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ActivityGivePayGiftTrigger extends AbstractActivityGiftTrigger {

	private static Logger logger = LoggerFactory.getLogger(ActivityGivePayGiftTrigger.class);

	@Override
	public void excute(Event event) {
		if(event instanceof ActivityGivePayGiftEvent) {
			ActivityGivePayGiftEvent aggEvent = (ActivityGivePayGiftEvent)event;
			final GiveGiftInfo giveGiftInfo = aggEvent.getGiveGiftInfo();
			List<Activity> activities = aggEvent.getActivities();

			//if(giveGiftInfo.isToAnchor()),如果是送给主播才走活动逻辑
			if(1 == 1) {
				//CallActivityScriptResult result = activityScriptService.onGiftSent(activities, giveGiftInfo);初始化活动礼物信息
				CallActivityScriptResult result = new CallActivityScriptResult();
				if(result != null) {
					try {
						//执行相关的插入数据库表操作
					} catch (Exception e) {
						logger.error(e.getMessage(), e);
					} finally {
						doExtension(result, giveGiftInfo);
					}
				}
			}
		}
	}

}
