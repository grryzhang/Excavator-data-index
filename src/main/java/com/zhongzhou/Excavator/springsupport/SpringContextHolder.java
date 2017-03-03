package com.zhongzhou.Excavator.springsupport;

import org.springframework.beans.BeansException;  
import org.springframework.context.ApplicationContext;  
import org.springframework.context.ApplicationContextAware;  
import org.springframework.stereotype.Service;  
@Service  
public class SpringContextHolder implements ApplicationContextAware {  
	
    private ApplicationContext applicationContext;  
  
    @Override  
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {  
        this.applicationContext = applicationContext;  
    }  
  
    public ApplicationContext getApplicationContext() {  
        return applicationContext;  
    }  
    public Object getBean(String beanName) {  
        return applicationContext.getBean(beanName);  
    }  
    public <T>T getBean(String beanName , Class<T>clazz) {  
        return applicationContext.getBean(beanName , clazz);  
    }  
}  