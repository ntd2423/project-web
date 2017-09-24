package com.ntd.job.handle.impl;

import com.ntd.job.handle.ChangeStatisticProtocol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("roomNumberUpdateHandler")
public class RoomNumberStatisticHandler implements ChangeStatisticProtocol {
	
	private static final Logger logger = LoggerFactory.getLogger(RoomNumberStatisticHandler.class);

	@Override
	public void doJob() {

	}

}
