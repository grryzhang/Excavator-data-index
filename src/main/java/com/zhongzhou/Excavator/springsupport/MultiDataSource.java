package com.zhongzhou.Excavator.springsupport;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class MultiDataSource  extends AbstractRoutingDataSource {

	private static ThreadLocal<String> dataSourceKey = new InheritableThreadLocal<String>();

    public static void setDataSourceKey(String dataSource) {
        dataSourceKey.set(dataSource);
    }

    @Override
    protected Object determineCurrentLookupKey() {
        return dataSourceKey.get();
    }
}
