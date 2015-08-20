/**
 * 
 */
package com.vanstone.centralserver.common.corp.passive;

import java.util.Date;

/**
 * @author shipeng
 * 被动消息
 */
public abstract class AbstractPassiveMsg {
	
	private String toUserName;
	private String fromUserName;
	private Date createTime;
	private String agentID;
	private CorpWeixinEvent event;
	private String eventName;
	
	protected AbstractPassiveMsg(MsgType msgType, CorpWeixinEvent weixinEvent, String eventName) {
		
	}
	
}
