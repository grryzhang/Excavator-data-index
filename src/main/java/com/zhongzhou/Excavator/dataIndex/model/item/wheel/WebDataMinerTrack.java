package com.zhongzhou.Excavator.dataIndex.model.item.wheel;

import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;

import com.zhongzhou.Excavator.dataIndex.model.WebDataTrackMongoData;

@Entity( value="web_miner_tracks" , noClassnameStored = true)
public class WebDataMinerTrack implements WebDataTrackMongoData<DataSourceTrack>{

	private long insertTime ;
	
    private String modelClassName;
    
    @Embedded( concreteClass = DataSourceTrack.class )
    private DataSourceTrack data;

    @Override
    public long getInsertTime() {
        return insertTime;
    }

    @Override
    public void setInsertTime(long insertTime) {
        this.insertTime = insertTime;
    }

    @Override
    public String getModelClass() {
        return modelClassName;
    }

    @Override
    public void setModelClass(String modelClassName) {
        this.modelClassName = modelClassName;
    }

    @Override
    public DataSourceTrack getData() {
        return data;
    }

    @Override
    public void setData( DataSourceTrack data ){
        this.data = data;
        this.modelClassName = data.getClass().getName();
    }
	
}
