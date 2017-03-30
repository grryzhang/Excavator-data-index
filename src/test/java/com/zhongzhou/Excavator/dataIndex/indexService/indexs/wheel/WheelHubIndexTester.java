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

public class WheelHubIndexTester {

	private static XmlWebApplicationContext  context;
	private static String[] configs = { "classpath:applicationContext.xml" }; 
	
	public static HubDiameterIndex wheelIndexService;
	public static PCDIndex pcdIndex;
	
	@BeforeClass  
	public static void configTest(){

		try {
			context = new XmlWebApplicationContext ();
			context.setConfigLocations(configs);
			
			context.refresh();
			
			pcdIndex           = (PCDIndex)context.getBean(  PCDIndex.class );
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testAll(){
		
	}
	
	@Test
	public void testParserWheelHubDiameterData() throws Exception{
		
		 List<String> data = new ArrayList( Arrays.asList( "114.3" ) );
		 
		 Method method = PCDIndex.class.getDeclaredMethod("getMatchedPCD", new Class[]{ List.class });     
	     method.setAccessible(true);  
	     List<String> result = (List<String>)method.invoke( pcdIndex, data );  
	     
	     for( String oneMatched : result ){
	    	 System.out.println( oneMatched + "|" );
	     }

	     Assert.assertNotEquals( null, result );
	     Assert.assertEquals( true, result.size() > 0 );
	}
}
