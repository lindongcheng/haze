package com.haze.sdk;


import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD,ElementType.PARAMETER,ElementType.METHOD,ElementType.TYPE})
@Documented
public @interface Logout {

	/**
	 * 被修饰域是否关闭日志输出功能
	 * @return
	 */
	boolean ignore() default false;
}
