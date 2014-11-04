/**
 * 
 */
package com.vanstone.centralserver.business.sdk.weixin;

import java.util.Date;

/**
 * 微信公众平台配置信息
 * @author shipengpipi@126.com
 */
public interface IWeixinInfo {
	
	/**
	 * 引用ID，同Appname
	 * @return
	 */
	String getId() ;

    /**
     * 引用ID，同Appname
     * @param id
     */
    void setId(String id) ;

    /**
     * 获取Appid
     * @return
     */
    String getAppid() ;

    /**
     * 设定Appid
     * @param appid
     */
    void setAppid(String appid);

    /**
     * 获取AppSecret
     * @return
     */
    String getAppSecret() ;

    /**
     * 设定AppSecret
     * @param appSecret
     */
    void setAppSecret(String appSecret) ;

    /**
     * 获取AccessTokenMD5值
     * @return
     */
    String getAccessTokenMd5() ;

    /**
     * 获取Syst Insert Time
     * @return
     */
    Date getSysInsertTime() ;

    /**
     * 获取Sys Update Time
     * @return
     */
    Date getSysUpdateTime();

    /**
     * 获取Last Retrieval Token Time
     * @return
     */
    Date getLastRetrievalTokenTime() ;

    /**
     * 设定LastRetrieval Token Time
     * @param lastRetrievalTokenTime
     */
    void setLastRetrievalTokenTime(Date lastRetrievalTokenTime) ;
    
    /**
     * 获取描述信息
     * @return
     */
    String getContent();

    /**
     * 设定描述信息
     * @param content
     */
    void setContent(String content) ;

    /**
     * 获取Access Token信息
     * @return
     */
    String getAccessToken() ;

}
