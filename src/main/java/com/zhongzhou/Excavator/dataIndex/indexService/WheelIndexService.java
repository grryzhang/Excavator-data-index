package com.zhongzhou.Excavator.dataIndex.indexService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.mongodb.morphia.query.validation.ExistsOperationValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhongzhou.Excavator.dataIndex.DAO.mongo.CorporationDAO;
import com.zhongzhou.Excavator.dataIndex.DAO.mongo.IndexIntermediateReulstDAO;
import com.zhongzhou.Excavator.dataIndex.DAO.mongo.MinerTrackDAO;
import com.zhongzhou.Excavator.dataIndex.DAO.mongo.WheelDAO;
import com.zhongzhou.Excavator.dataIndex.Exception.DataIndexException;
import com.zhongzhou.Excavator.dataIndex.filterService.WheelCorpFilterService;
import com.zhongzhou.Excavator.dataIndex.indexService.indexs.wheel.HubDiameterIndex;
import com.zhongzhou.Excavator.dataIndex.indexService.indexs.wheel.MaterialIndex;
import com.zhongzhou.Excavator.dataIndex.indexService.indexs.wheel.PCDIndex;
import com.zhongzhou.Excavator.dataIndex.model.WebDataMongoData;
import com.zhongzhou.Excavator.dataIndex.model.item.wheel.CorporationSearchParameters;
import com.zhongzhou.Excavator.dataIndex.model.item.wheel.DataIndexedCorporation;
import com.zhongzhou.Excavator.dataIndex.model.item.wheel.IndexCreateParameters;
import com.zhongzhou.Excavator.dataIndex.model.item.wheel.SourceSearchParameters;
import com.zhongzhou.Excavator.dataIndex.model.item.wheel.TrackSearchParameters;
import com.zhongzhou.Excavator.dataIndex.model.item.wheel.WebDataCorporation;
import com.zhongzhou.Excavator.dataIndex.model.item.wheel.WebDataMinerTrack;
import com.zhongzhou.Excavator.dataIndex.model.item.wheel.Wheel;
import com.zhongzhou.Excavator.dataIndex.sortService.sorters.Sorter;
import com.zhongzhou.Excavator.dataIndex.sortService.sorters.SorterFactory;
import com.zhongzhou.common.util.BeanUtil;

@Service
public class WheelIndexService {
	
	private static Logger logger = Logger.getLogger(WheelIndexService.class);
	
	@Autowired
	WheelDAO wheelDAO;
	
	@Autowired
	MinerTrackDAO minerTrackDAO;
	
	@Autowired
	CorporationDAO corporationDAO;
	
	@Autowired
	IndexIntermediateReulstDAO indexResultDAO;
	
	@Autowired
	HubDiameterIndex hubDiameterIndex;
	@Autowired
	PCDIndex pcdIndex;
	@Autowired
	MaterialIndex materialIndex;
	
	@Autowired
	WheelCorpFilterService wheelCorpFilterService;
	
	@Autowired
	SorterFactory sorterFactory;
	
	public long countMatchedWheel( IndexCreateParameters searchParameters ){
		
		SourceSearchParameters sourceSearchParameters = this.prepareSourceSP( searchParameters );
		
		long count = wheelDAO.countWheelData(sourceSearchParameters);
		
		return count;
	}
	
	public List<Wheel> indexMatchedWheel( IndexCreateParameters searchParameters ){
		
		List< Wheel > result = null;
		
		SourceSearchParameters sourceSearchParameters = this.prepareSourceSP( searchParameters );
		
		if( searchParameters.limit <= 0 ){
			throw new DataIndexException( "Index for matched Wheel must set limit, or it would cause out of memory when the result has too many records." );
		}
			
		String[] excludeFields = new String[]{"data.specification","data.productDescription"};
		List< WebDataMongoData<Wheel> > wheelSourceData = wheelDAO.getWheelData(sourceSearchParameters,excludeFields,null);
		
		result = new ArrayList< Wheel >();
		for( WebDataMongoData<Wheel> wheel : wheelSourceData ){
			
			if( wheel != null && wheel.getData() != null ){
				result.add( wheel.getData() );
			}
		}	
		
		return result;
	}
	
	private SourceSearchParameters prepareSourceSP( IndexCreateParameters searchParameters ){
		
		IndexCreateParameters searchParametersClone = null;
		try{
			searchParametersClone = BeanUtil.beanCloneByJakson(searchParameters, IndexCreateParameters.class);
		}catch( IOException e ){
			throw new DataIndexException( e );
		}
		
		SourceSearchParameters sourceSearchParameters = new SourceSearchParameters();
		
		if( searchParameters.hubDiameter != null && searchParameters.hubDiameter.size() > 0 ){
			
			List<String> matchedContent = hubDiameterIndex.getMatchedWheelHubDiameter( searchParametersClone.hubDiameter );
			sourceSearchParameters.wheelHubDiameter = matchedContent;
		}
		
		if( searchParameters.PCD != null && searchParameters.PCD.size() > 0 ){
			
			List<String> matchedContent = pcdIndex.getMatchedPCD( searchParametersClone.PCD );
			sourceSearchParameters.wheelPCD = matchedContent;
		}
		
		if( searchParameters.material != null && searchParameters.material.size() > 0 ){
			
			List<String> matchedContent = materialIndex.getMatchedMaterial( searchParametersClone.material );
			sourceSearchParameters.material = matchedContent;
		}
			
		sourceSearchParameters.limit = searchParameters.limit;
		sourceSearchParameters.start = searchParameters.start;
		
		return sourceSearchParameters;
	}
	
