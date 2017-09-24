package com.ntd.job;

import com.ntd.job.basic.AbstractJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ActivityRankInoJob extends AbstractJob {
	
	private static final Logger logger = LoggerFactory.getLogger(ActivityRankInoJob.class);

	@Override
	protected void executeInternal() {
		try {

			logger.info("FamilyPKActivityRankInoJob start!!!");

			logger.info("FamilyPKCommonActivityRankInoJob end!!!");
		} catch (Exception e) {
			logger.error("FamilyPKCommonActivityRankInoJob run timer task failed!!!", e);
		}
		
	}
	
}
