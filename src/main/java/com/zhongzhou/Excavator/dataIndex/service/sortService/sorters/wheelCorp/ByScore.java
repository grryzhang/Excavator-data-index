package com.zhongzhou.Excavator.dataIndex.service.sortService.sorters.wheelCorp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.zhongzhou.Excavator.dataIndex.Exception.DataIndexException;
import com.zhongzhou.Excavator.dataIndex.model.item.wheel.Corporation;
import com.zhongzhou.Excavator.dataIndex.model.item.wheel.DataIndexedCorporation;
import com.zhongzhou.Excavator.dataIndex.service.sortService.sorters.Sorter;

public class ByScore implements Sorter<DataIndexedCorporation> {
	
	public static final String filterName = "wheel.corp.sort.byDefaultGrade";
	
	@Override
	public List<DataIndexedCorporation> sort( List<DataIndexedCorporation> sources ) throws DataIndexException {
		
		List<DataIndexedCorporation> sortResult = new ArrayList<DataIndexedCorporation>();

		try{
			
			for( DataIndexedCorporation source : sources ){
				
				source.setCorpScore ( this.calculateScore( source ) );
				
				sortResult.add( source );
			}

			Collections.sort( sortResult, new Comparator<DataIndexedCorporation>() {   

				@Override
				public int compare(DataIndexedCorporation o1, DataIndexedCorporation o2) {
			        return ( o1.getCorpScore() ).compareTo( o2.getCorpScore() );
				}
			}); 
			Collections.reverse( sortResult );

			return sortResult;
			
		}catch(Exception e){
			throw new DataIndexException( "Sorter Filter error." , e );
		}
	}
	
	
	
	private double calculateScore( DataIndexedCorporation pendingIndexedCorp ){
		
		double score = 0;
		
		if( pendingIndexedCorp.corp == null ) return 0;
		
		pendingIndexedCorp.scorExplain = new HashMap<String,Double>();
		
		if( pendingIndexedCorp.corp.getFactorySize() != null ){
			double itemScore = csFactorySize( pendingIndexedCorp.corp.getFactorySize() );
			score += itemScore;
			pendingIndexedCorp.scorExplain.put("csFactorySize", itemScore);
		}
		
		if( pendingIndexedCorp.corp.getYearEstablished() != null ){
			double itemScore = csYearEstablished( pendingIndexedCorp.corp.getYearEstablished() );
			score += itemScore;
			pendingIndexedCorp.scorExplain.put("csYearEstablished", itemScore);
		}
		
		if( pendingIndexedCorp.corp.getAnnualOutputValue() != null ){
			double itemScore =  csAnnualOutputValue( pendingIndexedCorp.corp.getAnnualOutputValue() );
			score += itemScore;
			pendingIndexedCorp.scorExplain.put("csAnnualOutputValue", itemScore);
		}else{
			
			if( pendingIndexedCorp.corp.getAnnualExportRevenue() != null ){
				double itemScore =  csAnnualOutputValue( pendingIndexedCorp.corp.getAnnualExportRevenue() );
				score += itemScore;
				pendingIndexedCorp.scorExplain.put("csAnnualOutputValue", itemScore);
			}
		}
		
		if( pendingIndexedCorp.corp.getHighestEverAnnualOutput() != null ){
			double itemScore =  csHighestEverAnnualOutput( pendingIndexedCorp.corp.getHighestEverAnnualOutput() );
			score += itemScore;
			pendingIndexedCorp.scorExplain.put("csHighestEverAnnualOutput", itemScore);
		}
		
		if( pendingIndexedCorp.corp.getNoOfProductionLines() != null ){
			double itemScore = csNoOfProductionLines( pendingIndexedCorp.corp.getNoOfProductionLines() );
			score += itemScore;
			pendingIndexedCorp.scorExplain.put("csNoOfProductionLines", itemScore);
		}
		
		return score;
	}
	
	private double csFactorySize( String factorySizeString ){
		
		double factorySize = 0;
		
		factorySizeString = factorySizeString.replace(",", "");
		
		String regex = "(\\d+\\.*\\d*)+\\W*[~-]\\W*(\\d+\\.*\\d*)+";
		Pattern between= Pattern.compile(regex);
		Matcher m = between.matcher( factorySizeString );	
		if( m.find() ) {
				
			Matcher m1 = Pattern.compile("(\\d+\\.*\\d*)+").matcher( m.group() );
			
			m1.find();
			double first  = Double.valueOf( m1.group() );
			m1.find();
			double second = Double.valueOf( m1.group() );

			factorySize = ( first + second ) / 2;
					
		}else{
			
			regex = "(\\d+\\.*\\d*)+";
			Pattern number= Pattern.compile(regex);
			
			m  = number.matcher( factorySizeString );
			
			if( m.find() ){
				factorySize = Double.valueOf( m.group() );
			}
		}
		
		
		if( factorySize < 20000 ){
			return 0.1;
		}else if( factorySize>=20000 && factorySize <= 50000 ){
			return 0.3;
		}else if( factorySize > 50000 ){
			return 0.6;
		}else{
			return 0;
		}
	}
	
	private double csYearEstablished( String yearEstablishedString ){
		
		int yearEstablished = 0;
		
		String regex = "(\\d+\\.*\\d*)+";
		Pattern number= Pattern.compile(regex);
		
		Matcher m = number.matcher( yearEstablishedString );
		
		if( m.find() ){
			yearEstablished = Integer.valueOf( m.group() );
		}
		
		if( yearEstablished > 2010 ){
			return 0.1;
		}else if( yearEstablished>=2000 && yearEstablished <= 2010 ){
			return 0.3;
		}else if( yearEstablished < 2000 ){
			return 0.6;
		}else{
			return 0;
		}
	}
	
