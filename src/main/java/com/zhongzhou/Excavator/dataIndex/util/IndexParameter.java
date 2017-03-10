package com.zhongzhou.Excavator.dataIndex.util;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.zhongzhou.common.util.BeanUtil;

public class IndexParameter {

	public static String indexCreateParameter2String( Object object ) throws JsonGenerationException, JsonMappingException, IOException, IllegalArgumentException, IllegalAccessException{
		
		Class targetClass = object.getClass();
		
		List<String> exceptFieldName = new ArrayList<String>();
		String filterName = null;
			
		Field[] fieldlist = targetClass.getDeclaredFields();
		
		for( Field field : fieldlist ){
			
			if( field.isAnnotationPresent(isShowCondition.class) ){
				
				field.setAccessible( true );
				
				Object value = field.get( object );
				
				if( value == null ){
					exceptFieldName.add( field.getName() );
				}
			}else{
				
				exceptFieldName.add( field.getName() );
			}
		}	
		
		String[] expectFields = new String[ exceptFieldName.size() ];
		exceptFieldName.toArray( expectFields );
		
		byte[] resultString =BeanUtil.beanJaksonSerializeAllExcept( object , expectFields );
		
		return new String( resultString , "UTF-8" );
	}
	
	@Documented
	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.FIELD)
	@Inherited
	public @interface isShowCondition {
	}
}
