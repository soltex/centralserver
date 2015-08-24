/**
 * 
 */
package com.vanstone.centralserver.common.corp.oauth2;

import org.apache.commons.lang.StringUtils;

/**
 * @author shipeng
 *
 */
public class OAuth2Result {

	private String deviceID;
	private String userID;
	private String openID;
	
	public OAuth2Result(String userID, String openID, String deviceID) {
		this.deviceID = deviceID;
		this.userID = userID;
		this.openID = openID;
	}
	
	public String getDeviceID() {
		return deviceID;
	}

	public String getUserID() {
		return userID;
	}

	public String getOpenID() {
		return openID;
	}
	
	/**
	 * 是否为企业内会员
	 * @return
	 */
	public boolean isCorpMember() {
		if (StringUtils.isEmpty(this.getUserID())) {
			return false;
		}
		return true;
	}
}
