package com.zhongzhou.Excavator.dataIndex.DAO.mongo;

import java.util.List;

import javax.annotation.Resource;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import org.springframework.stereotype.Component;

import com.zhongzhou.Excavator.dataIndex.model.item.wheel.DataIndexedCorporation;
import com.zhongzhou.Excavator.dataIndex.model.item.wheel.DataIndexedCorporationSearchParameters;
import com.zhongzhou.Excavator.dataIndex.model.item.wheel.WebDataCorporation;
import com.zhongzhou.Excavator.springsupport.injectlist.DataSourceList;

@Component
public class IndexIntermediateReulstDAO {

	@Resource(name=DataSourceList.MONGO_MD_DOCUMENTS)
	Datastore  mongoMorphiaDataStore;
	
	public void saveCorporationIndexResult( List< DataIndexedCorporation > corpIndexDataResult ){
		
		mongoMorphiaDataStore.save( corpIndexDataResult );
	}
	
	public List< DataIndexedCorporation > getCorporationIndexResult( DataIndexedCorporationSearchParameters searchParameters ){
		
		Query query = mongoMorphiaDataStore
				.createQuery( DataIndexedCorporation.class )
				.disableValidation();
		
		if( searchParameters.indexIds != null && searchParameters.indexIds.size() > 0 ){
			
			query.field("indexId").in( searchParameters.indexIds );
		}
		
		if( searchParameters.limit > 0 && searchParameters.start >= 0 ){
			query.offset( searchParameters.start ).limit( searchParameters.limit );
		}
		
		List< DataIndexedCorporation > result = query.asList();
		
		return result;
	}
}
