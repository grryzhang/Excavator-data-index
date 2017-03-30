package com.zhongzhou.Excavator.dataIndex.service.exportService.excelTemplate.rule;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCell;

import com.zhongzhou.common.util.BeanUtil;

import com.zhongzhou.Excavator.dataIndex.Exception.ExportTemplateException;
import com.zhongzhou.Excavator.dataIndex.service.exportService.excelTemplate.ExcelTemplateRule;
import com.zhongzhou.Excavator.dataIndex.service.exportService.excelTemplate.ExportUtil;
import com.zhongzhou.Excavator.dataIndex.service.exportService.excelTemplate.TemplateContext;

public class ObjectVerticalExpand implements ExcelTemplateRule{
	
	public String dataExpression;
	
	public List<ExcelTemplateRule> innerRules = new ArrayList<ExcelTemplateRule>();
	
	private Map<String, Object> visableData = new HashMap<String, Object>();
	
	private void doDataExpress( Object data ){
		
	}

	@Override
	public void render( TemplateContext context ) throws ExportTemplateException {
		
		String variableName = null;
		String variableDataExpression = null;
		if( dataExpression == null ){
			variableName = "";
			variableDataExpression = "";
		}else{
			
			variableName           = ExportUtil.getFirstMatch( dataExpression , "(?<=let)\\s*\\S+\\s*" );
			variableDataExpression = ExportUtil.getFirstMatch( dataExpression , "(?<=as)\\s*\\S+\\s*" );
			
			if( variableName == null || variableDataExpression == null ){
				throw new ExportTemplateException( 
					"Failed in " + ListAttach.class.toString()
					+ ": data parsing." 
					+ "Wrong data expression:" + dataExpression
					);
			}
			
			variableName = variableName.trim();
			variableDataExpression = variableDataExpression.trim().replace("$", "");
		}
		Object templateData = null;
		try {
			
			String path     = variableDataExpression.replace("$", "").trim();
			String dataName = ExportUtil.getFirstMatch( path, "\\S+?(?=(\\.+|$))"  );
			if( dataName == null ){
				dataName = "";
			}
			
			if( context.getVisableData( dataName ) != null ){
				Object data = context.getVisableData( dataName );
				path = ExportUtil.replaceFirst(path, dataName + "(\\.+|$)",""  );
				templateData = ExportUtil.getData( data , path );
			}
			
		} catch ( NoSuchFieldException | SecurityException | IllegalArgumentException| IllegalAccessException e ) {
			throw new ExportTemplateException( "Failed in " + ListAttach.class.toString() + ": data parsing." , e );
		}
		
		int startRowNum = context.getLastRowNum() + 1;
		
		context.setVisableData( variableName, templateData );
		
		List<ExcelTemplateRule> propagationInnerRules = new ArrayList<ExcelTemplateRule>();
		
		Map<Object, Object> beanMap = null;
		try {
			if( !(templateData instanceof Map) ){
				beanMap = BeanUtil.bean2Map( templateData );
			}else{
				beanMap = (Map<Object, Object>)templateData;
			}
			if( beanMap == null ){
				beanMap = new HashMap<Object, Object>(){{ put("","");}};
			}
		} catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException e) {
			throw new ExportTemplateException(e);
		}
		for( int i=0; i<beanMap.size(); i++ ){
			propagationInnerRules.addAll( this.innerRules );
		}
		
		for( ExcelTemplateRule rule : propagationInnerRules ){
			
			rule.render( context );
		}		
		this.expendObjectData(startRowNum, beanMap, context);
		
		context.removeVisableData( variableName );
	}
	
	private void expendObjectData( int startRowNum , Map<Object, Object> beanMap , TemplateContext context ) throws ExportTemplateException {
		
		//TODO context.setLastRowNum( rowStart );

		Iterator<Object> dataKeyIterator = beanMap.keySet().iterator();
		
		Sheet newSheet = context.getNewSheeet();
		int lastRowNum = newSheet.getLastRowNum();
		Object currentKey = null;
		for( int i = startRowNum ; i<= lastRowNum ; i++ ){
			Row row = newSheet.getRow( i );
			
			if( row != null ){
				
				Iterator<Cell> cellIterator = row.iterator();
				
				while( cellIterator.hasNext() ){
					
					Cell cell = cellIterator.next();
					
					if( cell != null && XSSFCell.CELL_TYPE_STRING == cell.getCellType() ){
						
						String stringContent = cell.getStringCellValue();
						
						if( stringContent.toLowerCase().trim().startsWith("#key")){
							if( dataKeyIterator.hasNext() ){
								currentKey = dataKeyIterator.next();
							}
							cell.setCellValue( currentKey.toString() );
						}
						if( stringContent.toLowerCase().trim().startsWith("#value") ){
							cell.setCellValue( beanMap.get(currentKey) == null ? "" : beanMap.get(currentKey).toString() );
						}
					}
				}
			}
		}
	}
	
	@Override
	public void addInnerRule( ExcelTemplateRule rule )throws ExportTemplateException{
		innerRules.add(rule);
	}
}
