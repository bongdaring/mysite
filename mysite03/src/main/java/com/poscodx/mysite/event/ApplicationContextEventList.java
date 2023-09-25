package com.poscodx.mysite.event;

import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;

public class ApplicationContextEventList {
	
	// ApplicationContext를 초기화하거나 새로 고칠 때 Spring은 ContextRefreshedEvent를 발생
	// ApplicationContext : 스프링 컨테이너, 객체(Bean)들을 관리
	@EventListener({ContextRefreshedEvent.class})
	public void handlerContextRefreshedEvent() {
		System.out.println("--- ContextRefreshedEvent Received ---");
		
	}
}
