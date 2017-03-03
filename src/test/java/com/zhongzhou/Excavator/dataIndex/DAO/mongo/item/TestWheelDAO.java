package com.zhongzhou.Excavator.dataIndex.DAO.mongo.item;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.web.context.support.XmlWebApplicationContext;

import com.zhongzhou.Excavator.dataIndex.DAO.mongo.WheelDAO;
import com.zhongzhou.Excavator.dataIndex.model.WebDataMongoData;
import com.zhongzhou.Excavator.dataIndex.model.item.wheel.SourceSearchParameters;
import com.zhongzhou.Excavator.dataIndex.model.item.wheel.Wheel;
import com.zhongzhou.Excavator.springsupport.injectlist.ServiceNameList;

public class TestWheelDAO {

	private static XmlWebApplicationContext  context;
	private static String[] configs = { "classpath:applicationContext.xml" }; 
	
	public static WheelDAO wheelDAO;
	
	@BeforeClass  
	public static void configTest(){

		try {
			context = new XmlWebApplicationContext ();
			context.setConfigLocations(configs);
			
			context.refresh();
			
			wheelDAO  = context.getBean( WheelDAO.class );	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testAll(){
		//this.testCountWheelData();
		this.testGetWheelData();
	}
	
	
	public void testCountWheelData(){
		
		SourceSearchParameters searchParameters = new SourceSearchParameters();
		
		long count = wheelDAO.countWheelData(searchParameters);
			
		System.out.println( count );
	}
	
	public void testGetWheelData(){
		
		SourceSearchParameters searchParameters = new SourceSearchParameters();
		searchParameters.wheelHubDiameter = new ArrayList<String>( Arrays.asList("11-15 Inch, 13,14 inch","13-22 INCH") );
		
		List< WebDataMongoData<Wheel> > result = wheelDAO.getWheelData(searchParameters);
			
		for( WebDataMongoData<Wheel> oneData : result  ){
			
			System.out.println( oneData.getData() );
			System.out.println( oneData.getData().getName() );
		}
	}
}
