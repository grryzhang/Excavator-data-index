package com.zhongzhou.Excavator.dataIndex.service.sortService;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhongzhou.Excavator.dataIndex.Exception.DataIndexException;
import com.zhongzhou.Excavator.dataIndex.service.filterService.filters.Filter;
import com.zhongzhou.Excavator.dataIndex.service.filterService.filters.FilterFactory;
import com.zhongzhou.Excavator.dataIndex.model.item.wheel.Corporation;
import com.zhongzhou.common.util.BeanUtil;

@Service
public class WheelCorpSortService {
	
	@Autowired
	FilterFactory filterFactory;
	
	public List<Corporation> defaultWheelCorpSort( List<Corporation> source ) throws DataIndexException{
		
		Filter<Corporation> testFilter = filterFactory.getFilter("wheel.corp.mainProduct");
		
		List<Corporation> tmp = testFilter.filter(source);
		
		return tmp;
	}
}
