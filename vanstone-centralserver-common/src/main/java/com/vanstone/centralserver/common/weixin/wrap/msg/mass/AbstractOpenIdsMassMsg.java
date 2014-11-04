/**
 * 
 */
package com.vanstone.centralserver.common.weixin.wrap.msg.mass;

import java.util.ArrayList;
import java.util.Collection;

import com.vanstone.centralserver.common.MyAssert;

/**
 * 通过OpenIds群发的消息
 * @author shipeng
 */
public abstract class AbstractOpenIdsMassMsg extends AbstractMassMsg {
	
	public AbstractOpenIdsMassMsg(String msgtype) {
		super(msgtype);
	}
	
	private Collection<String> openIds = new ArrayList<String>();
	
	public Collection<String> getOpenIds() {
		return openIds;
	}

	public void addOpenId(String openId) {
		MyAssert.hasText(openId);
		this.openIds.add(openId);
	}
	
	public void addOpenIds(Collection<String> openIds) {
		MyAssert.notEmpty(openIds);
		this.openIds.addAll(openIds);
	}
	
	public void clearOpenIds() {
		this.openIds.clear();
	}
	
}
