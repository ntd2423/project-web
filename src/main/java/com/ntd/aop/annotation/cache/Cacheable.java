package com.ntd.aop.annotation.cache;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Cacheable {

	/**
	 * key前缀
	 */
	String keyPre() default "";

	int cacheTime() default 60;

	Class<?> generic() default Integer.class;
}
