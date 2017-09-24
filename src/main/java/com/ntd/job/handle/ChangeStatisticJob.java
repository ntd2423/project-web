package com.ntd.job.handle;

import com.ntd.job.basic.AbstractJob;
import com.ntd.utils.SpringContextUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;

public class ChangeStatisticJob extends AbstractJob {
	
	private static final Log logger = LogFactory.getLog(ChangeStatisticJob.class);

	@Override
	protected void executeInternal() throws Exception {
		String[] handlerList = SpringContextUtil.getApplicationContext().getBeanNamesForType(ChangeStatisticProtocol.class);
		if(handlerList != null && handlerList.length > 0 ) {
			for(String handler : handlerList) {
				try {
					ChangeStatisticProtocol statHandler = (ChangeStatisticProtocol) SpringContextUtil.getBean(handler);
					if(statHandler != null) {
						long start = System.currentTimeMillis();
						logger.info(handler + "-job start...");
						statHandler.doJob();
						logger.info(handler + "-job end.");
						logger.info(handler + "-job cost " + (System.currentTimeMillis() - start) + " ms");
					}
				} catch (BeansException e) {
					logger.error(e.getMessage());
				}
			}
		}
	}
}
