package com.zhongzhou.Excavator.dataIndex.service.exportService.excelTemplate;

import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.zhongzhou.Excavator.dataIndex.Exception.ExportTemplateException;

public interface ExcelTemplateRule {

	public void render( TemplateContext context ) throws ExportTemplateException;
	
	public void addInnerRule( ExcelTemplateRule rule )throws ExportTemplateException;
}
