package com.ntd.job.basic;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;
import redis.clients.jedis.exceptions.JedisConnectionException;
import redis.clients.util.Pool;

/**
 * Redis分布式锁,用简单的setnx和expire实现分布式锁和锁超时
 * 
 */
public class RedisLock {

	private static Log log = LogFactory.getLog(RedisLock.class);

	// 默认超时时间
	public static final long DEFAULT_TIME_OUT = 20000;

	// 最大锁定时间
	private static final int MAX_LOCK_DURATION = 5*60*1000;//5分钟

	public static final Random random = new Random();

	private Pool<ShardedJedis> jedisPool;

	public Pool<ShardedJedis> getJedisPool() {
		return jedisPool;
	}

	public void setJedisPool(Pool<ShardedJedis> jedisPool) {
		this.jedisPool = jedisPool;
	}

	public long tryLock(String lockKey) {
		return tryLock(lockKey, DEFAULT_TIME_OUT);
	}

	/**
	 * 试着去获取Redis分布式锁，如果没有获取到，则等待一段时间。返回具体存储的锁的时间戳，用来作为解锁时的标示
	 *
	 */
	public long tryLock(String lockKey, long timeout) {
		ShardedJedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			Long current = System.currentTimeMillis();
			Long expireTime = current + MAX_LOCK_DURATION;
			do {
				Long i = jedis.setnx(lockKey, Long.toString(expireTime));
				if (i == 1) {
					// 获取锁
					if (log.isDebugEnabled()) {
						log.debug("try lock key success, lock get");
					}
					jedis.expire(lockKey, MAX_LOCK_DURATION / 1000);
					return expireTime;
				}else{
					//防止expire失败时造成永远都取不到锁
					String val = jedis.get(lockKey);
					if(StringUtils.isNotBlank(val)){
						if((System.currentTimeMillis()- NumberUtils.toLong(val)) > MAX_LOCK_DURATION){
							jedis.del(lockKey);
							log.info("over max lock duration, del key:" + lockKey);
						}
					}
				}
			} while ((System.currentTimeMillis() - current) < timeout);
			return 0l;
		} catch (JedisConnectionException je) {
			log.error(je.getMessage(), je);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			if (null != jedis) {
				jedisPool.returnResource(jedis);
			}
		}
		return 0l;
	}

	public void unlock(String lockKey, long lockValue) {
		ShardedJedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			if (jedis.exists(lockKey)) {
				String value = jedis.get(lockKey);
				if ((value != null) && Long.parseLong(value) == lockValue) {
					// 只删除由自己建立的lock，防止因为任务太久超时而删除了别的进程占有的锁
					jedis.del(lockKey);
					if (log.isDebugEnabled()) {
						log.debug("unlock key:" + lockKey);
					}
				}
			}
		} catch (JedisConnectionException je) {
			log.error(je.getMessage(), je);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			if (null != jedis) {
				jedisPool.returnResource(jedis);
			}
		}
	}

	public static void main(String[] args) {
		ExecutorService service = Executors.newFixedThreadPool(50);
		for (int i = 0; i < 50; i++) {
			service.execute(new Runnable() {

				@Override
				public void run() {
					System.out.println("start thread");
					List<JedisShardInfo> shards = new ArrayList<JedisShardInfo>();
					shards.add(new JedisShardInfo("123.58.179.90"));
					ShardedJedisPool pool = new ShardedJedisPool(new JedisPoolConfig(),shards);
					RedisLock redisLock = new RedisLock();
					redisLock.setJedisPool(pool);
					long result = redisLock.tryLock("test", 3000000);
					System.out.println("1");
					try {
						Thread.sleep(3000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					redisLock.unlock("test", result);
				}
			});
		}
		service.shutdown();
	}
}
