package com.zhongzhou.Excavator.springsupport.mongo;

import org.mongodb.morphia.Morphia;
import org.springframework.beans.factory.config.AbstractFactoryBean;

import com.mongodb.MongoClientOptions;
import com.mongodb.ReadPreference;
import com.mongodb.WriteConcern;

public class MongoClientOptionsFactoryBean extends AbstractFactoryBean<MongoClientOptions>  {
	
	
	private boolean socketKeepAlive = false; // 是否保持长链接
	private int connectTimeout = 10000;
	private int socketTimeout = 15000; // read数据超时时间
	
	private int connectionsPerHost = 20; // 每个地址最大请求数
	private int maxWaitTime = 1000 * 60 * 2; // 长链接的最大等待时间
	private int threadsAllowedToBlockForConnectionMultiplier = 5; // 一个socket最大的等待请求数

	private boolean cursorFinalizerEnabled = true;//自动设置一个finalizer，关掉可能未关闭的Mongo Cursor
	
	private WriteConcern writeConcern = WriteConcern.SAFE;
	private ReadPreference readPreference = ReadPreference.primary(); // 最近优先策略

	@Override
    protected MongoClientOptions createInstance() throws Exception {
    	
		MongoClientOptions options = new MongoClientOptions.Builder()
				.socketKeepAlive( this.socketKeepAlive ) // 是否保持长链接
				.connectTimeout( this.connectTimeout ) // 链接超时时间
				.socketTimeout( this.socketTimeout ) // read数据超时时间
				.readPreference( this.readPreference ) // 最近优先策略
				.connectionsPerHost( this.connectionsPerHost ) // 每个地址最大请求数
				.maxWaitTime( this.maxWaitTime ) // 长链接的最大等待时间
				.threadsAllowedToBlockForConnectionMultiplier( this.threadsAllowedToBlockForConnectionMultiplier ) // 一个socket最大的等待请求数
				.writeConcern( this.writeConcern )
				.cursorFinalizerEnabled( this.cursorFinalizerEnabled )
				.build();
		
        return options;
    }

    @Override
    public Class<?> getObjectType() {
        return MongoClientOptions.class;
    }
    
    @Override
    public void afterPropertiesSet() throws Exception {
    	
        super.afterPropertiesSet();
    }

	public boolean isSocketKeepAlive() {
		return socketKeepAlive;
	}

	public void setSocketKeepAlive(boolean socketKeepAlive) {
		this.socketKeepAlive = socketKeepAlive;
	}

	public int getConnectTimeout() {
		return connectTimeout;
	}

	public void setConnectTimeout(int connectTimeout) {
		this.connectTimeout = connectTimeout;
	}

	public int getSocketTimeout() {
		return socketTimeout;
	}

	public void setSocketTimeout(int socketTimeout) {
		this.socketTimeout = socketTimeout;
	}

	public int getConnectionsPerHost() {
		return connectionsPerHost;
	}

	public void setConnectionsPerHost(int connectionsPerHost) {
		this.connectionsPerHost = connectionsPerHost;
	}

	public int getMaxWaitTime() {
		return maxWaitTime;
	}

	public void setMaxWaitTime(int maxWaitTime) {
		this.maxWaitTime = maxWaitTime;
	}

	public int getThreadsAllowedToBlockForConnectionMultiplier() {
		return threadsAllowedToBlockForConnectionMultiplier;
	}

	public void setThreadsAllowedToBlockForConnectionMultiplier(int threadsAllowedToBlockForConnectionMultiplier) {
		this.threadsAllowedToBlockForConnectionMultiplier = threadsAllowedToBlockForConnectionMultiplier;
	}

	public boolean isCursorFinalizerEnabled() {
		return cursorFinalizerEnabled;
	}

	public void setCursorFinalizerEnabled(boolean cursorFinalizerEnabled) {
		this.cursorFinalizerEnabled = cursorFinalizerEnabled;
	}

	public WriteConcern getWriteConcern() {
		return writeConcern;
	}

	public void setWriteConcern(WriteConcern writeConcern) {
		this.writeConcern = writeConcern;
	}

	public ReadPreference getReadPreference() {
		return readPreference;
	}

	public void setReadPreference(ReadPreference readPreference) {
		this.readPreference = readPreference;
	}
}