	public List<DataIndexedCorporation> indexSuppliersOfMatchedWheel( final String indexID, final IndexCreateParameters searchParameters ) throws DataIndexException{
		
		IndexCreateParameters searchParametersClone = null;
		try{
			searchParametersClone = BeanUtil.beanCloneByJakson(searchParameters, IndexCreateParameters.class);
		}catch( IOException e ){
			throw new DataIndexException( e );
		}
		
		Date createDate = new Date();
		
		Map<String,DataIndexedCorporation> allPendingIndexedCorporations = new HashMap<String,DataIndexedCorporation>();
		
		long count = this.countMatchedWheel(searchParametersClone);
		int preProcess = 3000;
		int processed = 0;
		while( processed <= count ){
			
			System.out.println("Processed:" + processed );
			
			searchParametersClone.start = processed ;
			searchParametersClone.limit = preProcess; 

			List<Wheel> matched = this.indexMatchedWheel( searchParametersClone );
			
			Map<String,DataIndexedCorporation> pendingIndexedCorporations = this.getSupplierIDsOfMatchedWheel( indexID, matched );
		
			for(  String onePendingCorpUrl : pendingIndexedCorporations.keySet()  ){
				
				DataIndexedCorporation exist = allPendingIndexedCorporations.get( onePendingCorpUrl );
				DataIndexedCorporation onePending =  pendingIndexedCorporations.get(onePendingCorpUrl);
				if( exist == null ){
					onePending.setCreateDate( createDate.getTime() );
					allPendingIndexedCorporations.put( onePendingCorpUrl, onePending);
				}else{
					exist.wheelIDs.addAll( onePending.wheelIDs );
				}
			}
			
			matched = null;
			pendingIndexedCorporations = null;
			processed += preProcess;
		}
		
		List<String> corporationUrls = new ArrayList<String>();
		for( DataIndexedCorporation onePending : allPendingIndexedCorporations.values() ){
			
			corporationUrls.add( onePending.corpId );
		}
		
		CorporationSearchParameters corpSearchParameters = new CorporationSearchParameters();
		corpSearchParameters.resourceUrls = corporationUrls;
		corpSearchParameters.limit = -1;
		corpSearchParameters.start = -1;
		
		List<WebDataCorporation> webData = corporationDAO.getCorporationData( corpSearchParameters );
		
		for( WebDataCorporation data : webData){
			
			if( data.getData() != null ){
				DataIndexedCorporation exist = allPendingIndexedCorporations.get( data.getData().getId() );
				if( exist != null ){
					exist.corp = data.getData();
				}
			}
		}
		
		List<DataIndexedCorporation> dataIndexCorporations = new ArrayList<DataIndexedCorporation>( allPendingIndexedCorporations.values() );
		
		dataIndexCorporations = wheelCorpFilterService.defaultWheelCorpFilter( dataIndexCorporations );
		
		Sorter<DataIndexedCorporation> sorter = sorterFactory.getSorter("wheel.corp.sort.byDefaultGrade");
		dataIndexCorporations = sorter.sort(dataIndexCorporations);	
		
		int fromIndex = 0;
		int perSave = 30;
		while( fromIndex < dataIndexCorporations.size() ){
			
			int toIndex = dataIndexCorporations.size() - fromIndex > perSave ? fromIndex + perSave - 1 : dataIndexCorporations.size();
			
			List<DataIndexedCorporation> tempSave = dataIndexCorporations.subList(fromIndex, toIndex);

			indexResultDAO.saveCorporationIndexResult( tempSave );
			
			fromIndex = fromIndex + perSave;
		}
		
		allPendingIndexedCorporations = null;
		corporationUrls = null;
		corpSearchParameters = null;
		webData = null;

		return dataIndexCorporations;
		
	}
	
	private Map<String,DataIndexedCorporation> getSupplierIDsOfMatchedWheel( final String indexID, final List< Wheel > matchedWheel ) throws DataIndexException{
		
		Map<String,DataIndexedCorporation> dataIndexCorporations = new HashMap<String,DataIndexedCorporation>();
		
		List<String> tempWheelUrl = new ArrayList<String>(); 
		for( Wheel wheel : matchedWheel ){
			if( wheel != null ){
				tempWheelUrl.add( wheel.getId() );
			}
		}
		
		if( tempWheelUrl.size() > 0 ){
		
			TrackSearchParameters trackSearchParameters = new TrackSearchParameters();
			trackSearchParameters.productUrls = tempWheelUrl;
			trackSearchParameters.dataSourceType = new ArrayList<String>( Arrays.asList("alibaba.wheel.wheelUrl","madeInChina.wheel.wheelUrl") );

			List<WebDataMinerTrack> minerTracks = minerTrackDAO.getWheelDataTrack(trackSearchParameters);
			for( WebDataMinerTrack data : minerTracks ){
				
				if( data != null && data.getData() != null ){
					
					DataIndexedCorporation exist = dataIndexCorporations.get( data.getData().getCorporationUrl() );
					
					if( exist == null ){
						
						DataIndexedCorporation dataIndexCorp = new DataIndexedCorporation();
						dataIndexCorp.wheelIDs = new ArrayList<String>();
						dataIndexCorp.corpId = data.getData().getCorporationUrl();
						dataIndexCorp.setIndexId(indexID);
						//ataIndexCorp.setIndexCondition("test");
						dataIndexCorp.setDataType( DataIndexedCorporation.type );
						
						dataIndexCorporations.put( data.getData().getCorporationUrl(), dataIndexCorp );
					}else{
						
						exist.wheelIDs.add( data.getData().getProductUrl() );
					}
				}
			}
		}
		
		tempWheelUrl   = null;	

		return dataIndexCorporations;
	}
}
