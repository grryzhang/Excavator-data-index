package com.zhongzhou.Excavator.dataIndex.service.filterService.filters.wheelCorp;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.zhongzhou.Excavator.dataIndex.Exception.DataIndexException;
import com.zhongzhou.Excavator.dataIndex.service.filterService.filters.Filter;
import com.zhongzhou.Excavator.dataIndex.model.item.wheel.Corporation;
import com.zhongzhou.Excavator.dataIndex.model.item.wheel.DataIndexedCorporation;
import com.zhongzhou.common.util.BeanUtil;

public class MainProductsFilter implements Filter<DataIndexedCorporation> {
	
	public static final String filterName = "wheel.corp.mainProduct";
	
	@Override
	public List<DataIndexedCorporation> filter( List<DataIndexedCorporation> sources ) throws DataIndexException {

		String mainProductsFilter = ""
				+ "[\"Alloy wheel\",\"Steel wheel\",\"steel rim\",\"wire wheel\",\"trailer wheel\","
				+ "\"steel wheel rims\",\"trailer wheel rims\",\"wheel/rim\",\"Forklift Wheel\",\"Tubeless Wheel\", "
				+ "\"Tractor Wheel\",\"Wheel Rim\",\"Wheel Hub\",\"Automobile Wheel Hub\"]";
		
		try{
			List<String> mainProductsFilters = BeanUtil.beanJaksonUnSerializer( mainProductsFilter.getBytes(), List.class );
			
			List<DataIndexedCorporation> tmp = new ArrayList<DataIndexedCorporation>();
			
			for( DataIndexedCorporation source : sources ){
				
				if( source.corp != null ){
					
					boolean matched = false;
					for( String filter : mainProductsFilters ){
						
						if( source.corp.getMainProducts() != null ){
							
							if( source.corp.getMainProducts()
									.toLowerCase( Locale.ENGLISH )
									.indexOf( filter.toLowerCase( Locale.ENGLISH ) ) >=0 ){
								
								matched = true;
								break;
							}
						}
					}
					
					if( matched ){
						
						tmp.add( source );
					}
				}
			}
			
			return tmp;
			
		}catch(Exception e){
			throw new DataIndexException( "Json Filter paser error." , e );
		}
	}

}
