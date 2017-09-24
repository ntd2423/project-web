package com.ntd.utils;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import com.google.common.collect.Maps;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

public abstract class MapUtil {

	public static <K, V> HashMap<K, V> of(K k1, V v1) {
		HashMap<K, V> map = new HashMap<>();
		map.put(k1, v1);
		return map;
	}
	
	public static <K, V> HashMap<K, V> of(K k1, V v1, K k2, V v2) {
		HashMap<K, V> map = new HashMap<>();
		map.put(k1, v1);
		map.put(k2, v2);
		return map;
	}
	
	public static <K, V> HashMap<K, V> of(K k1, V v1, K k2, V v2, K k3, V v3) {
		HashMap<K, V> map = new HashMap<>();
		map.put(k1, v1);
		map.put(k2, v2);
		map.put(k3, v3);
		return map;
	}
	
	public static <K, V> HashMap<K, V> of(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4) {
		HashMap<K, V> map = new HashMap<>();
		map.put(k1, v1);
		map.put(k2, v2);
		map.put(k3, v3);
		map.put(k4, v4);
		return map;
	}
	
	public static <K, V> HashMap<K, V> of(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5) {
		HashMap<K, V> map = new HashMap<>();
		map.put(k1, v1);
		map.put(k2, v2);
		map.put(k3, v3);
		map.put(k4, v4);
		map.put(k5, v5);
		return map;
	}
	
	/**
	 * list to map
	 * @param bean
	 * @param fields
	 * @return Map<String, Object>} the returned map is unmodifiable.
	 */
	public static <E, T extends Object> Map<String, T> toMap(E bean, String... fields) {
		try {
			Builder<String, T> builder = ImmutableMap.<String, T> builder();
			Method[] allMethods = bean.getClass().getMethods();
			List<Method> methods = new ArrayList<>();
			for (Method method : allMethods) {
				if (Modifier.isPublic(method.getModifiers()) 
						&& method.getParameterTypes().length == 0
						&& ( method.getName().startsWith("get") 
								|| (method.getName().startsWith("is") 
										&& (method.getReturnType() == boolean.class 
											|| method.getReturnType() == Boolean.class)))) {
					methods.add(method);
				}
			}
			if (ArrayUtils.isEmpty(fields)) {
				Map<String, T> temp = Maps.newHashMap();
				for (Method method : methods) {
					String methodName = method.getName();
					String property = getPropertyNameFromMethodName(methodName);
					if (methodName.startsWith("get")) {
						if (!temp.containsKey(property) ) {
							temp.put(property, (T) method.invoke(bean));
						}
					} else {
						temp.put(property, (T) method.invoke(bean));
					}
				}
				return builder.putAll(temp).build();
			} else {
				Map<String, Method> methodMap = toMap(methods, new KeyGetter<String, Method>() {
					@Override
					public String get(Method e) {
						return e.getName();
					}
				});
				
				for(String field : fields) {
					String isField = "is" + StringUtils.capitalize(field);
					if (methodMap.containsKey(isField)) {
						builder.put(field, (T) methodMap.get(isField).invoke(bean));
					} else {
						builder.put(field, (T) methodMap.get("get" + StringUtils.capitalize(field)).invoke(bean));
					}
				}
				return builder.build();
			}
		} catch (Exception e) {
			throw new IllegalArgumentException("bean to map error!", e);
		}
	}
	
	private static String getPropertyNameFromMethodName(String methodName) {
		if (methodName.startsWith("get")) {
			return StringUtils.uncapitalize(StringUtils.removeStart(methodName, "get"));
		} else {
			return StringUtils.uncapitalize(StringUtils.removeStart(methodName, "is"));
		}
	}
	
	/**
	 * list to map
	 * @param list
	 * @param keyGetter create key with type K
	 * @return {@code List<E> ==> Map<K, E>} the returned map is unmodifiable.
	 * @throws IllegalArgumentException when error occurs
	 */
	public static <K, E> Map<K, E> toMap(List<E> list, String field) {
		try {
			if (CollectionUtils.isEmpty(list)) {
				return ImmutableMap.of();
			}
			
			Builder<K, E> builder = ImmutableMap.<K, E> builder();
			if (Map.class.isAssignableFrom(list.get(0).getClass())) {
				for (E e : list) {
					builder.put((K) ((Map)e).get(field), e);
				}
				return builder.build();
			}
			
			Method getMethod = null, isMethod = null;
			Method[] allMethods = list.get(0).getClass().getMethods();
			String isField = "is" + StringUtils.capitalize(field);
			String getField = "get" + StringUtils.capitalize(field);
			for (Method method : allMethods) {
				if (isMethod == null && method.getName().equals(isField) && Modifier.isPublic(method.getModifiers()) && method.getParameterTypes().length == 0  && ( boolean.class == method.getReturnType() || Boolean.class == method.getReturnType())) {
					isMethod = method;
				}
				if (getMethod == null && method.getName().equals(getField) && Modifier.isPublic(method.getModifiers()) && method.getParameterTypes().length == 0) {
					getMethod = method;
				}
			}
			
			if (isMethod == null && getMethod == null) {
				throw new IllegalArgumentException("no accessor for the field!");
			}
			
			Method method = isMethod == null ? getMethod : isMethod;
			for (E e : list) {
				builder.put((K)method.invoke(e), e);
			}
			return builder.build();
		} catch (Exception e) {
			throw new IllegalArgumentException("list to map error!", e);
		}
	}
	
	/**
	 * list to map
	 * @param list
	 * @param keyGetter create key with type K
	 * @return {@code List<E> ==> Map<K, E>} the returned map is unmodifiable.
	 */
	public static <K, E> Map<K, E> toMap(List<E> list, KeyGetter<? extends K, E> keyGetter) {
		if (CollectionUtils.isEmpty(list)) {
			return ImmutableMap.of();
		}
		
		Builder<K, E> builder = ImmutableMap.<K, E> builder();
		for (E e : list) {
			builder.put(keyGetter.get(e), e);
		}
		return builder.build();
	}
	
	
	public static interface KeyGetter<K, E> {
		K get(E e);
	}
	
	public static void main(String[] args) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		/*Map<String, User> map = toMap(Arrays.asList(new User()), "age");
		System.out.println(map);*/
		System.out.println(Map.class.isAssignableFrom(List.class));
	}
	
	public static class User {
		private String name = "zhou";
		
		/*public boolean isAge() {
			return true;
		}*/
		public String getAge() {
			return "aa";
		}
	}
}
