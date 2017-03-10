package com.zhongzhou.Excavator.dataIndex.service.exportService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhongzhou.Excavator.dataIndex.DAO.mongo.IndexIntermediateReulstDAO;
import com.zhongzhou.Excavator.dataIndex.DAO.mongo.WheelDAO;
import com.zhongzhou.Excavator.dataIndex.Exception.DataIndexException;
import com.zhongzhou.Excavator.dataIndex.model.IndexSearchParameters;
import com.zhongzhou.Excavator.dataIndex.model.WebDataMongoData;
import com.zhongzhou.Excavator.dataIndex.model.item.wheel.DataIndexedCorporation;
import com.zhongzhou.Excavator.dataIndex.model.item.wheel.Wheel;
import com.zhongzhou.Excavator.dataIndex.model.item.wheel.WheelSearchParameters;
import com.zhongzhou.common.util.BeanUtil;

@Service
public class IndexedCorporationExportService {

	@Autowired
	IndexIntermediateReulstDAO indexIntermediateReulstDAO;
	
	@Autowired
	WheelDAO wheelDAO;
	
	public void doExcelExport( IndexSearchParameters indexSearchParameters ){
		
		IndexSearchParameters indexSearchParametersClone = null;
		try {
			indexSearchParametersClone = BeanUtil.beanCloneByJakson(indexSearchParameters,IndexSearchParameters.class );
		} catch (IOException e) {
			throw new DataIndexException( "Failed to doExcelExport:", e);
		}
		if( indexSearchParametersClone == null ){
			throw new DataIndexException( "Failed to doExcelExport, search parameters is null.");
		}
		
		long count = indexIntermediateReulstDAO.countCorporationIndexResult( indexSearchParametersClone );
		
		int perProcessed = 50;
		for( int i=0 ; i < count ; i+= perProcessed  ){
			
			indexSearchParametersClone.start = i;
			indexSearchParametersClone.limit = perProcessed;
			
			List<DataIndexedCorporation>  indexedCorps = indexIntermediateReulstDAO.getCorporationIndexResult( indexSearchParametersClone );
			
			List<String> wheelIds = new ArrayList<String>();
			for( DataIndexedCorporation indexedCorporation : indexedCorps ){
				
				if( indexedCorporation.wheelIDs != null ){
					for( String wheelID : indexedCorporation.wheelIDs ){
						wheelIds.add( wheelID );
					}
				}
			}
			
			WheelSearchParameters wheelSearchParameters = new WheelSearchParameters();
			wheelSearchParameters.wheelIds = wheelIds;
			wheelSearchParameters.start = -1;
			wheelSearchParameters.limit = -1;
			String[] excludeFields = new String[]{"data.specification","data.productDescription"};
			List<WebDataMongoData<Wheel>> wheels = wheelDAO.getWheelData( wheelSearchParameters , excludeFields, null );
			
			Map<String, Wheel> wheelsMap = new HashMap<String, Wheel>();
			for( WebDataMongoData<Wheel> wheelData : wheels ){
				if( wheelData != null && wheelData.getData() != null ){
					wheelsMap.put( wheelData.getData().getId() , wheelData.getData() );
				}
			}
				
			for( DataIndexedCorporation corp : indexedCorps ){
				corp.wheels = new ArrayList<Wheel>();
				if( corp.wheelIDs != null ){
					for( String wheelID : corp.wheelIDs ){
						Wheel wheel = wheelsMap.get( wheelID );
						if( wheel != null ){
							corp.wheels.add( wheel );
						}
					}
				}
			}
			
			
		}
	}
}
