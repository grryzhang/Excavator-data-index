package com.zhongzhou.Excavator.dataIndex.sortService.sorters;

import java.util.List;

import com.zhongzhou.Excavator.dataIndex.Exception.DataIndexException;

public interface Sorter<T> {

	public List<T> sort( List<T> source ) throws DataIndexException;
}
