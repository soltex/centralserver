/**
 * 
 */
package com.vanstone.centralserver.common.weixin.wrap.msg;

/**
 * @author shipeng
 */
public class Event4Subscribe extends AbstractEvent {
	
	public Event4Subscribe() {
		this.setMsgType(TYPE_EVENT);
		this.setEvent(RECEIVE_EVENT_SUBSCRIBE);
	}
	
}
