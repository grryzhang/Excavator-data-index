package com.zhongzhou.Excavator.dataIndex.model;

import org.mongodb.morphia.annotations.Entity;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by lixiaohao on 2016/11/18
 *
 * @Description
 * @Create 2016-11-18 11:04
 * @Company
 */
public interface WebDataTrackMongoData<T> {
    
    public long getInsertTime();

    public void setInsertTime(long insertTime);

    public String getModelClass();

    public void setModelClass(String modelClassName);
    
    public T getData();
    
    public void setData( T data );
}