	private double csAnnualOutputValue( String annualOutputValue ){
		
		annualOutputValue = annualOutputValue.replace(",", "");
		
		double annualOutput = 0;
			
		if( annualOutput == 0 ){
			String regex = "\\s*(\\d+\\.*\\d*)[A-Za-z\\s]*[~-][A-Za-z\\s]*\\s*(\\d+\\.*\\d*)+";
			Pattern between= Pattern.compile(regex);
			Matcher m = between.matcher( annualOutputValue );	
			
			if( m.find() ) {
					
				Matcher m1 = Pattern.compile("(\\d+\\.*\\d*)+").matcher( m.group() );
				
				m1.find();
				double first  = Double.valueOf( m1.group() );
				m1.find();
				double second = Double.valueOf( m1.group() );

				annualOutput = ( first + second ) / 2;
			}
		}
		
		if( annualOutput == 0 ){
			
			String regex = "(\\d+\\.*\\d*)\\s*[million|Million|MILLION]";
			Pattern dollar = Pattern.compile(regex);
			Matcher m = dollar.matcher( annualOutputValue );	
			
			if( m.find() ){
				
				Matcher m1 = Pattern.compile("(\\d+\\.*\\d*)+").matcher( m.group() );
				
				m1.find();
				annualOutput  = Double.valueOf( m1.group() );
			}
		}
		
		if( annualOutput == 0 ){

			Matcher m = Pattern.compile("(\\d+\\.*\\d*)+").matcher( annualOutputValue );
				
			while( m.find() ){
				
				double value =  Double.valueOf( m.group() );
				if( value > annualOutput ){
					annualOutput = value;
				}
			}
		}
			
		if( annualOutputValue.toLowerCase(Locale.ENGLISH).contains("million") ){
			annualOutput = annualOutput * 1000000;
		}else if( annualOutputValue.toLowerCase(Locale.ENGLISH).contains("billion") ){
			annualOutput = annualOutput * 1000000000;
		}else if( annualOutputValue.toLowerCase(Locale.ENGLISH).contains("thousand") ){
			annualOutput = annualOutput * 1000;
		}
		
		if( annualOutputValue.toLowerCase(Locale.ENGLISH).contains("rmb") || annualOutputValue.contains("¥") ) {
			annualOutput = annualOutput / 7;
		}
		
		if( annualOutput < 10000000 ){
			return 0.1;
		}else if( annualOutput>=10000000  && annualOutput <= 50000000 ){
			return 0.3;
		}else if( annualOutput > 50000000 ){
			return 0.6;
		}else{
			return 0;
		}
	}
	
	private double csHighestEverAnnualOutput( String highestEverAnnualOutputString ){
		
		highestEverAnnualOutputString = highestEverAnnualOutputString.replace(",", "");
		
		double highestEverAnnualOutput = 0;
		
		if( highestEverAnnualOutput == 0 ){
			String regex = "(\\d+\\.*\\d*)+";
			Pattern between= Pattern.compile(regex);
			Matcher m = between.matcher( highestEverAnnualOutputString );	
			
			if( m.find() ) {
					
				highestEverAnnualOutput  = Double.valueOf( m.group() );
			}
		}
		
		String  lowerCaseString = highestEverAnnualOutputString.toLowerCase(Locale.ENGLISH);
		if( lowerCaseString.contains("million") ){
			highestEverAnnualOutput = highestEverAnnualOutput * 1000000;
		}else if( lowerCaseString.contains("billion") ){
			highestEverAnnualOutput = highestEverAnnualOutput * 1000000000;
		}else if( lowerCaseString.contains("thousand") ){
			highestEverAnnualOutput = highestEverAnnualOutput * 1000;
		}else if( lowerCaseString.contains("万") ){
			highestEverAnnualOutput = highestEverAnnualOutput * 10000;
		}
		if( highestEverAnnualOutputString.contains("月") ){
			highestEverAnnualOutput = highestEverAnnualOutput * 12;
		}
		
		if( highestEverAnnualOutput < 1000000 ){
			return 0.1;
		}else if( highestEverAnnualOutput>=1000000  && highestEverAnnualOutput <= 3000000 ){
			return 0.3;
		}else if( highestEverAnnualOutput > 3000000 ){
			return 0.6;
		}else{
			return 0;
		}
	}
	
	private double csNoOfProductionLines( String noOfProductionLinesString ){
		
		noOfProductionLinesString = noOfProductionLinesString.replace(",", "");
		
		double noOfProductionLines = 0;
		
		if( noOfProductionLines == 0 ){
			String regex = "(\\d+\\.*\\d*)+";
			Pattern number = Pattern.compile(regex);
			Matcher m = number.matcher( noOfProductionLinesString );	
			
			if( m.find() ) {
					
				noOfProductionLines  = Double.valueOf( m.group() );
			}
		}
		
		if( noOfProductionLines < 5 ){
			return 0.1;
		}else if( noOfProductionLines>=5  && noOfProductionLines <= 10 ){
			return 0.3;
		}else if( noOfProductionLines > 10 ){
			return 0.6;
		}else{
			return 0;
		}
	}
}
