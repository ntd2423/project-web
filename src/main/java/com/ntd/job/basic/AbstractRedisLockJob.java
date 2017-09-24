package com.ntd.job.basic;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import redis.clients.jedis.ShardedJedis;
import redis.clients.util.Pool;

public abstract class AbstractRedisLockJob {
	
	protected Log log = LogFactory.getLog(this.getClass());
	private Boolean isRunning = false;
	private RedisLock redisLock = new RedisLock();
	private static final int MIN_JOB_DURATION = 2*1000;//2秒
	private static final String JOB_REDIS_KEY_PREFIX = "JobRedisKey-";

	public void execute() {
		synchronized (isRunning) {
			if (isRunning) {
				if (log.isDebugEnabled()) {
					log.debug(this.getClass().getName() + " already running...");
				}
				return;
			}
			isRunning = true;
		}

		String redisKey = JOB_REDIS_KEY_PREFIX + getClass().getName();
		long ret = 0L;
		try {
			redisLock.setJedisPool(getShardedJedisPool());
			ret = redisLock.tryLock(redisKey, 0L);
			if(ret != 0L) {
				log.info("succeed to lock key : " + redisKey);
				long start = System.currentTimeMillis();
				executeInternal();
				long cost = System.currentTimeMillis() - start;
				long sleep_time = MIN_JOB_DURATION - cost;
				if(sleep_time > 0) {
					Thread.sleep(sleep_time);
				}
			} else {
				log.info("fail to lock key : " + redisKey);
			}
		} catch (Exception e) {
			log.error("定时任务执行错误"+this.getClass().getName(), e);
		} finally {
			synchronized (isRunning) {
				isRunning = false;
			}
			if(ret != 0L) {
				redisLock.unlock(redisKey, ret);
			}
		}
	}
	
	protected abstract Pool<ShardedJedis> getShardedJedisPool();

	protected abstract void executeInternal() throws Exception;

}
