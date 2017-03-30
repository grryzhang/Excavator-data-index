package com.zhongzhou.Excavator.dataIndex.service.exportService.excelTemplate.rule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import com.zhongzhou.Excavator.dataIndex.Exception.ExportTemplateException;
import com.zhongzhou.Excavator.dataIndex.service.exportService.excelTemplate.ExcelTemplateRule;
import com.zhongzhou.Excavator.dataIndex.service.exportService.excelTemplate.ExportUtil;
import com.zhongzhou.Excavator.dataIndex.service.exportService.excelTemplate.POIUtil;
import com.zhongzhou.Excavator.dataIndex.service.exportService.excelTemplate.TemplateContext;

public class RowCopy implements ExcelTemplateRule{
	
	public String dataExpression;
	
	public List<ExcelTemplateRule> innerRules = new ArrayList<ExcelTemplateRule>();;
	
	private Row templateRow;
	
	public RowCopy( Row templateRow ) throws ExportTemplateException {
		if( templateRow == null ){
			throw new ExportTemplateException( "templateRow in RowCopy template should not be null." );
		}
		this.templateRow = templateRow;
	}
	
	private void attachCellData( Row targetRow , TemplateContext context ) throws ExportTemplateException {
		
		Iterator<Cell> cellIterator = targetRow.cellIterator();
		while( cellIterator.hasNext() ){
			
			Cell cell = cellIterator.next();
			
			if( cell != null && XSSFCell.CELL_TYPE_STRING == cell.getCellType() ){
				
				String stringContent = cell.getStringCellValue();
				
				if( stringContent.toLowerCase().trim().startsWith("$") ){
					
					try {
						Object data = null;
						String path = stringContent.replace("$", "").trim();
						String variableName = ExportUtil.getFirstMatch( path, "\\S+?(?=(\\.+|$))"  );
						
						if( context.getVisableData( variableName ) != null ){
							data = context.getVisableData( variableName );
							path = ExportUtil.replaceFirst(path, variableName + "(\\.+|$)",""  );
						}else{
							data = context.getVisableData( "" );
						}
						
						Object value = ExportUtil.getData( data , path );
						
						if( value != null ){
							cell.setCellValue( value.toString() );
						}else{
							cell.setCellValue( "" );
						}
					} catch (NoSuchFieldException | SecurityException | IllegalArgumentException| IllegalAccessException e) {
						throw new ExportTemplateException(e);
					}
				}
			}
		}
	}
	
	@Override
	public void render( TemplateContext context ) throws ExportTemplateException {
		
		Sheet templateSheeet = context.getTemplateSheeet();
		Sheet targetSheeet   = context.getNewSheeet();
		Integer rowStart = context.getLastRowNum() + 1;
		Row targetRow = targetSheeet.getRow( rowStart );
		if( targetRow == null ){
			targetRow = targetSheeet.createRow( rowStart );
		}
		
		int columnOffset = context.getColumnOffset();
		POIUtil.copyRow(
			targetRow, templateRow, 
			targetSheeet.getWorkbook() , templateSheeet.getWorkbook() , 
			targetSheeet.createDrawingPatriarch(), new HashMap<String,CellStyle>() , columnOffset );
		
		this.attachCellData( targetRow , context );
		
		if( innerRules != null && innerRules.size() > 0 ){
			for( ExcelTemplateRule rule : innerRules ){
				
				rule.render( context );
			}
		}else{
			context.setLastRowNum( rowStart );
		}
	}
	
	@Override
	public void addInnerRule( ExcelTemplateRule rule )throws ExportTemplateException{
		innerRules.add(rule);
	}
}
