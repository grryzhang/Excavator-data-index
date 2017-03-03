package com.zhongzhou.Excavator.Annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotated at com.zhongzhou.Excavator.DAO file
 * <br>Used by com.zhongzhou.Excavator.springsupport.MultiDataSourceAspect
 * 
 * <br>Used to define which datasource the DAO file should use
 * @author Grry Zhang
 *
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
public @interface DataSource {
	
	public String dataSource();
}
