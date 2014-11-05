/**
 * 
 */
package com.vanstone.centralserver.common.weixin.wrap.msg;

/**
 * 2. 用户已关注时的事件推送
		推送XML数据包示例：
	<xml>
		<ToUserName><![CDATA[toUser]]></ToUserName>
		<FromUserName><![CDATA[FromUser]]></FromUserName>
		<CreateTime>123456789</CreateTime>
		<MsgType><![CDATA[event]]></MsgType>
		<Event><![CDATA[SCAN]]></Event>
		<EventKey><![CDATA[SCENE_VALUE]]></EventKey>
		<Ticket><![CDATA[TICKET]]></Ticket>
	</xml> 
 * @author shipeng
 *
 */
public class Event4Scan extends AbstractEvent {
	private String EventKey;
	private String Ticket;
	
	public Event4Scan() {
		super(RECEIVE_EVENT_SCAN);
	}

	public String getEventKey() {
		return EventKey;
	}

	public void setEventKey(String eventKey) {
		EventKey = eventKey;
	}

	public String getTicket() {
		return Ticket;
	}

	public void setTicket(String ticket) {
		Ticket = ticket;
	}
	
}
