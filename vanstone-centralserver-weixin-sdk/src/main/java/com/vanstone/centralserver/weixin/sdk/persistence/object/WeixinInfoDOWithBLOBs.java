package com.vanstone.centralserver.weixin.sdk.persistence.object;

import com.vanstone.centralserver.business.sdk.weixin.IWeixinInfo;

public class WeixinInfoDOWithBLOBs extends WeixinInfoDO  implements IWeixinInfo {
    private String content;

    private String accessToken;

    public String getContent() {
        return content;
    }
    
    public void setContent(String content) {
        this.content = content;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}