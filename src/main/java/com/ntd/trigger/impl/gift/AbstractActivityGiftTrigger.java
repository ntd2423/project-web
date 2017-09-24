package com.ntd.trigger.impl.gift;

import com.ntd.trigger.Trigger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractActivityGiftTrigger implements Trigger {

	private static Logger logger = LoggerFactory.getLogger(AbstractActivityGiftTrigger.class);

	protected void doExtension(CallActivityScriptResult result, GiveGiftInfo giveGiftInfo) {

	}

}
