package com.zhongzhou.Excavator.dataIndex.indexService.indexs.wheel;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.web.context.support.XmlWebApplicationContext;

import com.zhongzhou.Excavator.dataIndex.DAO.mongo.WheelDAO;
import com.zhongzhou.Excavator.dataIndex.service.indexService.indexs.wheel.HubDiameterIndex;
import com.zhongzhou.Excavator.dataIndex.service.indexService.indexs.wheel.PCDIndex;
import com.zhongzhou.Excavator.dataIndex.model.WebDataMongoData;
import com.zhongzhou.Excavator.dataIndex.model.item.wheel.SourceSearchParameters;
import com.zhongzhou.Excavator.dataIndex.model.item.wheel.Wheel;
import com.zhongzhou.Excavator.springsupport.injectlist.ServiceNameList;

public class TestPCDIndex {

	private static XmlWebApplicationContext  context;
	private static String[] configs = { "classpath:applicationContext.xml" }; 
	
	public static HubDiameterIndex wheelIndexService;
	
	@BeforeClass  
	public static void configTest(){

		try {
			context = new XmlWebApplicationContext ();
			context.setConfigLocations(configs);
			
			context.refresh();
			
			wheelIndexService = (HubDiameterIndex)context.getBean(  HubDiameterIndex.class );
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testAll(){
		
	}
	
	@Test
	public void testParserWheelHubDiameterData() throws Exception{
		
		 List<String> conditions = new ArrayList( Arrays.asList( "0-18" ) );

		 List<String> pendingCompaireData = new ArrayList( Arrays.asList( "19.5×6.75" ) );
		 
		 
		 List<String> result = wheelIndexService.getMatchedWheelHubDiameter(conditions, pendingCompaireData);
	     
	     for( String oneMatched : result ){
	    	 System.out.println( oneMatched + "|" );
	     }

	     //Assert.assertNotEquals( null, result );
	     //Assert.assertEquals( true, result.size() <= 0 );
	}
}
