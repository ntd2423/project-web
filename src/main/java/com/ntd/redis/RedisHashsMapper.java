package com.ntd.redis;

import com.ntd.entity.User;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCommands;
import redis.clients.jedis.Response;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPipeline;

public class RedisHashsMapper {

	private static Logger logger = LoggerFactory.getLogger(RedisHashsMapper.class);

	private static final String DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";

	private Document configXMLDocument;

	private Resource configMapperXml;

	private int loaded = 0;

	static{
		ConvertUtils.register(new StringToDateConverter(DATE_PATTERN), Date.class);
	}

	public Resource getConfigMapperXml() {
		return configMapperXml;
	}

	public void setConfigMapperXml(Resource configMapperXml) {
		this.configMapperXml = configMapperXml;
	}

	public boolean loadConfig() {
		if (loaded == 0) {
			try {
				File configFile = configMapperXml.getFile();
				SAXReader saxReader = new SAXReader();
					configXMLDocument = saxReader.read(configFile);
					loaded = 1;
			} catch (DocumentException e) {
				loaded = -1;
				logger.warn("读取Redis缓存映射文件失败", e);
			} catch (IOException e) {
				loaded = -1;
				logger.error("读取Redis缓存映射文件出错", e);
			}
		}
		return loaded == 1;
	}

	/**
	 * 根据设置的对应关系，将一个JAVA对象转换成redis中存储的Hashs数据
	 * 
	 * @param jedis
	 *            jedis对象
	 * @param key
	 *            存储的Redis键值
	 * @param mapId
	 *            设置的对象关系的ID
	 * @param value
	 *            需要存储的对象
	 * @param forceUpdate
	 *            是否需要强制更新，如果为空的话，则JAVA对象中的空值，其对应的Hashs数据中的属性将被删除
	 */
	@SuppressWarnings("unchecked")
	public void save(JedisCommands jedis, String key, String mapId,
					 Object value, boolean forceUpdate) {
		if (loadConfig()) {
			Element hashConfig = (Element) configXMLDocument
					.selectSingleNode("/mapper/map[@id='" + mapId + "']");
			if (null == hashConfig) {
				logger.warn("保存对象到Redis缓存时失败，属性映射文件错误，缺少" + mapId);
				return;
			}
			List<Element> propMaps = hashConfig.elements();
			Map<String, String> hash = new HashMap<String, String>();
			for (Element element : propMaps) {
				String propName = element.attributeValue("property");
				String fieldName = element.attributeValue("field");

				try {
					Object propValue = PropertyUtils.getProperty(value,
							propName);
					if (null == propValue) {
						continue;
					} else {
						if (null != propValue) {
							if (propValue instanceof Date) {
								SimpleDateFormat format = new SimpleDateFormat(
										DATE_PATTERN);
								propValue = format.format(propValue);
							}
							hash.put(fieldName, propValue.toString());
						}
					}
				} catch (Exception e) {
					logger.warn("设置Redis缓存时属性提取失败mapId:[" + propName
							+ "],property:[" + propName + "],fieldName:["
							+ fieldName + "]", e);
				}

			}
			jedis.hmset(key, hash);
		} else {
			logger.warn("初始化Redis缓存失败，无法加载");
		}
	}

	/**
	 * 
	 * @param jedis
	 * @param mapId
	 * @param forceUpdate
	 */
	@SuppressWarnings("unchecked")
	public void mutiSave(ShardedJedis jedis, List<String> keys, String mapId,
						 @SuppressWarnings("rawtypes") List values, boolean forceUpdate) {
		if (loadConfig()) {
			Element hashConfig = (Element) configXMLDocument
					.selectSingleNode("/mapper/map[@id='" + mapId + "']");
			if (null == hashConfig) {
				logger.warn("保存对象到Redis缓存时失败，属性映射文件错误，缺少" + mapId);
				return;
			}
			if (null == keys || null == values || keys.size() != values.size()) {
				logger.warn("保存对象到Redis时发现key和值个数不正确");
				return;
			}
			List<Element> propMaps = hashConfig.elements();

			ShardedJedisPipeline pipeline = jedis.pipelined();
			for (int i = 0; i < keys.size(); i++) {
				Object value = values.get(i);

				if (null == value) {
					continue;
				}

				String key = keys.get(i);

				Map<String, String> hash = new HashMap<String, String>();
				for (Element element : propMaps) {
					String propName = element.attributeValue("property");
					String fieldName = element.attributeValue("field");

					try {
						Object propValue = PropertyUtils.getProperty(value,
								propName);
						if (null == propValue) {
							continue;
						} else {
							if (null != propValue) {
								if (propValue instanceof Date) {
									SimpleDateFormat format = new SimpleDateFormat(
											DATE_PATTERN);
									propValue = format.format(propValue);
								}
								hash.put(fieldName, propValue.toString());
							}
						}
					} catch (Exception e) {
						logger.warn("设置Redis缓存时属性提取失败mapId:[" + propName
								+ "],property:[" + propName + "],fieldName:["
								+ fieldName + "]", e);
					}

				}
				pipeline.hmset(key, hash);
			}
			pipeline.sync();
		} else {
			logger.warn("初始化Redis缓存失败，无法加载");
		}
	}
	
