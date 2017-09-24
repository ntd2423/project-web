package com.ntd.aop.annotation.cache;

import com.ntd.redis.JedisTemplate;
import com.ntd.utils.JSONUtil;
import java.lang.reflect.Method;
import java.util.List;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;

public class CacheableInterceptor implements MethodInterceptor {


	private static final Logger logger = LoggerFactory.getLogger(CacheableInterceptor.class);

	@Autowired
	private JedisTemplate jedisTemplate;

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		Object rval = preHandleInternal(invocation);
		if (rval != null) {
			return rval;
		}
		try {
			rval = invocation.proceed();
		} catch (Throwable ex) {
			throw ex;
		}
		postHandleInternal(invocation, rval);
		return rval;
	}

	private Object preHandleInternal(MethodInvocation invocation) {

		Object[] params = invocation.getArguments();
		Method method = invocation.getMethod();
		String mname = method.getName();
		String key = getKey(method, method.getAnnotation(Cacheable.class).keyPre(), params);

		// 有泛型，是个集合
		if (method.getAnnotation(Cacheable.class).generic() != Integer.class) {

			if (List.class.getName().equals(method.getReturnType().getName())) {

				return this.getCacheList(method.getReturnType(), key, method.getAnnotation(Cacheable.class).generic());
			}
		}
		return getCache(method.getReturnType(), key);
	}

	// 设置缓存
	private void postHandleInternal(MethodInvocation invocation, Object obj) {

		Object[] params = invocation.getArguments();
		Method method = invocation.getMethod();
		String key = getKey(method, method.getAnnotation(Cacheable.class).keyPre(), params);
		int cacheTime = invocation.getMethod().getAnnotation(Cacheable.class).cacheTime();
		if(obj != null){
			setCache(key, obj, cacheTime);
		}
	}

	private Object getCache(Class<?> clazz, final String key){
		String value = jedisTemplate.execute(new JedisTemplate.JedisAction<String>() {

			@Override
			public String action(Jedis jedis) {
				return jedis.get(key);
			}
		});
		if(value == null){
			return null;
		}
		logger.debug("get from cache: {}", key);
		return JSONUtil.fromJson(value, clazz);
	}

	private List<?> getCacheList(Class<?> clazz, final String key, Class<?> generic){
		String value = jedisTemplate.execute(new JedisTemplate.JedisAction<String>() {

			@Override
			public String action(Jedis jedis) {
				return jedis.get(key);
			}
		});
		if(value == null){
			return null;
		}
		logger.debug("get from cache: {}", key);

		// 如果是list
		if (clazz.getName().equals(List.class.getName())) {

			List<?> returnList = JSONUtil.fromJsonList(value, generic);
			return returnList;
		}

		return null;
	}

	private void setCache(final String key, final Object value, final int cacheTime){
		logger.debug("set cache: {}", key);
		jedisTemplate.execute(new JedisTemplate.JedisActionNoResult() {


			@Override
			public void action(Jedis jedis) {
				jedis.setex(key, cacheTime, JSONUtil.toJson(value));
			}
		});
	}

	private String getKey(Method method, String keyPre, Object[] params){
		String kp = keyPre;
		if(StringUtils.isBlank(kp)){
			kp = method.getDeclaringClass().getName();
		}
		StringBuilder sb = new StringBuilder(kp);
		for(Object obj : params){
			if(obj != null){
				sb.append("#").append(obj.toString());
			}
		}
		return sb.toString();
	}

}
