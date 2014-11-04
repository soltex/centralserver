/**
 * 
 */
package com.vanstone.centralserver.common.weixin.wrap.msg;

/**
 * 点击菜单拉取消息时的事件推送
	
	<xml>
		<ToUserName><![CDATA[toUser]]></ToUserName>
		<FromUserName><![CDATA[FromUser]]></FromUserName>
		<CreateTime>123456789</CreateTime>
		<MsgType><![CDATA[event]]></MsgType>
		<Event><![CDATA[CLICK]]></Event>
		<EventKey><![CDATA[EVENTKEY]]></EventKey>
	</xml>
	
 * @author shipeng
 */
public class Event4Click extends AbstractEvent {
	
	private String eventKey;
	
	public Event4Click() {
		this.setMsgType(TYPE_EVENT);
		this.setEvent(RECEIVE_EVENT_CLICK);
	}
	
	public String getEventKey() {
		return eventKey;
	}

	public void setEventKey(String eventKey) {
		this.eventKey = eventKey;
	}
	
}
