package com.zhongzhou.Excavator.dataIndex.DAO.mongo;

import java.util.List;

import javax.annotation.Resource;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import org.springframework.stereotype.Component;

import com.zhongzhou.Excavator.dataIndex.model.WebDataMongoData;
import com.zhongzhou.Excavator.dataIndex.model.item.wheel.CorporationSearchParameters;
import com.zhongzhou.Excavator.dataIndex.model.item.wheel.SourceSearchParameters;
import com.zhongzhou.Excavator.dataIndex.model.item.wheel.WebDataCorporation;
import com.zhongzhou.Excavator.dataIndex.model.item.wheel.WebDataWheel;
import com.zhongzhou.Excavator.dataIndex.model.item.wheel.Wheel;
import com.zhongzhou.Excavator.springsupport.injectlist.DataSourceList;

@Component
public class CorporationDAO {
	@Resource(name=DataSourceList.MONGO_MD_DOCUMENTS)
	Datastore  mongoMorphiaDataStore;
	
	public List< WebDataCorporation > getCorporationData( CorporationSearchParameters searchParameters ){
		
		Query query = mongoMorphiaDataStore
				.createQuery((new WebDataCorporation()).getClass())
				.disableValidation();
		
		this.prepareQuery(query, searchParameters);

		if( searchParameters.limit > 0 && searchParameters.start >= 0 ){
			query.offset( searchParameters.start ).limit( searchParameters.limit );
		}
		
		List< WebDataCorporation > result = query.asList();
		
		return result;
	}
	
	private void prepareQuery( Query query , CorporationSearchParameters searchParameters ){
		
		if( searchParameters.resourceUrls != null ){
			
			query.field("data.id").in( searchParameters.resourceUrls );
		}
		
		query.field("active").notEqual( false );
	}
}
