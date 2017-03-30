package com.zhongzhou.Excavator.dataIndex.exportService;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.web.context.support.XmlWebApplicationContext;

import com.zhongzhou.Excavator.dataIndex.Exception.ExportTemplateException;
import com.zhongzhou.Excavator.dataIndex.model.IndexSearchParameters;
import com.zhongzhou.Excavator.dataIndex.service.exportService.IndexedCorporationExportService;


public class TestIndexedCorporationExportService {
	
	private static XmlWebApplicationContext  context;
	private static String[] configs = { "classpath:applicationContext.xml" }; 
	
	public static IndexedCorporationExportService indexedCorporationExportService;
	
	@BeforeClass  
	public static void configTest(){

		try {
			context = new XmlWebApplicationContext ();
			context.setConfigLocations(configs);
			
			context.refresh();
			
			indexedCorporationExportService  = context.getBean( IndexedCorporationExportService.class );
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testAll() throws IOException, ExportTemplateException{
		
		this.testDoExcelExport();
	}
	
	public void testDoExcelExport() throws IOException, ExportTemplateException{
		
		String indexName = "wheel-steel-index";
		
		File fileOutput = new File( "D://temp//" + indexName + ".xlsx" );
		if( !fileOutput.exists() ){
			fileOutput.createNewFile();
		}
		FileOutputStream out = new FileOutputStream( fileOutput );
		
		IndexSearchParameters indexSearchParameters = new IndexSearchParameters();
		indexSearchParameters.indexIds = new ArrayList<>( Arrays.asList( indexName ) );
		
		indexedCorporationExportService.doExcelExport(indexSearchParameters, out);
		
		out.flush();
		out.close();
	}
}
