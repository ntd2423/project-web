package com.ntd.aop.annotation.db;

import java.lang.reflect.Method;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.ClassFilter;
import org.springframework.aop.MethodMatcher;
import org.springframework.aop.Pointcut;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

public class DBReadWritePointcut implements Pointcut {

	private static Logger logger = LoggerFactory.getLogger(DBReadWritePointcut.class);

	private MethodMatcher methodMatcher = new MethodMatcher() {

		@Override
		public boolean matches(Method method, Class<?> targetClass,
							   Object[] args) {
			return matches(method, targetClass);
		}

		@Override
		public boolean matches(Method method, Class<?> targetClass) {
			/*SwitchDataSource swDs = AnnotationUtils.getAnnotation(method, SwitchDataSource.class);
			if (null != swDs) {
				logger.debug("DBReadWritePointcut matched method: {}", method.getName());
				return true;
			}
			return false;*/

			logger.debug("DBReadWritePointcut matched method: {}, targetClass：{}", new Object[]{method.getName(), targetClass});
			return true;
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
			return AnnotationUtils.isAnnotationDeclaredLocally(
					Service.class, clazz)
					|| AnnotationUtils.isAnnotationInherited(Service.class,
					clazz)|| AnnotationUtils.isAnnotationDeclaredLocally(Repository.class,
					clazz)|| AnnotationUtils.isAnnotationInherited(Repository.class,
					clazz);
		}
	};

	@Override
	public ClassFilter getClassFilter() {
		return classFilter;
	}

}
