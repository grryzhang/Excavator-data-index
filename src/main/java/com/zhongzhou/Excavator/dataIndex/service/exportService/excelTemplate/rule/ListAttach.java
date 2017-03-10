package com.zhongzhou.Excavator.dataIndex.service.exportService.excelTemplate.rule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.zhongzhou.Excavator.dataIndex.Exception.ExportTemplateException;
import com.zhongzhou.Excavator.dataIndex.service.exportService.excelTemplate.ExcelTemplateRule;
import com.zhongzhou.Excavator.dataIndex.service.exportService.excelTemplate.ExportUtil;
import com.zhongzhou.Excavator.dataIndex.service.exportService.excelTemplate.TemplateContext;

public class ListAttach implements ExcelTemplateRule{
	
	public String dataExpression;
	
	public List<ExcelTemplateRule> innerRules = new ArrayList<ExcelTemplateRule>();
	
	public Integer templateStartNum;
	
	public Integer templateEndNum;
	
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
		if( templateData!= null && !( templateData instanceof List ) ){
			throw new ExportTemplateException( 
				"Failed in " 
				+ ListAttach.class.toString() 
				+ ", target data:"
				+ templateData
				+ "+ is not List."); 
		}
		
		if( innerRules == null || innerRules.size()<=0 ){
			return;
		}
		
		for( Object oneData : (List)templateData ){
			
			for( ExcelTemplateRule rule : innerRules ){
				
				if( variableName.length() > 0 ){
					context.setVisableData( variableName, oneData );
				}
				
				rule.render( context );
				
				if( variableName.length() > 0 ){
					context.removeVisableData( variableName );
				}
			}
		}
	}
	
	@Override
	public void addInnerRule( ExcelTemplateRule rule )throws ExportTemplateException{
		innerRules.add(rule);
	}
}
