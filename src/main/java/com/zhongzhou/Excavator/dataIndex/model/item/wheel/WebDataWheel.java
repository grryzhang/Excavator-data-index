package com.zhongzhou.Excavator.dataIndex.model.item.wheel;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;

import com.zhongzhou.Excavator.dataIndex.model.WebDataMongoData;

@Entity( value="web_data_wheel" , noClassnameStored=true )
public class WebDataWheel implements WebDataMongoData<Wheel>{

	private long insertTime ;
	
    private String modelClassName;
    
    @Embedded( concreteClass = Wheel.class )
    private Wheel data;

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
    public Wheel getData() {
        return data;
    }

    @Override
    public void setData( Wheel data ){
        this.data = data;
        this.modelClassName = data.getClass().getName();
    }
}
