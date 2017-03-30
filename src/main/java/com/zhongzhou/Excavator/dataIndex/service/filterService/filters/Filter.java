package com.zhongzhou.Excavator.dataIndex.service.filterService.filters;

import java.util.List;

import com.zhongzhou.Excavator.dataIndex.Exception.DataIndexException;
import com.zhongzhou.Excavator.dataIndex.model.item.wheel.IndexCreateParameters;

public interface Filter<T> {

	public List<T> filter( List<T> source ) throws DataIndexException;
}
