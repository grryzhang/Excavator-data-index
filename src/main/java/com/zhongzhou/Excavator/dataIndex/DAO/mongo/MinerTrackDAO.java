package com.zhongzhou.Excavator.dataIndex.DAO.mongo;

import java.util.List;

import javax.annotation.Resource;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import org.springframework.stereotype.Component;

import com.zhongzhou.Excavator.dataIndex.model.WebDataMongoData;
import com.zhongzhou.Excavator.dataIndex.model.item.wheel.DataSourceTrack;
import com.zhongzhou.Excavator.dataIndex.model.item.wheel.TrackSearchParameters;
import com.zhongzhou.Excavator.dataIndex.model.item.wheel.WebDataMinerTrack;
import com.zhongzhou.Excavator.dataIndex.model.item.wheel.WebDataWheel;
import com.zhongzhou.Excavator.dataIndex.model.item.wheel.Wheel;
import com.zhongzhou.Excavator.springsupport.injectlist.DataSourceList;

@Component
public class MinerTrackDAO {

	@Resource(name=DataSourceList.MONGO_MD_DOCUMENTS)
	Datastore  mongoMorphiaDataStore;
	
	public List< WebDataMinerTrack > getWheelDataTrack( TrackSearchParameters searchParameters ){
		
		Query query = mongoMorphiaDataStore
				.createQuery((new WebDataMinerTrack()).getClass())
				.disableValidation();
		
		if( searchParameters.productUrls != null && searchParameters.productUrls.size() > 0 ){
			
			query.field("data.productUrl").in( searchParameters.productUrls );
		}
		
		if(  searchParameters.dataSourceType != null && searchParameters.dataSourceType.size() > 0  ){
			query.field("data.dataSourceType").in( searchParameters.dataSourceType );
		}
		
		List< WebDataMinerTrack > result = query.asList();
		
		return result;
	}
}
