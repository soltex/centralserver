/**
 * 
 */
package com.vanstone.centralserver.common.weixin.wrap.msg;

/**
 * 首次二维码扫描，用户未订阅状态
	 <xml>
	 <ToUserName><![CDATA[toUser]]></ToUserName>
	<FromUserName><![CDATA[FromUser]]></FromUserName>
	<CreateTime>123456789</CreateTime>
	<MsgType><![CDATA[event]]></MsgType>
	<Event><![CDATA[subscribe]]></Event>
	<EventKey><![CDATA[qrscene_123123]]></EventKey>
	<Ticket><![CDATA[TICKET]]></Ticket>
	</xml>
 * @author shipeng
 */
public class Event4FirstSubscribe extends Event4Subscribe {

	private String eventKey;
	private String ticket;

	public Event4FirstSubscribe() {
		this.setMsgType(TYPE_EVENT);
		this.setEvent(RECEIVE_EVENT_SUBSCRIBE);
	}

	public String getEventKey() {
		return eventKey;
	}

	public void setEventKey(String eventKey) {
		this.eventKey = eventKey;
	}

	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

}
