package com.zhongzhou.Excavator.dataIndex.model.item.wheel;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.zhongzhou.Excavator.dataIndex.util.IndexParameter.isShowCondition;

public class IndexCreateParameters {
	
	@isShowCondition
	public List<String> material;

	@isShowCondition
	public List<String> hubDiameter;
	
	@isShowCondition
	public List<String> PCD;
	
	public int start = 0;
	
	public int limit = 50;
}
