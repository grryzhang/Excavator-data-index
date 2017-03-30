package com.zhongzhou.Excavator.dataIndex.service.filterService;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhongzhou.Excavator.dataIndex.Exception.DataIndexException;
import com.zhongzhou.Excavator.dataIndex.service.filterService.filters.Filter;
import com.zhongzhou.Excavator.dataIndex.service.filterService.filters.FilterFactory;
import com.zhongzhou.Excavator.dataIndex.model.item.wheel.Corporation;
import com.zhongzhou.Excavator.dataIndex.model.item.wheel.DataIndexedCorporation;
import com.zhongzhou.Excavator.dataIndex.model.item.wheel.IndexCreateParameters;
import com.zhongzhou.common.util.BeanUtil;

@Service
public class WheelCorpFilterService {
	
	@Autowired
	FilterFactory filterFactory;
	
	private static String[] defaultFilterChain = {"wheel.corp.mainProduct","wheel.corp.businessType"};
	
	public List<DataIndexedCorporation> defaultWheelCorpFilter( List<DataIndexedCorporation> source  ) throws DataIndexException{
		
		List<DataIndexedCorporation> tmp = new ArrayList<DataIndexedCorporation>();
		tmp.addAll( source );
		
		for( String filterName : defaultFilterChain ){
			
			Filter<DataIndexedCorporation> testFilter = filterFactory.getFilter( filterName );
			
			tmp = testFilter.filter( tmp );
		}
		
		
		return tmp;
	}
}
