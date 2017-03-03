package com.zhongzhou.Excavator.springsupport;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.apache.ibatis.binding.MapperProxy;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import com.zhongzhou.Excavator.Annotation.DataSource;

@Component
@Aspect
public class MultiDataSourceAspect {
	
	/*
	@Pointcut(value="execution(* com.SinoPerfect.pss.system.scheduler..*.run*(..))")
	public void addPermissionCheck(){
		
	}
	
	@Around( value="addPermissionCheck()" )
	public Object permissionAroundCheck( final ProceedingJoinPoint pjp ) throws Throwable{
		........
	}
	*
	*/

	@Before(value="execution(* com.zhongzhou.Excavator.DAO..*(..))")
    public void doBefore(JoinPoint jp) throws Throwable {
		
		try{
			MethodSignature menthodObject = (MethodSignature)jp.getSignature();
			Method targetMethod = menthodObject.getMethod();
			
			Class targetObject = targetMethod.getDeclaringClass();
			
			DataSource dataSourceAnnotation = (DataSource)targetObject.getAnnotation(DataSource.class);
					
			if( dataSourceAnnotation != null ){
				
				String dataSourceName = dataSourceAnnotation.dataSource();

				MultiDataSource.setDataSourceKey( dataSourceName );
				
			}else{
		        	
		        MultiDataSource.setDataSourceKey("MASTER_POSTGRESQL");
			}
			
		}catch(Throwable e){
			
			throw e;
		}
    }
}
