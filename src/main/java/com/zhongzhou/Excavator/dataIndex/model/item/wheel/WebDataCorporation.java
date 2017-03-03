package com.zhongzhou.Excavator.dataIndex.model.item.wheel;

import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;

import com.zhongzhou.Excavator.dataIndex.model.WebDataMongoData;

@Entity( value="web_data_corporation" , noClassnameStored=true )
public class WebDataCorporation implements WebDataMongoData<Corporation>{

	private long insertTime ;
	
    private String modelClassName;
    
    @Embedded( concreteClass = Corporation.class )
    private Corporation data;

    @Override
    public long getInsertTime() {
        return insertTime;
    }

    @Override
    public void setInsertTime(long insertTime) {
        this.insertTime = insertTime;
    }

    @Override
    public String getModelClassName() {
        return modelClassName;
    }

    @Override
    public void setModelClassName(String modelClassName) {
        this.modelClassName = modelClassName;
    }

    @Override
    public Corporation getData() {
        return data;
    }

    @Override
    public void setData( Corporation data ){
        this.data = data;
        this.modelClassName = data.getClass().getName();
    }
}
