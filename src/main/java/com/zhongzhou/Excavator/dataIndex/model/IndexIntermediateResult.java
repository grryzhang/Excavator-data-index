package com.zhongzhou.Excavator.dataIndex.model;

public interface IndexIntermediateResult<T> {

	public String getIndexId();
	public void   setIndexId(String indexId);

	public String getIndexCondition() ;
	public void setIndexCondition(String indexCondition);

	public String getDataType();
	public void setDataType(String resultType);
	
	public long getCreateDate();
	public void setCreateDate( long date );
}
