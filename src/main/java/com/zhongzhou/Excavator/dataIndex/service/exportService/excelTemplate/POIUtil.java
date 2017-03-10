package com.zhongzhou.Excavator.dataIndex.service.exportService.excelTemplate;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFComment;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.zhongzhou.Excavator.dataIndex.Exception.ExportTemplateException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Comment;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFSheet;

/**
 * POI工具类 功能点： 
 * 1、实现excel的sheet复制，复制的内容包括单元的内容、样式、注释
 * 2、setMForeColor修改XSSFColor.YELLOW的色值，setMBorderColor修改PINK的色值
 * 
 * @author Administrator
 */
public final class POIUtil {

	/**
	 * 功能：拷贝sheet
	 * 实际调用 	copySheet(targetSheet, sourceSheet, targetWork, sourceWork, true)
	 * @param targetSheet
	 * @param sourceSheet
	 * @param targetWork
	 * @param sourceWork                                                                   
	 */
	public static void copySheet(
			XSSFSheet targetSheet, 
			XSSFSheet sourceSheet,
			XSSFWorkbook targetWork, 
			XSSFWorkbook sourceWork) throws Exception{
		if(targetSheet == null || sourceSheet == null || targetWork == null || sourceWork == null){
			throw new IllegalArgumentException("targetSheet/sourceSheet/sourceRow/sourceWork is null when try to copySheet.");
		}
		copySheet(targetSheet, sourceSheet, targetWork, sourceWork, true);
	}

	/**
	 * 功能：拷贝sheet
	 * @param targetSheet
	 * @param sourceSheet
	 * @param targetWork
	 * @param sourceWork
	 * @param copyStyle					boolean 是否拷贝样式
	 */
	public static void copySheet(
			XSSFSheet targetSheet, 
			XSSFSheet sourceSheet,
			XSSFWorkbook targetWork, 
			XSSFWorkbook sourceWork, 
			boolean copyStyle)throws Exception {
		
		if(targetSheet == null || sourceSheet == null || targetWork == null || sourceWork == null){
			throw new IllegalArgumentException("targetRow/sourceRow/sourceRow/sourceWork is null when try to copyRow.");
		}
		
		//复制源表中的行
		int maxColumnNum = 0;

		Map<String,CellStyle> styleMap = (copyStyle) ? new HashMap<String,CellStyle>() : null;
		
		XSSFDrawing targetDrawing = targetSheet.createDrawingPatriarch(); //用于复制注释
		for (int i = sourceSheet.getFirstRowNum(); i <= sourceSheet.getLastRowNum(); i++) {
			XSSFRow sourceRow = sourceSheet.getRow(i);
			XSSFRow targetRow = targetSheet.createRow(i);
			
			if (sourceRow != null) {
				copyRow(targetRow, sourceRow,targetWork, sourceWork, targetDrawing, styleMap);
				if (sourceRow.getLastCellNum() > maxColumnNum) {
					maxColumnNum = sourceRow.getLastCellNum();
				}
			}
		}
		
		//复制源表中的合并单元格
		mergerRegion(targetSheet, sourceSheet);
		
		//设置目标sheet的列宽
		for (int i = 0; i <= maxColumnNum; i++) {
			targetSheet.setColumnWidth(i, sourceSheet.getColumnWidth(i));
		}
	}
	
	/**
	 * 功能：拷贝row
	 * @param targetRow
	 * @param sourceRow
	 * @param styleMap
	 * @param targetWork
	 * @param sourceWork
	 * @param targetPatriarch
	 */
	public static void copyRow(
			Row targetRow, 
			Row sourceRow,
			XSSFWorkbook targetWork,
			XSSFWorkbook sourceWork,
			XSSFDrawing targetDrawing, 
			Map<String,CellStyle> styleMap) throws ExportTemplateException {
		
		POIUtil.copyRow( targetRow, sourceRow, sourceWork, sourceWork, targetDrawing, styleMap, 0 );
	}
	
	public static void copyRow(
			Row targetRow, 
			Row sourceRow,
			Workbook targetWork,
			Workbook sourceWork,
			Drawing targetDrawing, 
			Map<String,CellStyle> styleMap,
			Integer columnOffset) throws ExportTemplateException {
		
		if(targetRow == null || sourceRow == null || targetWork == null || sourceWork == null || targetDrawing == null){
			throw new IllegalArgumentException("targetRow/sourceRow/sourceRow/sourceWork is null when try to copyRow.");
		}
		
		if( columnOffset == null ) columnOffset = 0;
		
		//设置行高
		targetRow.setHeight(sourceRow.getHeight());
		
		Iterator<Cell> sourceCellIterator = sourceRow.cellIterator();
		while( sourceCellIterator.hasNext() ){
			Cell sourceCell = sourceCellIterator.next();
			
			if( sourceCell != null && sourceCell.getColumnIndex() > columnOffset + 1 ){
				Cell targetCell = targetRow.getCell( sourceCell.getColumnIndex() + columnOffset );
				if( targetCell == null ){
					targetCell = targetRow.createCell( sourceCell.getColumnIndex() + columnOffset );
				}
				
				copyCell(targetCell, sourceCell, targetWork, sourceWork, styleMap);
				copyComment(targetCell,sourceCell,targetDrawing);
			}
		}
	}
	
