package com.zhongzhou.Excavator.dataIndex.web.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;
import com.zhongzhou.Excavator.dataIndex.Exception.ExportTemplateException;
import com.zhongzhou.Excavator.dataIndex.model.IndexSearchParameters;
import com.zhongzhou.Excavator.dataIndex.service.exportService.IndexedCorporationExportService;
import com.zhongzhou.common.model.web.JsonResponse;

@Controller
public class ExportController {
	
	@Autowired
	IndexedCorporationExportService indexedCorporationExportService;

	@RequestMapping(
		method=RequestMethod.POST, 
		value="/export/recommend/wheel/{indexId}" ,  
		produces = {"application/ostet-stream"} )
	public ResponseEntity<byte[]> getRecommendOfWheel(
			@PathVariable String indexId,
			HttpServletRequest request, 
			HttpServletResponse response, 
			HttpSession session
			) throws ExportTemplateException, IOException{
			
		HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
	    headers.setContentDispositionFormData("attachment", "table.xlsx");
	     
	    IndexSearchParameters indexSearchParameters = new IndexSearchParameters();
		indexSearchParameters.indexIds = new ArrayList<>( Arrays.asList( indexId ) );
	     
		ByteArrayOutputStream exceloutStream = new ByteArrayOutputStream();
		
	    indexedCorporationExportService.doExcelExport(indexSearchParameters, exceloutStream);
	    
	    exceloutStream.flush();
	    
	    byte[] byt = exceloutStream.toByteArray();
	    
	    exceloutStream.close();
	     
	    return new ResponseEntity<byte[]>( byt, headers, HttpStatus.CREATED);
	}
	
	/*
	public ResponseEntity<byte[]> getRecommendOfWheel(
			@PathVariable String indexId,
			HttpServletRequest request, 
			HttpServletResponse response, 
			HttpSession session
			) throws ExportTemplateException, IOException{
			
		HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
	    headers.setContentDispositionFormData("attachment", "table.xlsx");
	     
	    IndexSearchParameters indexSearchParameters = new IndexSearchParameters();
		indexSearchParameters.indexIds = new ArrayList<>( Arrays.asList( indexId ) );
	     
		ByteArrayOutputStream exceloutStream = new ByteArrayOutputStream();
		
	    indexedCorporationExportService.doExcelExport(indexSearchParameters, exceloutStream);
	    
	    exceloutStream.flush();
	    
	    byte[] byt = exceloutStream.toByteArray();
	    
	    exceloutStream.close();
	     
	    return new ResponseEntity<byte[]>( byt, headers, HttpStatus.CREATED);
	}
	*/
}
