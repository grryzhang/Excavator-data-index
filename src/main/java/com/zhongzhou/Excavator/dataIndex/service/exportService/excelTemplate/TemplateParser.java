package com.zhongzhou.Excavator.dataIndex.service.exportService.excelTemplate;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;
import org.springframework.stereotype.Service;

import com.zhongzhou.Excavator.dataIndex.Exception.ExportTemplateException;
import com.zhongzhou.Excavator.dataIndex.service.exportService.excelTemplate.rule.ListAttach;
import com.zhongzhou.Excavator.dataIndex.service.exportService.excelTemplate.rule.ObjectExpand;
import com.zhongzhou.Excavator.dataIndex.service.exportService.excelTemplate.rule.RowCopy;
import com.zhongzhou.Excavator.dataIndex.service.exportService.excelTemplate.rule.SheetCopy;

/**
 * TODO 要为 context 设置全局变量( innerRule 也许需要看到上层的数据 )
 * @author zhanghuanping
 *
 */
@Service
public class TemplateParser {
	
	public class TestData {
		
		public String data1 = "111";
		public String data2 = "222";
		
		public Corp corp = new Corp();
		
		public List<Wheel> wheels = new ArrayList<Wheel>( Arrays.asList( new Wheel(), new Wheel() ) );
	}
	
	public class Corp {
		
		public String name = "corp's name";
	}
	
	public class Wheel {
		
		public String name = "wheel's name";
	}

	@Test
	public void createExcelByTemplate() throws IOException, ExportTemplateException{
		
		File fileOutput = new File( "D://temp//new.xlsx" );
		if( !fileOutput.exists() ){
			fileOutput.createNewFile();
		}
		FileOutputStream out = new FileOutputStream( fileOutput );
		
		XSSFWorkbook newWorkbook = new XSSFWorkbook();
		
		List<Object> testData = new ArrayList( Arrays.asList( new TestData() , new TestData() ) );
		
		FileInputStream xlsxFileInput = new FileInputStream("D://temp//template.xlsx");
		XSSFWorkbook templateWorkbook = new XSSFWorkbook( xlsxFileInput );
		
		Iterator<Sheet> iteratorSheet = templateWorkbook.sheetIterator();
		while( iteratorSheet.hasNext() ) {
			
			Sheet templateSheet = iteratorSheet.next();
			
			ExcelTemplateRule tempalteRule = this.parseXlsxTemplate( templateSheet );
			
			Sheet newSheet = newWorkbook.getSheet( templateSheet.getSheetName() );
			if( newSheet == null ){
				newSheet = newWorkbook.createSheet( templateSheet.getSheetName() );
			}
			
			TemplateContext context = new TemplateContext(
					templateWorkbook, 
					templateSheet,  
					newWorkbook , 
					newSheet );
			context.setVisableData("", testData);
			
			tempalteRule.render(context);
		}

		newWorkbook.write(out);
		newWorkbook.close();
		
		out.flush();
		out.close();
		
		templateWorkbook.close();
		xlsxFileInput.close();
	}

	private ExcelTemplateRule parseXlsxTemplate( Sheet templateSheet ) throws IOException, ExportTemplateException{
		
		Stack<ExcelTemplateRule> listTemplateStack = new Stack<ExcelTemplateRule>();
		
		ExcelTemplateRule templateRule = new SheetCopy();
		listTemplateStack.push( templateRule );
		
		Iterator<Row> rowIterator = templateSheet.rowIterator();

		while( rowIterator.hasNext() ) {
			
			Row row = rowIterator.next();
			
			ExcelTemplateRule newRule = null;
			
			if( row != null ){
				
				Cell cell = row.getCell(0);
				
				boolean ruleRow = false;
				if( cell != null && XSSFCell.CELL_TYPE_STRING == cell.getCellType() ){

					String stringContent = cell.getStringCellValue();
					if( stringContent != null ){
						if( stringContent.toLowerCase().contains( "#template-list-start" ) ){
							
							ruleRow = true;
							
							ListAttach listTemplate = new ListAttach();
							
							listTemplate.dataExpression = 
									ExportUtil.getFirstMatch( 
											stringContent, 
											"\\(\\s*let\\s+\\S+\\s+as\\s+\\$+.*\\)"
									);
							
							newRule = listTemplate;
							
							listTemplateStack.peek().addInnerRule( newRule );
							listTemplateStack.push( listTemplate );
							
						}else if( stringContent.toLowerCase().contains( "#template-list-end" )  ){
							
							ruleRow = true;
							
							ExcelTemplateRule popOne = listTemplateStack.pop();
							
						}else if( stringContent.toLowerCase().contains( "#template-Object-start" )  ){
							
							ruleRow = true;
							
							ObjectExpand objectExpandTemplate = new ObjectExpand();
							
							objectExpandTemplate.dataExpression = 
									ExportUtil.getFirstMatch( 
											stringContent, 
											"\\(\\s*let\\s+\\S+\\s+as\\s+\\$+.*\\)"
									);
							
							newRule = objectExpandTemplate;
							
							listTemplateStack.peek().addInnerRule( newRule );
							listTemplateStack.push( objectExpandTemplate );
							
						}else if( stringContent.toLowerCase().contains( "#template-Object-end" )  ){
							
							ruleRow = true;
							
							ExcelTemplateRule popOne = listTemplateStack.pop();
						}
					}
				}	
				
				/* default is row copy */
				if( !ruleRow ){
					newRule = new RowCopy( row );
					listTemplateStack.peek().addInnerRule( newRule );
				}
			}
		}	

		return templateRule;
	}
}
