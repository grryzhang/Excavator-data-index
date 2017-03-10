package com.zhongzhou.Excavator.dataIndex.service.sortService.sorters;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.stereotype.Component;

import com.zhongzhou.Excavator.dataIndex.Exception.DataIndexException;

@Component
public class SorterFactory {
	
	@Autowired
	private AutowireCapableBeanFactory capableBeanFactory;

	private Map<String,String> sorterMapper = new HashMap<String,String>();
	
	public SorterFactory(){
		
		sorterMapper.put( "wheel.corp.sort.byDefaultGrade", "com.zhongzhou.Excavator.dataIndex.sortService.sorters.wheelCorp.ByScore");
	}

	public <T> Sorter<T> getSorter( String sorterName ) throws DataIndexException{
		
		Class clazz;
		
		if( !this.sorterMapper.containsKey( sorterName ) ){
			throw new DataIndexException( "Filter is not existed by input name: " + sorterName );
		}
		
		try {	 
			
			clazz = Class.forName( this.sorterMapper.get( sorterName ) );
			Object instance = clazz.newInstance();
			capableBeanFactory.autowireBean( instance );
			return (Sorter<T>)instance;
		} catch ( ClassNotFoundException | InstantiationException | IllegalAccessException e ) {
			
			throw new DataIndexException(
					"Can not initialize Sorter:" 
					+ sorterName
					+ ", class : " 
					+ this.sorterMapper.get( sorterName ) 
					+"is not found." 
					,e);
		}
	}

}
