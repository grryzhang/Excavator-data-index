package com.zhongzhou.Excavator.dataIndex.DAO.mongo.item;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.web.context.support.XmlWebApplicationContext;

import com.zhongzhou.Excavator.dataIndex.DAO.mongo.MinerTrackDAO;
import com.zhongzhou.Excavator.dataIndex.model.WebDataMongoData;
import com.zhongzhou.Excavator.dataIndex.model.item.wheel.TrackSearchParameters;
import com.zhongzhou.Excavator.dataIndex.model.item.wheel.WebDataMinerTrack;

public class TestMinerTrackDAO {

	private static XmlWebApplicationContext  context;
	private static String[] configs = { "classpath:applicationContext.xml" }; 
	
	public static MinerTrackDAO minerTrackDAO;
	
	@BeforeClass  
	public static void configTest(){

		try {
			context = new XmlWebApplicationContext ();
			context.setConfigLocations(configs);
			
			context.refresh();
			
			minerTrackDAO  = context.getBean( MinerTrackDAO.class );	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testAll(){
		
		testGetWheelDataTrack();
	}
	
	public void testGetWheelDataTrack(){
		
		TrackSearchParameters searchParameters = new TrackSearchParameters();
		searchParameters.productUrls = new ArrayList( Arrays.asList( "https://www.alibaba.com/product-detail/Sports-car-steel-wheel-15x8-offroad_60562361342.html" ) );

		List<WebDataMinerTrack> result = minerTrackDAO.getWheelDataTrack(searchParameters);
		
		System.out.println( result.size() );
	}
}
