package com.zhongzhou.Excavator.dataIndex.service.indexService.indexs.wheel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhongzhou.Excavator.dataIndex.DAO.mongo.WheelDAO;
import com.zhongzhou.Excavator.dataIndex.service.indexService.indexs.wheel.HubDiameterIndex.WheelHubDiameterParseredData;

@Service
public class PCDIndex {
	
	@Autowired
	WheelDAO wheelDAO;

	public List<String> getMatchedPCD( List<String> conditions ){
		
		
		
		List<String> matched = new ArrayList<String>();
		List<String> notMatch = new ArrayList<String>();
		
		if( conditions != null ){
			
			List<String> distinctValue = wheelDAO.getFieldDistinct("data.pcd");
			
			for( String condition : conditions ){
				
				if( condition != null && condition.length() > 0 ){
					for( String existedData : distinctValue ){
						
						if( this.isMatch(condition, existedData) ){
								
							matched.add( existedData );
						} 
					}
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
	
	private boolean isMatch( String pendingCompare , String existedData  ){
		
		//numberReg = "(\\d+\\.*\\d*)";
		
		double pendingCompareDouble = Double.parseDouble( pendingCompare );
		boolean matched = true;
		
		// parser content like 12
		String regex = "(\\d+\\.*\\d*)+";
		Pattern number= Pattern.compile(regex);
		// has x
		regex = "(\\d+\\.*\\d*)+\\s*[RXx\\*]\\s*(\\d+\\.*\\d*)+";
		Pattern hasX= Pattern.compile(regex);
		// parser content like 12-13, 12~13, 12/13
		regex = "(\\d+\\.*\\d*)+\\W*[~-]\\W*(\\d+\\.*\\d*)+";
		Pattern between= Pattern.compile(regex);
		
		StringBuffer temp = new StringBuffer( existedData );
		
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
			
		//between compare
		m = between.matcher( temp.toString() );	
		while ( m.find() ) {
				
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
				
				if( Double.compare( pendingCompareDouble ,  first ) > 0 &&  Double.compare( pendingCompareDouble ,  second ) < 0  ){
					return matched;
				}
			}
		}	
		//
		m = number.matcher( temp.toString() );	
		while ( m.find() ) {
			Double oneExisted =  Double.parseDouble( m.group() );
			if( Double.compare( pendingCompareDouble ,  oneExisted ) == 0 ){
				return matched;
			}
		}
		
		return false;
	}
}
