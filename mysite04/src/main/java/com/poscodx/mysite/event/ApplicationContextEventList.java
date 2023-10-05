package com.poscodx.mysite.event;

import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;

import com.poscodx.mysite.service.SiteService;
import com.poscodx.mysite.vo.SiteVo;

public class ApplicationContextEventList {
	
	@Autowired
	private ApplicationContext applicationContext;
	
	// ApplicationContext를 초기화하거나 새로 고칠 때 Spring은 ContextRefreshedEvent를 발생
	// ApplicationContext : 스프링 컨테이너, 객체(Bean)들을 관리
	@EventListener({ContextRefreshedEvent.class})
	public void handlerContextRefreshedEvent() {
		System.out.println("--- ContextRefreshedEvent Received ---" + applicationContext);
		SiteService siteService = applicationContext.getBean(SiteService.class);
		SiteVo site = siteService.findSite();
		

		MutablePropertyValues propertyValues = new MutablePropertyValues();
		propertyValues.add("title", site.getTitle());
		propertyValues.add("profile", site.getProfile());
		propertyValues.add("welcome", site.getWelcome());
		propertyValues.add("description", site.getDescription());

		GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
		beanDefinition.setBeanClass(SiteVo.class);
		beanDefinition.setPropertyValues(propertyValues);

		AutowireCapableBeanFactory factory = applicationContext.getAutowireCapableBeanFactory();
		BeanDefinitionRegistry registry = (BeanDefinitionRegistry)factory;
		
		
		registry.registerBeanDefinition("site", beanDefinition);
		
	}
}





