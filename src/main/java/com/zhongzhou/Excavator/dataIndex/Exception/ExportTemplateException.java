package com.zhongzhou.Excavator.dataIndex.Exception;

public class ExportTemplateException extends Exception {

	public ExportTemplateException(String message){
		super(message);
	}

	public ExportTemplateException(String message,Throwable cause){
		
		super(message,cause);
	}

	public ExportTemplateException(Throwable cause){
		
		super(cause);
	}
}