	/**
	 * 功能：拷贝cell，依据styleMap是否为空判断是否拷贝单元格样式
	 * @param targetCell			不能为空
	 * @param sourceCell			不能为空
	 * @param targetWork			不能为空
	 * @param sourceWork			不能为空
	 * @param styleMap				可以为空				
	 */
	public static void copyCell(
			Cell targetCell, 
			Cell sourceCell, 
			Workbook targetWork, 
			Workbook sourceWork,
			Map<String,CellStyle> styleMap) {
		
		if(targetCell == null || sourceCell == null || targetWork == null || sourceWork == null ){
			throw new IllegalArgumentException("targetCell/sourceCell/targetWork/sourceWork is null when try to copyCell.");
		}
		
		//处理单元格样式
		if(styleMap != null){
			if (targetWork == sourceWork) {
				targetCell.setCellStyle(sourceCell.getCellStyle());
			} else {
				String stHashCode = "" + sourceCell.getCellStyle().hashCode();
				CellStyle targetCellStyle = styleMap.get(stHashCode);
				if (targetCellStyle == null) {
					targetCellStyle = targetWork.createCellStyle();
					targetCellStyle.cloneStyleFrom(sourceCell.getCellStyle());
					styleMap.put(stHashCode, targetCellStyle);
				}
				
				targetCell.setCellStyle(targetCellStyle);
			}
		}
		
		//处理单元格内容
		switch ( sourceCell.getCellType() ) {
		case XSSFCell.CELL_TYPE_STRING:
			targetCell.setCellValue(sourceCell.getRichStringCellValue());
			break;
		case XSSFCell.CELL_TYPE_NUMERIC:
			targetCell.setCellValue(sourceCell.getNumericCellValue());
			break;
		case XSSFCell.CELL_TYPE_BLANK:
			targetCell.setCellType(XSSFCell.CELL_TYPE_BLANK);
			break;
		case XSSFCell.CELL_TYPE_BOOLEAN:
			targetCell.setCellValue(sourceCell.getBooleanCellValue());
			break;
		case XSSFCell.CELL_TYPE_ERROR:
			targetCell.setCellErrorValue(sourceCell.getErrorCellValue());
			break;
		case XSSFCell.CELL_TYPE_FORMULA:
			targetCell.setCellFormula(sourceCell.getCellFormula());
			break;
		default:
			break;
		}
	}

	
	/**
	 * 功能：拷贝comment
	 * @param targetCell
	 * @param sourceCell
	 * @param targetPatriarch
	 */
	public static void copyComment(
			Cell targetCell,
			Cell sourceCell,
			Drawing targetDrawing)throws ExportTemplateException{
		
		if(targetCell == null || sourceCell == null || targetDrawing == null){
			throw new IllegalArgumentException("targetCell or sourceCell is null when try to copyComment. ");
		}
		
		//处理单元格注释
		Comment comment = sourceCell.getCellComment();
		if(comment != null){
			Comment newComment = targetDrawing.createCellComment(new XSSFClientAnchor());
			newComment.setAuthor(comment.getAuthor());
			newComment.setColumn(comment.getColumn());
			newComment.setRow(comment.getRow());
			newComment.setString(comment.getString());
			newComment.setVisible(comment.isVisible());
			targetCell.setCellComment(newComment);
		}
	}
	
	/**
	 * 功能：复制原有sheet的合并单元格到新创建的sheet
	 * 
	 * @param sheetCreat
	 * @param sourceSheet
	 */
	public static void mergerRegion(XSSFSheet targetSheet, XSSFSheet sourceSheet)throws Exception {
		if(targetSheet == null || sourceSheet == null){
			throw new IllegalArgumentException("targetSheet or sourceSheet is null when try to mergerRegion. ");
		}
		
		for (int i = 0; i < sourceSheet.getNumMergedRegions(); i++) {
			CellRangeAddress oldRange = sourceSheet.getMergedRegion(i);
			CellRangeAddress newRange = new CellRangeAddress(
				oldRange.getFirstRow(), oldRange.getLastRow(),
				oldRange.getFirstColumn(), oldRange.getLastColumn());
			targetSheet.addMergedRegion(newRange);
		}
	}
}