package com.ntd.job.basic;

import com.ntd.utils.SpringContextUtil;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import redis.clients.jedis.ShardedJedis;
import redis.clients.util.Pool;

public abstract class AbstractJob extends AbstractRedisLockJob{

	static ScheduledExecutorService JOB_EXECUTOR_SERVICE = Executors.newScheduledThreadPool(2);
	
	private Pool<ShardedJedis> jedisPool = (Pool<ShardedJedis>) SpringContextUtil.getBean("shardedJedisPool");

	@Override
	protected Pool<ShardedJedis> getShardedJedisPool(){
		return jedisPool;
	}

}
