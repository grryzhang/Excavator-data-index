package com.zhongzhou.Excavator.dataIndex.web.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.fasterxml.jackson.databind.util.BeanUtil;
import com.zhongzhou.Excavator.dataIndex.model.IndexSearchParameters;
import com.zhongzhou.Excavator.dataIndex.model.RecommendIndex;
import com.zhongzhou.Excavator.dataIndex.model.item.wheel.DataIndexedCorporation;
import com.zhongzhou.Excavator.dataIndex.model.item.wheel.IndexCreateParameters;
import com.zhongzhou.Excavator.dataIndex.service.dataService.WheelDataIndexService;
import com.zhongzhou.Excavator.dataIndex.model.item.wheel.CorporationIndexSearchParameters;
import com.zhongzhou.Excavator.springsupport.injectlist.ServiceNameList;
import com.zhongzhou.common.model.web.JsonResponse;

@Controller
public class RecommendController {
	
	@Autowired
	private WheelDataIndexService wheelDataIndexService;

	@RequestMapping(method=RequestMethod.POST, value="/recommend/wheel/{id}/default")
	public @ResponseBody JsonResponse getRecommendOfWheel( 
			@PathVariable String id,
			@RequestBody CorporationIndexSearchParameters searchParameters,
			HttpServletRequest request, 
			HttpServletResponse response, 
			HttpSession session ) throws Exception{  
		
		JsonResponse result = new JsonResponse();
		
		if( searchParameters == null ) searchParameters  = new CorporationIndexSearchParameters();
		searchParameters.indexIds = new ArrayList<String>( Arrays.asList( id ) );
		
		long count = wheelDataIndexService.countCorporationIndex(searchParameters);
		
		List< DataIndexedCorporation > indexedCorporations = wheelDataIndexService.getIndexedCorporationData(searchParameters);
		
		result.setSuccess( true );
		result.setTotalResult( count );
		result.setActionMessage( "Success" );
		result.setData( indexedCorporations );
		
		return result;
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/recommend/wheel/{id}/count")
	public @ResponseBody JsonResponse getCountOfOneRecommendOfWheel( 
			@PathVariable String id,
			@RequestBody IndexSearchParameters searchParameters,
			HttpServletRequest request, 
			HttpServletResponse response, 
			HttpSession session ) throws Exception{  
		
		JsonResponse result = new JsonResponse();
		
		if( searchParameters == null ) searchParameters  = new IndexSearchParameters();
		searchParameters.indexIds = new ArrayList<String>( Arrays.asList( id ) );
		searchParameters.limit = -1;
		searchParameters.start = -1;
		
		long count = wheelDataIndexService.countCorporationIndex(searchParameters);
		
		result.setSuccess( true );
		result.setTotalResult( count );
		result.setActionMessage( "Success" );
		result.setData( count );
		
		return result;
	}
	
	
	@RequestMapping(method=RequestMethod.POST, value="recommend/wheel/{id}/corpList")
	public @ResponseBody JsonResponse getRecommendListWithoutWheelData( 
			@PathVariable String id,
			@RequestBody CorporationIndexSearchParameters searchParameters,
			HttpServletRequest request, 
			HttpServletResponse response, 
			HttpSession session ) throws Exception{  
		
		JsonResponse result = new JsonResponse();
		
		if( searchParameters == null ) searchParameters  = new CorporationIndexSearchParameters();
		searchParameters.indexIds = new ArrayList<String>( Arrays.asList( id ) );
		
		long count = wheelDataIndexService.countCorporationIndex(searchParameters);

		List< DataIndexedCorporation > indexedCorporations = wheelDataIndexService.getIndexedCorporationDataWithoutWheel(searchParameters);
		
		result.setSuccess( true );
		result.setTotalResult( count );
		result.setActionMessage( "Success" );
		result.setData( indexedCorporations );
		
		return result;
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/recommend/list/wheel")
	public @ResponseBody JsonResponse getRecommendList( 
			@RequestBody CorporationIndexSearchParameters searchParameters,
			HttpServletRequest request, 
			HttpServletResponse response, 
			HttpSession session ) throws Exception{  
		
		JsonResponse result = new JsonResponse();
		
		long count = wheelDataIndexService.countCorporationIndex(searchParameters);
		
		List< RecommendIndex > indexedCorporations = wheelDataIndexService.getRecommendIndex(searchParameters);
		
		result.setSuccess( true );
		result.setTotalResult( count );
		result.setActionMessage( "Success" );
		result.setData( indexedCorporations );
		
		return result;
	}
}
