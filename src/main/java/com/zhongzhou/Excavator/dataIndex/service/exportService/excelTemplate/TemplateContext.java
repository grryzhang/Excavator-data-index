package com.zhongzhou.Excavator.dataIndex.service.exportService.excelTemplate;

import java.util.HashMap;
import java.util.Map;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.zhongzhou.Excavator.dataIndex.Exception.ExportTemplateException;

public class TemplateContext {

	private Integer lastRowNum = -1;
	
	private Integer columnOffset = -1;
	
	private Workbook templateWorkbook;
	
	private Sheet templateSheeet;
	
	private Workbook newWorkbook;
	
	private Sheet newSheeet;
	
	private Map<String, Object> visableData = new HashMap<String, Object>();
	
	TemplateContext( 
			Workbook templateWorkbook, 
			Sheet    templateSheeet,  
			Workbook newWorkbook , 
			Sheet    newSheeet  ) throws ExportTemplateException{
		if( newWorkbook == null ){
			throw new ExportTemplateException( "newWorkbook in TemplateContext should not be null." );
		}
		if( newSheeet == null ){
			throw new ExportTemplateException( "newSheeet in TemplateContext should not be null." );
		}
		if( templateWorkbook == null ){
			throw new ExportTemplateException( "templateWorkbook in TemplateContext should not be null." );
		}
		if( templateSheeet == null ){
			throw new ExportTemplateException( "templateSheeet in TemplateContext should not be null." );
		}
		this.templateWorkbook = templateWorkbook;
		this.templateSheeet   = templateSheeet;
		this.newWorkbook      = newWorkbook;
		this.newSheeet        = newSheeet;
	}

	public Integer getLastRowNum() {
		return lastRowNum;
	}

	public void setLastRowNum(Integer lastRowNum) {
		this.lastRowNum = lastRowNum;
	}

	public Workbook getNewWorkbook() {
		return newWorkbook;
	}

	public Sheet getNewSheeet() {
		return newSheeet;
	}

	public Workbook getTemplateWorkbook() {
		return templateWorkbook;
	}

	public Sheet getTemplateSheeet() {
		return templateSheeet;
	}

	public void setVisableData( String name,  Object data ) {
		this.visableData.put( name, data );
	}

	public void removeVisableData( String name ) {
		
		if( "".equals( name ) ){
			return ;
		}
		
		if( this.visableData.get( name )!= null ){
			this.visableData.remove( name );
		}
	}
	
	public Object getVisableData( String name ) {
		return this.visableData.get(name);
	}

	public Integer getColumnOffset() {
		return columnOffset;
	}

	public void setColumnOffset(Integer columnOffset) {
		this.columnOffset = columnOffset;
	}
}
