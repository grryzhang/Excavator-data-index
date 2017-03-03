package com.zhongzhou.Excavator.dataIndex.sortService.sorter.wheelCorp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.web.context.support.XmlWebApplicationContext;

import com.zhongzhou.Excavator.dataIndex.DAO.mongo.CorporationDAO;
import com.zhongzhou.Excavator.dataIndex.DAO.mongo.MinerTrackDAO;
import com.zhongzhou.Excavator.dataIndex.model.item.wheel.Corporation;
import com.zhongzhou.Excavator.dataIndex.model.item.wheel.CorporationSearchParameters;
import com.zhongzhou.Excavator.dataIndex.model.item.wheel.DataIndexedCorporation;
import com.zhongzhou.Excavator.dataIndex.model.item.wheel.TrackSearchParameters;
import com.zhongzhou.Excavator.dataIndex.model.item.wheel.WebDataCorporation;
import com.zhongzhou.Excavator.dataIndex.model.item.wheel.WebDataMinerTrack;
import com.zhongzhou.Excavator.dataIndex.sortService.sorters.Sorter;
import com.zhongzhou.Excavator.dataIndex.sortService.sorters.SorterFactory;

public class TestByScore {

	private static XmlWebApplicationContext  context;
	private static String[] configs = { "classpath:applicationContext.xml" }; 
	
	public static CorporationDAO corporationDAO;
	public static SorterFactory sorterFactory;
	
	@BeforeClass  
	public static void configTest(){

		try {
			context = new XmlWebApplicationContext ();
			context.setConfigLocations(configs);
			
			context.refresh();
			
			corporationDAO  = context.getBean( CorporationDAO.class );	
			sorterFactory  = context.getBean( SorterFactory.class );	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testAll(){
		
		testCalculateScore();
	}
	
	public void testCalculateScore(){
		
		CorporationSearchParameters searchParameters = new CorporationSearchParameters();
		searchParameters.resourceUrls = new ArrayList( 
			Arrays.asList( 
				"https://cnpowcan.en.alibaba.com/company_profile.html#top-nav-bar"
			) 
		);

		List<WebDataCorporation> result = corporationDAO.getCorporationData(searchParameters);
		
		List<DataIndexedCorporation> corporations = new ArrayList<DataIndexedCorporation>();
		
		for( WebDataCorporation webDataCorp : result ){
			
			DataIndexedCorporation dataIndexCorp = new DataIndexedCorporation();
			dataIndexCorp.corp = webDataCorp.getData();
			corporations.add( dataIndexCorp );
		}
		
		Sorter<DataIndexedCorporation> sorter = sorterFactory.getSorter("wheel.corp.sort.byDefaultGrade");
		
		corporations = sorter.sort(corporations);
		
		for( DataIndexedCorporation dataIndexCorp : corporations ){
			System.out.println( dataIndexCorp.corp.getName() + ":" + dataIndexCorp.corpScore );
			
			if( dataIndexCorp.scorExplain != null ){
				for( String key : dataIndexCorp.scorExplain.keySet() ){
					System.out.println( "      " + key + ":" + + dataIndexCorp.scorExplain.get(key) );
				}
			}
			
			System.out.println( "----------------------------------------" );
		}
	}
}