	public void saveMap(ShardedJedis jedis, String key, Map<String,Object> map, int keepSeconds){
		ShardedJedisPipeline pipeline = jedis.pipelined();
		Iterator<Map.Entry<String,Object>> iter = map.entrySet().iterator();
		while(iter.hasNext()){
			Map.Entry<String,Object> entry = iter.next();
			if(entry.getValue() != null){
				Object value = entry.getValue();
				if(value instanceof Date){
					SimpleDateFormat format = new SimpleDateFormat(
							DATE_PATTERN);
					value = format.format(value);
				}
				pipeline.hset(key, entry.getKey(), value.toString());
			}
		}
		if(keepSeconds > 0) {
			pipeline.expire(key, keepSeconds);
		}
		pipeline.sync();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Object retrieve(JedisCommands jedis, String key, String mapId) {
		if (loadConfig()) {
			if (!jedis.exists(key)) {
				return null;
			}
			Element hashConfig = (Element) configXMLDocument
					.selectSingleNode("/mapper/map[@id='" + mapId + "']");
			if (null == hashConfig) {
				logger.warn("保存对象到Redis缓存时失败，属性映射文件错误，缺少" + mapId);
			}

			String className = hashConfig.attributeValue("type");
			if (null == className) {
				logger.warn("没有配置到指定的类");
				return null;
			}
			Class targetClass = null;
			try {
				targetClass = Class.forName(className);
			} catch (ClassNotFoundException e) {
				logger.warn("无法获取到指定的类", e);
				return null;
			}
			Object target = null;
			try {
				target = targetClass.newInstance();
			} catch (InstantiationException e) {
				logger.warn("初始化类时发生异常", e);
				return null;
			} catch (IllegalAccessException e) {
				logger.warn("初始化对象时发生访问异常", e);
				return null;
			}

			List<Element> propMaps = hashConfig.elements();

			String[] fieldNames = new String[propMaps.size()];
			String[] propNames = new String[propMaps.size()];

			for (int i = 0; i < propMaps.size(); i++) {
				Element element = propMaps.get(i);
				String propName = element.attributeValue("property");
				String fieldName = element.attributeValue("field");

				propNames[i] = propName;
				fieldNames[i] = fieldName;
			}
			List<String> values = jedis.hmget(key, fieldNames);
			for (int i = 0; i < fieldNames.length; i++) {
				Class propType;
				try {
					propType = PropertyUtils.getPropertyDescriptor(target,
							propNames[i]).getPropertyType();

					Object propValue = ConvertUtils.convert(values.get(i),
							propType);
					PropertyUtils.setProperty(target, propNames[i], propValue);
				} catch (Exception e) {
					logger.warn("从Redis缓存中取值并复制到对象时发生异常，class:[" + targetClass
							+ "], property:[" + propNames[i] + "],value:["
							+ values.get(i) + "]", e);
					continue;
				}
			}
			return target;
		}
		logger.warn("初始化Redis缓存失败，无法加载");
		return null;
	}
	
    public <T> List<T> mutiRetrieve(ShardedJedis jedis, List<String> keys, String mapId, CacheSetter<String> cacheSetter, String resultClassName) {
		if (loadConfig()) {
		    
			Element hashConfig = (Element) configXMLDocument
					.selectSingleNode("/mapper/map[@id='" + mapId + "']");
			
			if (null == hashConfig) {
				logger.warn("保存对象到Redis缓存时失败，属性映射文件错误，缺少" + mapId);
			}

			String className = hashConfig.attributeValue("type");
			if (null == className) {
				logger.warn("没有配置到指定的类");
				return null;
			}
			
			
			if (!resultClassName.equals(className)) {
			    logger.warn("配置的类不匹配");
			    return null;
			}

			try {
			    @SuppressWarnings("unchecked")
                Class<T> targetClass = (Class<T>) Class.forName(className);
			
        		@SuppressWarnings("unchecked")
                List<Element> propMaps = hashConfig.elements();
        
        		String[] fieldNames = new String[propMaps.size()];
        		String[] propNames = new String[propMaps.size()];
        
        		for (int i = 0; i < propMaps.size(); i++) {
        			Element element = propMaps.get(i);
        			String propName = element.attributeValue("property");
        			String fieldName = element.attributeValue("field");
        
        			propNames[i] = propName;
        			fieldNames[i] = fieldName;
        		}
        
        		ShardedJedisPipeline pipeline = jedis.pipelined();
        
        		// 先检查哪些KEY是存在的，只检查存在的KEY
        		List<Response<Boolean>> existResps = new ArrayList<Response<Boolean>>();
        		Set<String> hasCachedKeys = new HashSet<String>();
        		for (int i = 0; i < keys.size(); i++) {
        			existResps.add(pipeline.exists(keys.get(i)));
        		}
        		pipeline.sync();
        
        		
        		for (int i = 0; i < keys.size(); i++) {
        			String k = keys.get(i);
        			if (existResps.get(i).get()) {
        				hasCachedKeys.add(k);
        			}else if(cacheSetter != null && cacheSetter.set(k)) {
        				hasCachedKeys.add(k);
        			}
        		}
        
        		if (hasCachedKeys.size() == 0) {
        			return new ArrayList<T>(keys.size());
        		}
        
        		String[] cachedKeys = new String[hasCachedKeys.size()];
        		cachedKeys = hasCachedKeys.toArray(cachedKeys);
        
        		List<Response<List<String>>> resps = new ArrayList<Response<List<String>>>();
        		
        		pipeline = jedis.pipelined();
        		
        		for (int i = 0; i < cachedKeys.length; i++) {
        			resps.add(pipeline.hmget(cachedKeys[i], fieldNames));
        		}
        
        		pipeline.sync();
        
        		Map<String, T> cachedObjectMap = new HashMap<String, T>();
        		
        		for (int i = 0; i < cachedKeys.length; i++) {
        			T target = null;
        			try {
        				target = targetClass.newInstance();
        			} catch (InstantiationException e) {
        				logger.warn("初始化类时发生异常", e);
        				continue;
        			} catch (IllegalAccessException e) {
        				logger.warn("初始化对象时发生访问异常", e);
        				continue;
        			} 
        
        			List<String> values = resps.get(i).get();
        			for (int j = 0; j < fieldNames.length; j++) {
        				Class<?> propType;
        				try {
        					propType = PropertyUtils.getPropertyDescriptor(target,
        							propNames[j]).getPropertyType();

        					Object propValue = ConvertUtils.convert(values.get(j),
        							propType);
        					PropertyUtils.setProperty(target, propNames[j],
        							propValue);
        				} catch (Exception e) {
        					logger.warn("从Redis缓存中取值并复制到对象时发生异常，class:["
        							+ className + "], property:[" + propNames[j]
        							+ "],value:[" + values.get(j) + "]", e);
        					continue;
        				}
        			}
        
        			cachedObjectMap.put(cachedKeys[i], target);
        		}
        
        		List<T> result = new ArrayList<T>();
        		for (int i = 0; i < keys.size(); i++) {
        			result.add(cachedObjectMap.get(keys.get(i)));
        		}
        		cachedObjectMap.clear();
        		return result;
			} catch (ClassNotFoundException e) {
			    logger.warn("类加载异常", e);
			    return null;
			}
		}
		logger.warn("初始化Redis缓存失败，无法加载");
		return null;
	}
	
	public static interface CacheSetter<K>{
		boolean set(K key);
	}

	public static void main(String[] args) {
		RedisHashsMapper mapper = new RedisHashsMapper();
		mapper.setConfigMapperXml(new ClassPathResource("redis/rediscache_map.xml"));
		User user = new User();
		user.setUserId(1);
		user.setName("张三");
		user.setPhone("123456");
		user.setAge(20);
		Jedis jedis = new Jedis("114.113.202.254");
		mapper.save(jedis, "user#1000001#", "user", user, true);

		User user2 = (User) mapper.retrieve(jedis, "user#1000001#", "user");
		System.out.println(user2);

		System.out.println(user2.getUserId());
		System.out.println(user2.getName());
		System.out.println(user2.getPhone());
		System.out.println(user2.getAge());

		User user3 = (User) mapper.retrieve(jedis, "user#NOTEXSITED#", "user");
		System.out.println(user3);
	}
}
