package com.zhongzhou.Excavator.dataIndex.service.exportService.excelTemplate.rule;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import com.zhongzhou.Excavator.dataIndex.Exception.ExportTemplateException;
import com.zhongzhou.Excavator.dataIndex.service.exportService.excelTemplate.ExcelTemplateRule;
import com.zhongzhou.Excavator.dataIndex.service.exportService.excelTemplate.TemplateContext;

public class SheetCopy implements ExcelTemplateRule{
	
	public List<ExcelTemplateRule> innerRules = new ArrayList<ExcelTemplateRule>();
	
	@Override
	public void render( TemplateContext context ) throws ExportTemplateException {
		
		Sheet targetSheet   = context.getNewSheeet();
		Sheet templateSheet = context.getTemplateSheeet();
		Iterator<Row> rowIterator = templateSheet.iterator();
		
		int columnOffset = context.getColumnOffset();
		int maxColumnNum = 0;
		while( rowIterator.hasNext() ){
			
			Row templateRow = rowIterator.next();
			if ( templateRow.getLastCellNum() > maxColumnNum) {
				maxColumnNum = templateRow.getLastCellNum();
			}
		}
		for  (int i=0, j = 0 - columnOffset; j < maxColumnNum; i++ , j++) {
			targetSheet.setColumnWidth( i, templateSheet.getColumnWidth(j) );
		}
		
		if( innerRules == null || innerRules.size()<=0 ){
			return;
		}
			
		for( ExcelTemplateRule rule : innerRules ){
				
			rule.render( context );
		}
	}

	@Override
	public void addInnerRule( ExcelTemplateRule rule )throws ExportTemplateException{
		innerRules.add(rule);
	}
}
