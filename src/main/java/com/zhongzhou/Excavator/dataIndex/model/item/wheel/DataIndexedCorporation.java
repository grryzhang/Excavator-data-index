package com.zhongzhou.Excavator.dataIndex.model.item.wheel;

import java.util.List;
import java.util.Map;

import org.mongodb.morphia.annotations.Entity;

import com.zhongzhou.Excavator.dataIndex.model.IndexIntermediateResult;

@Entity( value="index_intermediate_result_corporation" , noClassnameStored=true )
public class DataIndexedCorporation implements IndexIntermediateResult<DataIndexedCorporation>{
	
	public static final String type = "data_index_corporation";
	
	public String corpId; // suggest to be it's URL

	public Double corpScore;
	
	public Corporation corp;
	
	public List<String> wheelIDs;
	
	public Map<String, Double> scorExplain;
	
	private String indexId;
	
	private String indexCondition;
	
	private String dataType;
	
	private long createDate;

	public String getIndexId() {
		return indexId;
	}

	public void setIndexId(String indexId) {
		this.indexId = indexId;
	}

	public String getIndexCondition() {
		return indexCondition;
	}

	public void setIndexCondition(String indexCondition) {
		this.indexCondition = indexCondition;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public long getCreateDate() {
		return createDate;
	}

	public void setCreateDate(long createDate) {
		this.createDate = createDate;
	}
}
