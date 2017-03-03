package com.zhongzhou.Excavator.dataIndex.filterService.filters;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.stereotype.Component;

import com.zhongzhou.Excavator.dataIndex.Exception.DataIndexException;

@Component
public class FilterFactory {
	
	@Autowired
	private AutowireCapableBeanFactory capableBeanFactory;

	private Map<String,String> filterMapper = new HashMap<String,String>();
	
	public FilterFactory(){
		
		filterMapper.put( "wheel.corp.mainProduct", "com.zhongzhou.Excavator.dataIndex.filterService.filters.wheelCorp.MainProductsFilter");
	}

	public <T> Filter<T> getFilter( String filterName ) throws DataIndexException{
		
		Class clazz;
		
		if( !this.filterMapper.containsKey( filterName ) ){
			throw new DataIndexException( "Filter is not existed by input name: " + filterName );
		}
		
		try {	 
			
			clazz = Class.forName( this.filterMapper.get( filterName ) );
			Object instance = clazz.newInstance();
			capableBeanFactory.autowireBean( instance );
			return (Filter<T>)instance;
		} catch ( ClassNotFoundException | InstantiationException | IllegalAccessException e ) {
			
			throw new DataIndexException(
					"Can not initialize filter:" 
					+ filterName
					+ ", class : " 
					+ this.filterMapper.get( filterName ) 
					+"is not found." 
					,e);
		}
	}

}
