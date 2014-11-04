package com.vanstone.centralserver.weixin.sdk.persistence.object;

import java.util.Date;

public class WeixinInfoDO {
    private String id;

    private String appid;

    private String appSecret;

    private String accessTokenMd5;

    private Date sysInsertTime;

    private Date sysUpdateTime;

    private Date lastRetrievalTokenTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public String getAccessTokenMd5() {
        return accessTokenMd5;
    }

    public void setAccessTokenMd5(String accessTokenMd5) {
        this.accessTokenMd5 = accessTokenMd5;
    }

    public Date getSysInsertTime() {
        return sysInsertTime;
    }

    public void setSysInsertTime(Date sysInsertTime) {
        this.sysInsertTime = sysInsertTime;
    }

    public Date getSysUpdateTime() {
        return sysUpdateTime;
    }

    public void setSysUpdateTime(Date sysUpdateTime) {
        this.sysUpdateTime = sysUpdateTime;
    }

    public Date getLastRetrievalTokenTime() {
        return lastRetrievalTokenTime;
    }

    public void setLastRetrievalTokenTime(Date lastRetrievalTokenTime) {
        this.lastRetrievalTokenTime = lastRetrievalTokenTime;
    }
}