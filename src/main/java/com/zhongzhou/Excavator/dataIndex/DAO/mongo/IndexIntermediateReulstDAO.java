package com.zhongzhou.Excavator.dataIndex.DAO.mongo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.aggregation.Accumulator;
import org.mongodb.morphia.aggregation.Group;
import org.mongodb.morphia.query.Query;
import org.springframework.stereotype.Component;

import com.zhongzhou.Excavator.dataIndex.model.item.wheel.DataIndexedCorporation;
import com.zhongzhou.Excavator.dataIndex.model.item.wheel.DataIndexedCorporationSearchParameters;
import com.zhongzhou.Excavator.dataIndex.model.item.wheel.WebDataCorporation;
import com.zhongzhou.Excavator.dataIndex.model.IndexSearchParameters;
import com.zhongzhou.Excavator.dataIndex.model.RecommendIndex;
import com.zhongzhou.Excavator.springsupport.injectlist.DataSourceList;

@Component
public class IndexIntermediateReulstDAO {

	@Resource(name=DataSourceList.MONGO_MD_DOCUMENTS)
	Datastore  mongoMorphiaDataStore;
	
	public void saveCorporationIndexResult( List< DataIndexedCorporation > corpIndexDataResult ){
		
		mongoMorphiaDataStore.save( corpIndexDataResult );
	}
	
	public List< RecommendIndex > getCorporationIndexList( IndexSearchParameters searchParameters ){
		
		Query query = mongoMorphiaDataStore
				.createQuery( DataIndexedCorporation.class )
				.disableValidation();
		
		this.prepareSearchParameters(searchParameters, query);
		
		Iterator<RecommendIndex> result = mongoMorphiaDataStore
				.createAggregation( DataIndexedCorporation.class )
			    .match( query )
				.group( 
						Group.id( Group.grouping("indexId"), Group.grouping("indexCondition"), Group.grouping("dataType") ),
						Group.grouping( "indexId",        Group.first("indexId")        ),
						Group.grouping( "indexCondition", Group.first("indexCondition") ),
						Group.grouping( "dataType",		  Group.first("dataType") 		),
						Group.grouping( "count", new Accumulator("$sum", 1) ) 
				)
				.aggregate(RecommendIndex.class);
		
		List<RecommendIndex> resultList = new ArrayList<RecommendIndex>();
		while( result.hasNext() ){
			resultList.add( result.next() );
		}
		
		return resultList;
	}
	
	public List< DataIndexedCorporation > getCorporationIndexResult( IndexSearchParameters searchParameters ){
		
		Query query = mongoMorphiaDataStore
				.createQuery( DataIndexedCorporation.class )
				.disableValidation();
		
		this.prepareSearchParameters(searchParameters, query);
		
		if( searchParameters.limit > 0 && searchParameters.start >= 0 ){
			query.offset( searchParameters.start ).limit( searchParameters.limit );
		}
		
		List< DataIndexedCorporation > result = query.asList();
		
		return result;
	}
	
	public long countCorporationIndexResult( IndexSearchParameters searchParameters ){
		
		Query query = mongoMorphiaDataStore
				.createQuery( DataIndexedCorporation.class )
				.disableValidation();
		
		this.prepareSearchParameters(searchParameters, query);
		
		long result = query.countAll();
		
		return result;
	}
	
	
	private void prepareSearchParameters( IndexSearchParameters searchParameters , Query query ){
		
		if( searchParameters.indexIds != null && searchParameters.indexIds.size() > 0 ){
			
			query.field("indexId").in( searchParameters.indexIds );
		}
	}
}
