package com.zhongzhou.Excavator.dataIndex.Exception;

public class DataIndexException  extends RuntimeException {
	
	private String exceptionId;

	public DataIndexException(String message){
		super(message);
	}

	public DataIndexException(String message,Throwable cause){
		
		super(message,cause);
	}

	public DataIndexException(Throwable cause){
		
		super(cause);
	}

	public String getExceptionId() {
		return exceptionId;
	}

	public void setExceptionId(String exceptionId) {
		this.exceptionId = exceptionId;
	}
}
