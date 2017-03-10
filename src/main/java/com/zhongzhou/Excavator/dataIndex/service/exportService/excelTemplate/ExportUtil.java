package com.zhongzhou.Excavator.dataIndex.service.exportService.excelTemplate;

import java.lang.reflect.Field;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.zhongzhou.Excavator.dataIndex.Exception.ExportTemplateException;

public class ExportUtil {
	
	public static String getFirstMatch(String content , String regex ){
		
		Pattern reger= Pattern.compile(regex);
		Matcher m = reger.matcher( content );
		while(m.find()){
			return m.group();
		}
		
		return null;
	}
	
	public static String replaceFirst( String content , String regex , String replacement ){
		
		Pattern reger= Pattern.compile(regex);
		Matcher m = reger.matcher( content );
		return m.replaceFirst(replacement);
	}

	public static Object getData( Object data , String pathDescription ) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException{
		
		if( data == null ) return null;
		if( pathDescription == null ) return data;
		
		String[] paths = pathDescription.split("\\.");
		
		Object targetData = data;
		for( String path : paths ){
			targetData = getFieldData( targetData, path );
		}
		
		return targetData;
	}
	
	private static Object getFieldData( Object data, String fieldName ) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException{
		
		if( data == null ) return null;
		if( fieldName == null || fieldName.length() < 1 ) return data;
		
		Field field = data.getClass().getDeclaredField(fieldName);
		if( field != null ){
			field.setAccessible( true );
			Object value = field.get( data );
	        if( value !=null ){
	            return value;
	        }
		}	

        return null;
	}
}
