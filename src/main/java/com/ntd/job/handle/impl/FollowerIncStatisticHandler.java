package com.ntd.job.handle.impl;

import com.ntd.job.handle.ChangeStatisticProtocol;
import java.util.concurrent.atomic.AtomicInteger;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
@Service("followerIncStatisticHandler")
public class FollowerIncStatisticHandler implements ChangeStatisticProtocol {
	
	private static final Log logger = LogFactory.getLog(FollowerIncStatisticHandler.class);
	
	private AtomicInteger tick = new AtomicInteger(1);
	
	private static final int multiple = 4;

	@Override
	public void doJob() {
		if(tick.getAndIncrement() == multiple) {
			try {

			} catch (Exception e) {
				logger.error(e.getMessage());
			} finally {
				//重置时钟
				tick.set(1);
			}
		}
	}

}
