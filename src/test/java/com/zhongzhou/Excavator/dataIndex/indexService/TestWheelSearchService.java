package com.zhongzhou.Excavator.dataIndex.indexService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.web.context.support.XmlWebApplicationContext;

import com.zhongzhou.Excavator.dataIndex.model.item.wheel.Corporation;
import com.zhongzhou.Excavator.dataIndex.model.item.wheel.DataIndexedCorporation;
import com.zhongzhou.Excavator.dataIndex.model.item.wheel.IndexCreateParameters;

public class TestWheelSearchService {
	
	private static XmlWebApplicationContext  context;
	private static String[] configs = { "classpath:applicationContext.xml" }; 
	
	public static WheelIndexService wheelSearchService;
	
	@BeforeClass  
	public static void configTest(){

		try {
			context = new XmlWebApplicationContext ();
			context.setConfigLocations(configs);
			
			context.refresh();
			
			wheelSearchService  = context.getBean(  WheelIndexService.class );
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testAll(){
		
		this.testGetSuppliersOfMatchedWheel();
	}
	
	public void testGetSuppliersOfMatchedWheel(){
		
		IndexCreateParameters searchParameters = new IndexCreateParameters();
		searchParameters.hubDiameter = new ArrayList<String>( Arrays.asList("17") );
		searchParameters.PCD = new ArrayList<String>( Arrays.asList("114.3") );
		
		List<DataIndexedCorporation> result = wheelSearchService.indexSuppliersOfMatchedWheel("indexID",searchParameters);
		
		for( DataIndexedCorporation dataIndexCorp : result ){
			System.out.println( dataIndexCorp.corp.getName() + "|--|" +  dataIndexCorp.corp.getId() + "|--|" + dataIndexCorp.corpScore );
		}
	}
}
