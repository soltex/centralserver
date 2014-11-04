/**
 * 
 */
package com.vanstone.centralserver.common.weixin.wrap.user;

import java.util.ArrayList;
import java.util.Collection;

/**
 * 获取关注者用户OpenId列表
 * @author shipengpipi@126.com
 */
public class UserOpenIdCollection {
	private Collection<String> openids = new ArrayList<String>();
	private String nextOpenId;
	
	public String getNextOpenId() {
		return nextOpenId;
	}

	public void setNextOpenId(String nextOpenId) {
		this.nextOpenId = nextOpenId;
	}

	public Collection<String> getOpenids() {
		return openids;
	}
	
	/**
	 * 增加OpenId
	 * @param openId
	 */
	public void addOpenId(String openId) {
		if (openId == null || "".equals(openId)) {
			return;
		}
		this.openids.add(openId);
	}
	
	public void addOpenIds(Collection<String> openids) {
		if (openids == null || openids.size() <=0) {
			return;
		}
		this.openids.addAll(openids);
	}
}
