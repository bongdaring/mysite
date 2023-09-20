package com.poscodx.mysite.security;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target(METHOD)
public @interface Auth {
	// @Auth("test")처럼 값을 넣어줄 경우
//	public String value() default "";
	// @Auth(value="test, test=false) 이렇게 사용 가능
	public String Role() default "USER";
	public boolean test() default false;
}
