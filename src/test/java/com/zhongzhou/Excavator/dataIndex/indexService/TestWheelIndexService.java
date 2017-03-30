package com.zhongzhou.Excavator.dataIndex.indexService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.web.context.support.XmlWebApplicationContext;

import com.zhongzhou.Excavator.dataIndex.DAO.mongo.WheelDAO;
import com.zhongzhou.Excavator.dataIndex.service.indexService.WheelIndexService;
import com.zhongzhou.Excavator.dataIndex.service.indexService.indexs.wheel.HubDiameterIndex;
import com.zhongzhou.Excavator.dataIndex.service.indexService.indexs.wheel.PCDIndex;
import com.zhongzhou.Excavator.dataIndex.model.WebDataMongoData;
import com.zhongzhou.Excavator.dataIndex.model.item.wheel.IndexCreateParameters;
import com.zhongzhou.Excavator.dataIndex.model.item.wheel.SourceSearchParameters;
import com.zhongzhou.Excavator.dataIndex.model.item.wheel.Wheel;
import com.zhongzhou.Excavator.springsupport.injectlist.ServiceNameList;

public class TestWheelIndexService {

	private static XmlWebApplicationContext  context;
	private static String[] configs = { "classpath:applicationContext.xml" }; 
	
	public static HubDiameterIndex hubDiameterIndexService;
	public static PCDIndex pcdIndex;
	
	public static WheelDAO wheelDAO;
	
	public static WheelIndexService wheelIndexService;
	
	@BeforeClass  
	public static void configTest(){

		try {
			context = new XmlWebApplicationContext ();
			context.setConfigLocations(configs);
			
			context.refresh();
			
			wheelIndexService = context.getBean( WheelIndexService.class );
			
			hubDiameterIndexService  = (HubDiameterIndex)context.getBean(  HubDiameterIndex.class );
			pcdIndex           = (PCDIndex)context.getBean(  PCDIndex.class );
			
			wheelDAO           = (WheelDAO)context.getBean(  WheelDAO.class );
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testAll(){
		
		this.testIndex();
		//this.testWheelPCDIndex();
		//this.testWheelHubDiameterIndex();
	}
	
	public void testIndex(){
		
		/*
		List<String> material = 
			new ArrayList<String>( Arrays.asList("alloy","aluminum") );
			
			List<String> material = 
				new ArrayList<String>( Arrays.asList("steel","iron") );
		
		IndexCreateParameters createParameters = new IndexCreateParameters();
		createParameters.material = material;
		*/
		
		List<String> hubDiameterMatched = new ArrayList<String>( Arrays.asList("0-18") );
		List<String> material = new ArrayList<String>( Arrays.asList("steel","iron") );
		List<String> PCD = new ArrayList<String>( Arrays.asList( "0-180" ) );
	
		IndexCreateParameters createParameters = new IndexCreateParameters();
		createParameters.hubDiameter = hubDiameterMatched;
		createParameters.material = material;
		createParameters.PCD = PCD;
		
		//wheel-aluminum-index
		//wheel-steel-index
		wheelIndexService.indexSuppliersOfMatchedWheel("wheel-steel-index", createParameters);
	}
}
