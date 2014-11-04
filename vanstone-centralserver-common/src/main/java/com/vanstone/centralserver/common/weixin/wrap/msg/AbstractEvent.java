package com.vanstone.centralserver.common.weixin.wrap.msg;


/**
 * @author shipeng
 */
public class AbstractEvent extends AbstractMsg {
	
	private String event;

	public String getEvent() {
		return event;
	}

	protected void setEvent(String event) {
		this.event = event;
	}
	
}
