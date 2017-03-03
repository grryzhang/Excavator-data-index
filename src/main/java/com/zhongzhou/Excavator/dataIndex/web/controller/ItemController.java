package com.zhongzhou.Excavator.dataIndex.web.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhongzhou.Excavator.dataIndex.indexService.WheelIndexService;
import com.zhongzhou.Excavator.dataIndex.model.item.wheel.DataIndexedCorporation;
import com.zhongzhou.Excavator.dataIndex.model.item.wheel.IndexCreateParameters;
import com.zhongzhou.Excavator.dataIndex.model.item.wheel.Wheel;
import com.zhongzhou.Excavator.springsupport.injectlist.ServiceNameList;
import com.zhongzhou.common.model.web.JsonResponse;

@Controller
public class ItemController {
	
	@Autowired
	public WheelIndexService wheelSearchService;

	@RequestMapping(method=RequestMethod.POST, value="/sieving/wheel")
	public @ResponseBody JsonResponse sieveWheels( 
			@RequestBody IndexCreateParameters createParameters,
			HttpServletRequest request, 
			HttpServletResponse response, 
			HttpSession session ) throws Exception{  
		
		JsonResponse result = new JsonResponse();
		
		String indexId = UUID.randomUUID().toString();

		Thread t = new indexServiceThreade( indexId , createParameters );
	    t.start();  
	    
	    Map<String,String> asynResult = new HashMap<String,String>();
	    asynResult.put( "indexId" , indexId );

		result.setSuccess( true );
		result.setActionMessage( "Success" );
		result.setData( asynResult );
		
		return result;
	}
	
	private class indexServiceThreade extends Thread{
		
		private String indexId;
		
		private IndexCreateParameters createParameters;
		
		public indexServiceThreade( String indexId , IndexCreateParameters createParameters ){
			this.indexId = indexId;
			this.createParameters = createParameters;
		}
		
		@Override
		public void run(){ 
			//List<DataIndexedCorporation> result = 
			wheelSearchService.indexSuppliersOfMatchedWheel(this.indexId,createParameters);
		}
	};
}
