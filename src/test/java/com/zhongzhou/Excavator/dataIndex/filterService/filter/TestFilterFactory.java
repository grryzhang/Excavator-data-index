package com.zhongzhou.Excavator.dataIndex.filterService.filter;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.web.context.support.XmlWebApplicationContext;

import com.zhongzhou.Excavator.dataIndex.DAO.mongo.WheelDAO;
import com.zhongzhou.Excavator.dataIndex.service.filterService.filters.Filter;
import com.zhongzhou.Excavator.dataIndex.service.filterService.filters.FilterFactory;
import com.zhongzhou.Excavator.dataIndex.model.item.wheel.Corporation;

public class TestFilterFactory {

	private static XmlWebApplicationContext  context;
	private static String[] configs = { "classpath:applicationContext.xml" }; 
	
	public static FilterFactory filterFactory;
	
	@BeforeClass  
	public static void configTest(){

		try {
			context = new XmlWebApplicationContext ();
			context.setConfigLocations(configs);
			
			context.refresh();
			
			filterFactory  = context.getBean( FilterFactory.class );	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testAll(){
		
		Filter<Corporation> testFilter = filterFactory.getFilter("wheel.corp.mainProduct");
		
		System.out.println( testFilter );
	}
}
