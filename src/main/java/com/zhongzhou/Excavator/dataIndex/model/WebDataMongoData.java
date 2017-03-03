package com.zhongzhou.Excavator.dataIndex.model;

import org.eclipse.jetty.util.Fields;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Reference;

import javax.xml.crypto.Data;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
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
public interface WebDataMongoData<T>{

    public long getInsertTime();

    public void setInsertTime(long insertTime);

    public String getModelClassName();

    public void setModelClassName(String modelClassName);
    
    public T getData();
    
    public void setData( T data );
}
