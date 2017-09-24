package com.ntd.trigger.impl.gift.handle;

import com.ntd.trigger.impl.gift.GeneralGiveGiftEvent;

public interface Handler {

	public void handle(GeneralGiveGiftEvent generalGiveGiftEvent);

}
