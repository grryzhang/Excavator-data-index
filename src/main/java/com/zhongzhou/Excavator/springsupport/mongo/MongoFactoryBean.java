package com.zhongzhou.Excavator.springsupport.mongo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.config.AbstractFactoryBean;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ReadPreference;
import com.mongodb.ServerAddress;
import com.mongodb.WriteConcern;

public class MongoFactoryBean extends AbstractFactoryBean<Mongo> {

	// 表示服务器列表(主从复制或者分片)的字符串数组
	private String serverStrings;
	// 表示Username,password,database字符串
	private String credentialStrings;
	// mongoDB配置对象
	private MongoClientOptions mongoOptions;
	// 是否主从分离(读取从库)，默认读写都在主库
	private boolean readSecondary = false;
	// 设定写策略(出错时是否抛异常)，默认采用SAFE模式(需要抛异常)
	private WriteConcern writeConcern = WriteConcern.SAFE;
	
	@Override
	public Class<?> getObjectType() {
	    return Mongo.class;
	}
	
	@Override
	protected MongoClient createInstance() throws Exception {
		
		MongoClient mongoClinet = initMongo();
	    // 设定主从分离
	    if (readSecondary) {
	    	mongoClinet.setReadPreference(ReadPreference.secondaryPreferred());
	    }
	
	    // 设定写策略
	    mongoClinet.setWriteConcern(writeConcern);
	    
	    mongoClinet.getDatabase("test");
	    
	    return mongoClinet;
	}
	
	/**
	 * 初始化mongo实例
	 * @return
	 * @throws Exception
	 */
	private MongoClient initMongo() throws Exception {
		
	    // 根据条件创建Mongo实例
		
		MongoClient mongoClient = null;
	    List<ServerAddress> serverList = this.getServerList();
	    List<MongoCredential> credentialList = this.getCredentialList();

	    if( credentialList != null && credentialList.size() > 0 ){
	    	
	    	if (mongoOptions != null) {
	        	
		    	//If the serverList is a mult-object list, then it is used for support replica set 
		    	// https://docs.mongodb.org/manual/replication/
		    	mongoClient = new MongoClient( serverList, credentialList, mongoOptions );
		    	
		    }else{
		    	
		    	mongoClient = new MongoClient( serverList, credentialList);
		    }
	    }else if (mongoOptions != null) {

		    mongoClient = new MongoClient( serverList, mongoOptions );
		    	
	    }else{
	    	
	    	mongoClient = new MongoClient( serverList );
	    }

	    return mongoClient;
	}
	
	
	/**
	 * 根据服务器字符串列表，解析出服务器对象列表
	 * <p>
	 * 
	 * @Title: getServerList
	 *         </p>
	 * 
	 * @return
	 * @throws Exception
	 */
	private List<ServerAddress> getServerList() throws IllegalArgumentException,Exception {
		
	    List<ServerAddress> serverList = new ArrayList<ServerAddress>();
	    
	    try {
	    	
	    	String[] servers = serverStrings.split("\\|");
            
            if ( servers.length < 1 ) {
            	
                throw new IllegalArgumentException(
                        "No valided server address: " + serverStrings);
                
            } else {
            	
            	for( String server : servers ){
            		
            		String[] hostParameters = serverStrings.split(":");
            		
            		if( hostParameters.length > 0 && hostParameters[0] != null ){
            			if( hostParameters[1] != null ){
            				serverList.add( new ServerAddress( hostParameters[0] , Integer.parseInt( hostParameters[1] ) ) );
            			}
            		}
            	}
            }
	            
	        return serverList;
	        
	    } catch (Exception e) {
	    	
	        throw new Exception( "Error while converting host properties to ServerAddressList", e);
	    }
	}
	
	private List<MongoCredential> getCredentialList() throws IllegalArgumentException,Exception {
		
	    List<MongoCredential> credentialList = new ArrayList<MongoCredential >();
	    
	    try {
	    	
	    	String[] credentials = credentialStrings.split("\\|");
            
            if ( credentials.length < 1 ) {
            	
                return null;
                
            } else {
            	
            	for( String credential : credentials ){
            		
            		String[] credentialParameters = credential.split(":");
            		
            		if( credentialParameters.length == 3 ){
            			credentialList.add( MongoCredential.createCredential( credentialParameters[0] , credentialParameters[1] , credentialParameters[2].toCharArray() ) );
            		}else{
            			 throw new IllegalArgumentException(
                                 "Not valided server credential: " + credential + ", in properties.");
            		}
            	}
            }
	            
	        return credentialList;
	        
	    } catch (Exception e) {
	    	
	        throw new Exception( "Error while converting host properties to ServerAddressList", e);
	    }
	}
	
	/* ------------------- setters --------------------- */
	public String getServerStrings() {
		return serverStrings;
	}

	public void setServerStrings(String serverStrings) {
		this.serverStrings = serverStrings;
	}

	public String getCredentialStrings() {
		return credentialStrings;
	}

	public void setCredentialStrings(String credentialStrings) {
		this.credentialStrings = credentialStrings;
	}

	public MongoClientOptions getMongoOptions() {
		return mongoOptions;
	}

	public void setMongoOptions(MongoClientOptions mongoOptions) {
		this.mongoOptions = mongoOptions;
	}

	public boolean isReadSecondary() {
		return readSecondary;
	}

	public void setReadSecondary(boolean readSecondary) {
		this.readSecondary = readSecondary;
	}

	public WriteConcern getWriteConcern() {
		return writeConcern;
	}

	public void setWriteConcern(WriteConcern writeConcern) {
		this.writeConcern = writeConcern;
	}
	
}