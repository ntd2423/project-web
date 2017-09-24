package com.ntd.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.exceptions.JedisConnectionException;
import redis.clients.jedis.exceptions.JedisException;
import redis.clients.util.Pool;

public class JedisTemplate {

	private Logger logger = LoggerFactory.getLogger(getClass());

	private Pool<Jedis> jedisPool;
	private Pool<ShardedJedis> shardedJedisPool;

	public void setJedisPool(Pool<Jedis> jedisPool) {
		this.jedisPool = jedisPool;
	}
	public void setShardedJedisPool(Pool<ShardedJedis> shardedJedisPool) {
		this.shardedJedisPool = shardedJedisPool;
	}

    public static interface JedisAction<T>{
        T action(Jedis jedis);
    }

    public static interface JedisActionNoResult{
        void action(Jedis jedis);
    }

    public static interface ShardJedisAction<T>{
        T action(ShardedJedis jedis);
    }

    public static interface ShardJedisActionNoResult {
        void action(ShardedJedis jedis);
    }

    public <T> T execute(JedisAction<T> jedisAction) throws JedisException{
		Jedis jedis = null;
		boolean broken = false;
		try {
			jedis = jedisPool.getResource();
			return jedisAction.action(jedis);
		} catch (JedisConnectionException e) {
			logger.error(e.getMessage(), e);
			broken = true;
			throw e;
		} finally {
			closeResource(jedis, broken);
		}
	}

	public void execute(JedisActionNoResult jedisActionNoResult) throws JedisException{
		Jedis jedis = null;
		boolean broken = false;
		try {
			jedis = jedisPool.getResource();
			jedisActionNoResult.action(jedis);
		} catch (JedisConnectionException e) {
			logger.error(e.getMessage(), e);
			broken = true;
			throw e;
		} finally {
			closeResource(jedis, broken);
		}
	}

	private void closeResource(Jedis jedis, boolean connectionBroken) {
		if (jedis != null) {
			if (connectionBroken) {
				jedisPool.returnBrokenResource(jedis);
			} else {
				jedisPool.returnResource(jedis);
			}
		}
	}

	public <T> T execute(ShardJedisAction<T> jedisAction) throws JedisException{
		ShardedJedis jedis = null;
		boolean broken = false;
		try {
			jedis = shardedJedisPool.getResource();
			return jedisAction.action(jedis);
		} catch (JedisConnectionException e) {
			logger.error(e.getMessage(), e);
			broken = true;
			throw e;
		} finally {
			closeResource(jedis, broken);
		}
	}

	public void execute(ShardJedisActionNoResult jedisActionNoResult) throws JedisException{
		ShardedJedis jedis = null;
		boolean broken = false;
		try {
			jedis = shardedJedisPool.getResource();
			jedisActionNoResult.action(jedis);
		} catch (JedisConnectionException e) {
			logger.error(e.getMessage(), e);
			broken = true;
			throw e;
		} finally {
			closeResource(jedis, broken);
		}
	}

    private void closeResource(ShardedJedis jedis, boolean connectionBroken) {
        if (jedis != null) {
            if (connectionBroken) {
                shardedJedisPool.returnBrokenResource(jedis);
            } else {
                shardedJedisPool.returnResource(jedis);
            }
        }
    }

	public Boolean exists(final String key){
		return exists(key, false);
	}

	public Boolean exists(final String key, boolean sharded){
		if(sharded){
			return execute(new ShardJedisAction<Boolean>(){

				@Override
				public Boolean action(ShardedJedis jedis) {
					return jedis.exists(key);
				}

			});
		}
		return execute(new JedisAction<Boolean>(){

			@Override
			public Boolean action(Jedis jedis) {
				return jedis.exists(key);
			}

		});
	}

	/////////////////////////////字符串封装start/////////////////////////////

	public String get(String key){
		return get(key, false);
	}

	public String get(final String key, boolean sharded){
		if(sharded){
			return execute(new ShardJedisAction<String>(){

				@Override
				public String action(ShardedJedis jedis) {
					return jedis.get(key);
				}

			});
		}
		return execute(new JedisAction<String>(){

			@Override
			public String action(Jedis jedis) {
				return jedis.get(key);
			}

		});
	}

	public String set(final String key, final String value){
		return set(key, value, false);
	}

	public String set(final String key, final String value, boolean sharded){
		if(sharded){
			return execute(new ShardJedisAction<String>(){

				@Override
				public String action(ShardedJedis jedis) {
					return jedis.set(key, value);
				}

			});
		}
		return execute(new JedisAction<String>(){

			@Override
			public String action(Jedis jedis) {
				return jedis.set(key, value);
			}

		});
	}

	public String setex(final String key, final int seconds, final String val){
		return setex(key, seconds, val, false);
	}

	public String setex(final String key, final int seconds, final String val, boolean sharded){
		if(sharded){
			return execute(new ShardJedisAction<String>(){

				@Override
				public String action(ShardedJedis jedis) {
					return jedis.setex(key, seconds, val);
				}

			});
		}
		return execute(new JedisAction<String>(){

			@Override
			public String action(Jedis jedis) {
				return jedis.setex(key, seconds, val);
			}

		});
	}


	/////////////////////////////字符串封装end/////////////////////////////

}
