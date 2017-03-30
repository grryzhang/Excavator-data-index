package com.zhongzhou.Excavator.dataIndex.service.indexService.indexs.wheel;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhongzhou.Excavator.dataIndex.DAO.mongo.WheelDAO;
@Service
public class PCDIndex {
	
	@Autowired
	WheelDAO wheelDAO;

	public List<String> getMatchedPCD( List<String> conditions ){

		List<String> matched = new ArrayList<String>();
		List<String> notMatch = new ArrayList<String>();
		
		if( conditions != null ){
			
			List<String> distinctValue = wheelDAO.getFieldDistinct("data.pcd");
			
			List<PCDParseredData> parsedContent     = this.parserPendingCompareData( distinctValue );
			List<Condition>       parsedConditions  = this.parserCondition(conditions);
			
			for( PCDParseredData parseredData : parsedContent ){
				
				if( this.isMatch(parsedConditions, parseredData ) ){
					
					matched.add( parseredData.originalData );
					continue;
				}  		
			}
		}
		/*
		for(String one : distinctValue ){
			
			if( !matched.contains( one ) ){
				notMatch.add( one );
				//System.out.println(one);
			}
		}
		*/
		
		return matched;
	}
	
	//compare, compare if item's hubDiameterData is match the input condition
	private boolean isMatch( List<Condition> conditions , PCDParseredData parsedData ){
		
		if( parsedData != null &&  parsedData.parseredData != null ){
			
			for( Condition condition : conditions ){
				
				if( condition!= null && condition.start != null && condition.end != null ){
					
					for( ParseredData parseredData : parsedData.parseredData ){
						
						if( parseredData != null && parseredData.start != null && parseredData.end != null ){
							
							if( condition.start.compareTo( parseredData.end ) <= 0 && condition.end.compareTo( parseredData.start ) >=0 ){
								return true;
							}
						}
					}
				}
			}
		}
		
		return false;
	}	
	
	private List<Condition> parserCondition( List<String> conditions ){
		
		List<Condition> parseredConditions = new ArrayList<Condition>();
		
		String regex = "(\\d+\\.*\\d*)+";
		Pattern number= Pattern.compile(regex);
		
		regex = "(\\d+\\.*\\d*)+\\W*[~-]\\W*(\\d+\\.*\\d*)+";
		Pattern between= Pattern.compile(regex);
		
		for( String condition : conditions ){
			
			StringBuffer temp = new StringBuffer( condition );
			
			Matcher m = between.matcher( condition );
			while ( m.find() ) {
					
				Matcher m1 = Pattern.compile("(\\d+\\.*\\d*)+").matcher( m.group() );
				
				int count = 0 ;
				BigDecimal first  = null ;
				BigDecimal second = null ;
				while( m1.find() ){
					if( count == 0 ) first  = new BigDecimal( m1.group() );
					if( count == 1 ) second = new BigDecimal( m1.group() );
					count++;
				};
				if( count == 2 && first != null && second != null ){
					
					if( first.compareTo( second ) > 0 ){ BigDecimal third = first; first = second; second = third; };
				}
				Condition oneParseredCondition = new Condition();
				oneParseredCondition.start = first;
				oneParseredCondition.end   = second;
				parseredConditions.add( oneParseredCondition );
				temp = temp.replace( m.start(), m.end() , " " );
				m = between.matcher( temp ); // temp has changed, match again
			}
			
			m = number.matcher( temp.toString() );	
			while ( m.find() ) {
				BigDecimal first = new BigDecimal( m.group() );
				Condition oneParseredCondition = new Condition();
				oneParseredCondition.start = first;
				oneParseredCondition.end   = first;
				parseredConditions.add( oneParseredCondition );
			}			
		}
		
		return parseredConditions;
	}
	
	private List<PCDParseredData> parserPendingCompareData( List<String> pendingCompare  ){
		
		List<PCDParseredData> parsedContent = new ArrayList<PCDParseredData>();
		
		//numberReg = "(\\d+\\.*\\d*)";
		
		boolean matched = true;
		
		// parser content like 12
		String regex = "(\\d+\\.*\\d*)+";
		Pattern number= Pattern.compile(regex);
		// has x
		regex = "(\\d+\\.*\\d*)+\\s*[RXx×\\*]\\s*(\\d+\\.*\\d*)+";
		Pattern hasX= Pattern.compile(regex);
		// parser content like 12-13, 12~13, 12/13
		regex = "(\\d+\\.*\\d*)+\\W*[~-]\\W*(\\d+\\.*\\d*)+";
		Pattern between= Pattern.compile(regex);
		
		for( String value : pendingCompare ){
			
			PCDParseredData parseredDataObj = new PCDParseredData();
			parseredDataObj.parseredData = new ArrayList<ParseredData>();
			parseredDataObj.originalData = value;
			parsedContent.add( parseredDataObj );
			
			StringBuffer temp = new StringBuffer( value );
			
			// replace like 18X2 to 18, 2X19 to 19
			Matcher m = hasX.matcher( temp );
			while( m.find() ){
				
				Matcher m1 = Pattern.compile("(\\d+\\.*\\d*)+").matcher( m.group() );
				
				int count = 0 ;
				double first  = 0 ;
				double second = 0 ;
				while( m1.find() ){
					if( count == 0 ) first  = Double.valueOf( m1.group() ).doubleValue();
					if( count == 1 ) second = Double.valueOf( m1.group() ).doubleValue();
					count++;
				};
				if( count == 2 && first != 0 ){
					
					if( first > second ){ double third = first; first = second; second = third; };
				}
				temp = temp.replace( m.start(), m.end() , String.valueOf( second ) );
				m = hasX.matcher( temp );
			}	
			
			temp = new StringBuffer( temp.toString().replaceAll("[A-Za-z]", " ") );
			
			//between parser
			m = between.matcher( temp.toString() );	
			while ( m.find() ) {
					
				Matcher m1 = Pattern.compile("(\\d+\\.*\\d*)+").matcher( m.group() );
				
				int count = 0 ;
				BigDecimal first  = null ;
				BigDecimal second = null ;
				while( m1.find() ){
					if( count == 0 ) first  = new BigDecimal( m1.group() );
					if( count == 1 ) second = new BigDecimal( m1.group() );
					count++;
				};
				if( count == 2 && first != null && second != null ){
					
					if( first.compareTo( second ) > 0 ){ BigDecimal third = first; first = second; second = third; };
				}
				ParseredData oneParseredData = new ParseredData();
				oneParseredData.start = first;
				oneParseredData.end = second;
				parseredDataObj.parseredData.add( oneParseredData );
				temp = temp.replace( m.start(), m.end() , " " );
				m = between.matcher( temp ); // temp has changed, match again
			}
				
			// parse the rest number
			m = number.matcher( temp.toString() );	
			while ( m.find() ) {
				BigDecimal first = new BigDecimal( m.group() );
				ParseredData oneParseredData = new ParseredData();
				oneParseredData.start = first;
				oneParseredData.end = first;
				parseredDataObj.parseredData.add( oneParseredData );
			}	
		}

		return parsedContent;
	}
	
	private class Condition{
		
		public BigDecimal start;
		public BigDecimal end;
	}
	
	private class ParseredData{
		public BigDecimal start;
		public BigDecimal end;
	}
	
	private class PCDParseredData {
		public String originalData;
		public List<ParseredData> parseredData ;
	}
}
