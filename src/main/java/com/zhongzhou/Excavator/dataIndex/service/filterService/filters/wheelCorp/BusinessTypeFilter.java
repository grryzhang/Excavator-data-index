package com.zhongzhou.Excavator.dataIndex.service.filterService.filters.wheelCorp;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.zhongzhou.Excavator.dataIndex.Exception.DataIndexException;
import com.zhongzhou.Excavator.dataIndex.model.item.wheel.DataIndexedCorporation;
import com.zhongzhou.Excavator.dataIndex.service.filterService.filters.Filter;
import com.zhongzhou.common.util.BeanUtil;

public class BusinessTypeFilter implements Filter<DataIndexedCorporation> {
public static final String filterName = "wheel.corp.businessType";
	
	@Override
	public List<DataIndexedCorporation> filter( List<DataIndexedCorporation> sources ) throws DataIndexException {

		String mustHasFilter = "[\"Manufacturer\",\"Factory\",\"Manufacture\"]";
		
		String notHasFitler = "[\"Trading\",\"Agent\"]";
		
		try{
			List<String> mustHas = BeanUtil.beanJaksonUnSerializer( mustHasFilter.getBytes(), List.class );
			
			List<String> notHas  = BeanUtil.beanJaksonUnSerializer( notHasFitler.getBytes(), List.class );
			
			List<DataIndexedCorporation> tmp = new ArrayList<DataIndexedCorporation>();
			
			match : for( DataIndexedCorporation source : sources ){
				
				if( source.corp != null ){
					
					boolean matched = true;
					
					if( source.corp.getBusinessType() == null ){
						tmp.add( source );
						continue match;
					}
					for( String filter : mustHas ){
						if( source.corp.getBusinessType()
								.toLowerCase( Locale.ENGLISH )
								.indexOf( filter.toLowerCase( Locale.ENGLISH ) ) >=0 ){
							
							tmp.add( source );
							continue match;
						}
					}
					for( String filter : notHas ){
						if( source.corp.getBusinessType()
								.toLowerCase( Locale.ENGLISH )
								.indexOf( filter.toLowerCase( Locale.ENGLISH ) ) >=0 ){
							matched = false;
							continue match;
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
