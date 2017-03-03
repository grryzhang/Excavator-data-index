package com.zhongzhou.Excavator.dataIndex.filterService;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhongzhou.Excavator.dataIndex.Exception.DataIndexException;
import com.zhongzhou.Excavator.dataIndex.filterService.filters.Filter;
import com.zhongzhou.Excavator.dataIndex.filterService.filters.FilterFactory;
import com.zhongzhou.Excavator.dataIndex.model.item.wheel.Corporation;
import com.zhongzhou.Excavator.dataIndex.model.item.wheel.DataIndexedCorporation;
import com.zhongzhou.common.util.BeanUtil;

@Service
public class WheelCorpFilterService {
	
	@Autowired
	FilterFactory filterFactory;
	
	public List<DataIndexedCorporation> defaultWheelCorpFilter( List<DataIndexedCorporation> source ) throws DataIndexException{
		
		Filter<DataIndexedCorporation> testFilter = filterFactory.getFilter("wheel.corp.mainProduct");
		
		List<DataIndexedCorporation> tmp = testFilter.filter(source);
		
		return tmp;
	}
}
