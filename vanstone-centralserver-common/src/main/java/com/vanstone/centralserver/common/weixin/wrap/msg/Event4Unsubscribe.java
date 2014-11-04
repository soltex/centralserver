/**
 * 
 */
package com.vanstone.centralserver.common.weixin.wrap.msg;

/**
 * @author shipeng
 *
 */
public class Event4Unsubscribe extends AbstractEvent {
	
	public Event4Unsubscribe() {
		this.setMsgType(TYPE_EVENT);
		this.setEvent(RECEIVE_EVENT_UNSUBSCRIBE);
	}
	
}
