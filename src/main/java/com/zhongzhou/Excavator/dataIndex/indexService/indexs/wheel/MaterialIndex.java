package com.zhongzhou.Excavator.dataIndex.indexService.indexs.wheel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhongzhou.Excavator.dataIndex.DAO.mongo.WheelDAO;
import com.zhongzhou.Excavator.dataIndex.indexService.indexs.wheel.HubDiameterIndex.WheelHubDiameterParseredData;

@Service
public class MaterialIndex {
	
	@Autowired
	WheelDAO wheelDAO;

	public List<String> getMatchedMaterial( List<String> materialConditions ){
		
		List<String> distinctValue = wheelDAO.getFieldDistinct("data.material");
		
		List<String> matched = new ArrayList<String>();

		for( String existedData : distinctValue ){
			
			for( String condition : materialConditions ){
			
				if( this.isMatch(condition, existedData) ){
					
					matched.add( existedData );
				} 
			}
		}	

		return matched;
	}
	
	private boolean isMatch( String pendingCompare , String existedData  ){
		
		String tempExistedData    = existedData.toLowerCase();
		String tempPendingCompare = pendingCompare.toLowerCase();
		
		if( tempExistedData.contains( tempPendingCompare ) ){
			
			return true;
		}
		
		return false;
	}
}
