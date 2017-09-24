package com.ntd.aop.annotation.cache;

import java.lang.reflect.Method;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.ClassFilter;
import org.springframework.aop.MethodMatcher;
import org.springframework.aop.Pointcut;

public class CacheablePointcut implements Pointcut {

	private static final Logger logger = LoggerFactory.getLogger(CacheablePointcut.class);

	private Set<String> filteredClass;

	public Set<String> getFilteredClass() {
		return filteredClass;
	}

	public void setFilteredClass(Set<String> filteredClass) {
		this.filteredClass = filteredClass;
	}

	private MethodMatcher methodMatcher = new MethodMatcher() {

		@Override
		public boolean matches(Method method, Class<?> targetClass,
				Object[] args) {
			return matches(method, targetClass);
		}

		@Override
		public boolean matches(Method method, Class<?> targetClass) {
			boolean metched = (method.getAnnotation(Cacheable.class) != null);
			if(metched){
				logger.debug("CacheablePointcut matched method: {}", method.getName());
			}
			return metched;
		}

		@Override
		public boolean isRuntime() {
			return false;
		}
	};

	@Override
	public MethodMatcher getMethodMatcher() {
		return methodMatcher;
	}

	private ClassFilter classFilter = new ClassFilter() {

		@Override
		public boolean matches(Class<?> clazz) {
			for (String item : filteredClass) {
				Pattern p = Pattern.compile(item);
				Matcher m = p.matcher(clazz.getName());
				if (m.matches()) {
					logger.debug("CacheablePointcut matched class: {}", clazz.getName());
					return true;
				}
			}
			return false;
		}
	};

	@Override
	public ClassFilter getClassFilter() {
		return classFilter;
	}

}
