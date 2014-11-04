/**
 * 
 */
package com.vanstone.weixin.client.taglib;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.vanstone.centralserver.common.weixin.wrap.oauth2.Scope;
import com.vanstone.weixin.client.IWeixinAPIManager;
import com.vanstone.weixin.client.WeixinClientFactory;

/**
 * 微信Tag支持
 * @author shipeng
 */
public class WeixinClientTag extends TagSupport {

	/***/
	private static final long serialVersionUID = -7292667863052451558L;
	
	private String appname;
	private String redirectUrl; 
	private boolean baseInfo = true;
	private String state;
	private String domain;
	
	@Override
	public int doStartTag() throws JspException {
		if (appname == null || appname.equals("") || redirectUrl == null || redirectUrl.equals("")) {
			throw new IllegalArgumentException("Appname or Redirect URL is Null.");
		}
		if (!redirectUrl.startsWith("/")) {
			redirectUrl = "/" + redirectUrl;
		}
		if (domain != null && !domain.equals("")) {
			redirectUrl = domain + redirectUrl;
		}
		if (!redirectUrl.startsWith("http")) {
			redirectUrl = "http://" + redirectUrl;
		}
		return super.doStartTag();
	}
	
	@Override
	public int doEndTag() throws JspException {
		IWeixinAPIManager weixinAPIManager = WeixinClientFactory.getWeixinAPIManager();
		String url = weixinAPIManager.getOAuth2Url(appname, redirectUrl, baseInfo ? Scope.snsapi_base : Scope.snsapi_userinfo, state);
		try {
			this.pageContext.getOut().write(url);
		} catch (IOException e) {
			e.printStackTrace();
			throw new IllegalArgumentException(e);
		}
		return super.doEndTag();
	}
	
	public String getAppname() {
		return appname;
	}

	public void setAppname(String appname) {
		this.appname = appname;
	}

	public String getRedirectUrl() {
		return redirectUrl;
	}

	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}

	public boolean isBaseInfo() {
		return baseInfo;
	}

	public void setBaseInfo(boolean baseInfo) {
		this.baseInfo = baseInfo;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}
	
}
