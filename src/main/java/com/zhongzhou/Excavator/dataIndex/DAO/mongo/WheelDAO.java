package com.zhongzhou.Excavator.dataIndex.DAO.mongo;

import java.util.List;

import javax.annotation.Resource;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import org.springframework.stereotype.Component;

import com.zhongzhou.Excavator.dataIndex.model.WebDataMongoData;
import com.zhongzhou.Excavator.dataIndex.model.item.wheel.SourceSearchParameters;
import com.zhongzhou.Excavator.dataIndex.model.item.wheel.WebDataWheel;
import com.zhongzhou.Excavator.dataIndex.model.item.wheel.Wheel;
import com.zhongzhou.Excavator.springsupport.injectlist.DataSourceList;

@Component
public class WheelDAO {

	@Resource(name=DataSourceList.MONGO_MD_DOCUMENTS)
	Datastore  mongoMorphiaDataStore;
	
	public List<String> getFieldDistinct( String name ){
		
		List<String> fieldDistinct = mongoMorphiaDataStore.getCollection( WebDataWheel.class ).distinct( name );
		return fieldDistinct;
	}

	public long countWheelData( SourceSearchParameters searchParameters ){
		
		Query query = this.prepareQuery(searchParameters);
		
		query.offset(0).limit(0);
		
		long count = query.countAll();
		
		return count;
	}

	public List< WebDataMongoData<Wheel> > getWheelData( SourceSearchParameters searchParameters ){
		
		Query query = this.prepareQuery(searchParameters);
		
		List< WebDataMongoData<Wheel> > result = query.asList();
		
		return result;
	}
	
	public List< WebDataMongoData<Wheel> > getWheelData( SourceSearchParameters searchParameters , String[] excludeFields, String [] includeFields ){
		
		Query query = this.prepareQuery(searchParameters);
		
		if( excludeFields != null ){
			query.retrievedFields( false, excludeFields );
		}
		if( includeFields != null ){
			query.retrievedFields( true, excludeFields );
		}
		
		List< WebDataMongoData<Wheel> > result = query.asList();
		
		return result;
	}
	
	private Query prepareQuery( SourceSearchParameters searchParameters ){
		
		Query query = mongoMorphiaDataStore
				.createQuery((new WebDataWheel()).getClass())
				.disableValidation();
		
		if( searchParameters.wheelHubDiameter != null ){
			
			query.field("data.wheelHubDiameter").in( searchParameters.wheelHubDiameter );
		}
		
		if( searchParameters.wheelPCD != null ){
			
			query.field("data.pcd").in( searchParameters.wheelPCD );
		}
		
		if( searchParameters.material != null ){
			
			query.field("data.material").in( searchParameters.material );
		}
		
		
		if( searchParameters.limit > 0 && searchParameters.start >= 0 ){
			query.offset( searchParameters.start ).limit( searchParameters.limit );
		}
		
		return query;
	}
}
