package com.ntd.trigger;

import com.ntd.trigger.impl.gift.ActivityGiveFreeGiftTrigger;
import com.ntd.trigger.impl.gift.ActivityGivePayGiftTrigger;
import com.ntd.trigger.impl.gift.GeneralGiveGiftEventTrigger;
import com.ntd.trigger.impl.other.BoCurrencyChangeTrigger;
import com.ntd.trigger.impl.other.RewardGiftTrigger;
import com.ntd.trigger.impl.other.UserChangeTrigger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommonEventTriggerSetting implements EventTriggerSetting {

	private Map<EventType, List<Trigger>> setting = new HashMap<EventType, List<Trigger>>();

	@Resource
	private ActivityGiveFreeGiftTrigger activityGiveFreeGiftTrigger;
	@Resource
	private ActivityGivePayGiftTrigger activityGivePayGiftTrigger;
	@Resource
	private GeneralGiveGiftEventTrigger generalGiveGiftEventTrigger;
	@Resource
	private BoCurrencyChangeTrigger boCurrencyChangeTrigger;
	@Resource
	private RewardGiftTrigger rewardGiftTrigger;
	@Resource
	private UserChangeTrigger userChangeTrigger;
	
	@PostConstruct
	public void init() {
		put(EventType.GIVE_PAY_GIFT, activityGivePayGiftTrigger);
		put(EventType.GIVE_FREE_GIFT, activityGiveFreeGiftTrigger);
		put(EventType.GENERAL_GIVE_GIFT, generalGiveGiftEventTrigger);
		put(EventType.BO_CURRENCY_CHANGE, boCurrencyChangeTrigger);
		put(EventType.REWARD_GIFT, rewardGiftTrigger);
		put(EventType.USER_CHANGE, userChangeTrigger);
	}
	
	private void put(EventType type, Trigger ... triggers ) {
		setting.put(type, Arrays.asList(triggers));
	}
	
	@Override
	public List<Trigger> getTriggers(EventType type) {
		return setting.get(type);
	}

}
