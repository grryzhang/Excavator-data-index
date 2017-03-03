package com.zhongzhou.Excavator.dataIndex.indexService.indexs.wheel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhongzhou.Excavator.dataIndex.DAO.mongo.WheelDAO;

@Service
public class HubDiameterIndex {
	
	@Autowired
	WheelDAO wheelDAO;

	public List<String> getMatchedWheelHubDiameter( List<String> conditions ){
		
		List<String> distinctValue = wheelDAO.getFieldDistinct("data.wheelHubDiameter");
		
		//List<String> distinctValue = new ArrayList<String>( Arrays.asList("12\"-26\"") );
		
		List<String> matched = new ArrayList<String>();
		List<String> notMatch = new ArrayList<String>();
		
		List<WheelHubDiameterParseredData> parsedContent = this.parserWheelHubDiameterData( distinctValue );
		
		for( String condition : conditions ){
			
			for( WheelHubDiameterParseredData parseredData : parsedContent ){
				
				for( String data : parseredData.parseredData  ){
					
					if( data.equals( condition ) ){
						
						matched.add( parseredData.originalData );
						break;
					}
				}  
			}
		}
		
		for(String one : distinctValue ){
			
			if( !matched.contains( one ) ){
				notMatch.add( one );
				//System.out.println(one);
			}
		}
		
		List<String> result = new ArrayList( new HashSet( matched ) );
		
		return result;
	}
	
	//将数据库的数据切割成可以比较的数据
	private List<WheelHubDiameterParseredData> parserWheelHubDiameterData( List<String> data ){
		
		List<WheelHubDiameterParseredData> parsedContent = new ArrayList<WheelHubDiameterParseredData>();
		
		//numberReg = "(\\d+\\.*\\d*)";
		
		// parser content like 12
		String regex = "(\\d+\\.*\\d*)+";
		Pattern number= Pattern.compile(regex);
		// has x
		regex = "(\\d+\\.*\\d*)+\\s*[RXx\\*]\\s*(\\d+\\.*\\d*)+";
		Pattern hasX= Pattern.compile(regex);
		// parser content like 12-13, 12~13, 12/13
		regex = "(\\d+\\.*\\d*)+\\W*[~-]\\W*(\\d+\\.*\\d*)+";
		Pattern between= Pattern.compile(regex);
		
		for( String value : data ){
			
			StringBuffer parseredData = new StringBuffer("");
			
			WheelHubDiameterParseredData parseredDataObj = new WheelHubDiameterParseredData();
			parseredDataObj.originalData = value;
			parsedContent.add( parseredDataObj );
			
			StringBuffer temp = new StringBuffer( value );
			
			// replace like 18X2 to 18, 2X19 to 19
			Matcher m = hasX.matcher( temp );
			while( m.find() ){
				
				Matcher m1 = Pattern.compile("(\\d+\\.*\\d*)+").matcher( m.group() );
				
				int count = 0 ;
				int first  = 0 ;
				int second = 0 ;
				while( m1.find() ){
					if( count == 0 ) first  = Float.valueOf( m1.group() ).intValue();
					if( count == 1 ) second = Float.valueOf( m1.group() ).intValue();
					count++;
				};
				if( count == 2 && first != 0 ){
					
					if( first > second ){ int third = first; first = second; second = third; };
				}
				temp = temp.replace( m.start(), m.end() , String.valueOf( second ) );
				m = hasX.matcher( temp );
			}
			
			temp = new StringBuffer( temp.toString().replaceAll("[A-Za-z]", " ") );
			
			m = between.matcher( temp.toString() );	
			while ( m.find() ) {
					
				Matcher m1 = Pattern.compile("(\\d+\\.*\\d*)+").matcher( m.group() );
				
				int count = 0 ;
				int first  = 0 ;
				int second = 0 ;
				while( m1.find() ){
					if( count == 0 ) first  = Float.valueOf( m1.group() ).intValue();
					if( count == 1 ) second = Float.valueOf( m1.group() ).intValue();
					count++;
				};
				if( count == 2 && first != 0 ){
					
					if( first > second ){ int third = first; first = second; second = third; };
					
					int third = first;
					while( third <= second ){
						parseredData.append( String.valueOf( third ) );
						parseredData.append( "," );
						third ++;
					}
				}
				temp = temp.replace( m.start(), m.end() , " " );
				m = between.matcher( temp.toString() );
			}
			
			m = number.matcher( temp.toString() );	
			while ( m.find() ) {
				parseredData.append( m.group() );
				parseredData.append( "," );
			}
			
			parseredDataObj.parseredData = new ArrayList<String>();
			m = number.matcher( parseredData.toString() );	
			while ( m.find() ) {
				parseredDataObj.parseredData.add( m.group() );
			}
		}
		
		return parsedContent;
	}
	
	
	public class WheelHubDiameterParseredData {
		
		public String originalData;
		public List<String> parseredData ;
	}
	
}
