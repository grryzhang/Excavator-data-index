package com.zhongzhou.Excavator.dataIndex.model.item.wheel;

import java.util.Date;

/**
 * Created by lixiaohao on 2016/11/10
 *
 * @Description 记录产品、公司的链接地址
 * @Create 2016-11-10 17:17
 * @Company
 */
public class DataSourceTrack {
    private String id;
    /**该数据所在连接*/
    private long insertTime;
    private String originalUrl;
    private String parentUrl;
    private String productUrl;
    private String corporationUrl;
    private String modelClassName;
    private Class modelClass;
    /**数据原始网站*/
    private String dataSource;
    /**当前页码*/
    private int currentPage;
    /**当前页下的索引*/
    private int index;
    public DataSourceTrack() {
        this.id = java.util.UUID.randomUUID().toString();
        this.insertTime = new Date().getTime();
    }

    public String getId() {
        return id;
    }

    public String getParentUrl() {
        return parentUrl;
    }

    public long getInsertTime() {
        return insertTime;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }

    public void setParentUrl(String parentUrl) {
        this.parentUrl = parentUrl;
    }

    public String getProductUrl() {
        return productUrl;
    }

    public void setProductUrl(String productUrl) {
        this.productUrl = productUrl;
    }

    public String getCorporationUrl() {
        return corporationUrl;
    }

    public void setCorporationUrl(String corporationUrl) {
        this.corporationUrl = corporationUrl;
    }

    public String getModelClassName() {
        return modelClassName;
    }

    public void setModelClassName(String modelClassName) {
        this.modelClassName = modelClassName;
    }

    public Class getModelClass() {
        return modelClass;
    }

    public void setModelClass(Class modelClass) {
        this.modelClass = modelClass;
    }

    public String getDataSource() {
        return dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
