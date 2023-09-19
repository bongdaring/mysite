package com.poscodx.mysite.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Component
@Aspect
public class MeasureExecutionTimeAspect {
	//*..* => com.poscodx.mysite
	// repository package에 있는 모든 클래스와 메소드들에 실행
	// @Around 안에 있는게 point cut
	@Around("execution(* *..*.repository.*.*(..)) ||  execution(* *..*.service.*.*(..)) || execution(* *..*.controller.*.*(..))")
	public Object adviceAround(ProceedingJoinPoint pjp) throws Throwable{
		// before
		StopWatch sw = new StopWatch();
		sw.start();
		
		Object result = pjp.proceed();
		
		// after
		sw.stop();
		long totalTime = sw.getTotalTimeMillis();
		
		// 실행 클래스 가져오기
		String className = pjp.getTarget().getClass().getName();
		
		// 실행 메소드 가져오기
		String methodName = pjp.getSignature().getName();
		String taskName = className + "."+methodName;
		System.out.println("[Execution Time]["+taskName+"] " + totalTime + "mills");
		
		return result;
	}
}
