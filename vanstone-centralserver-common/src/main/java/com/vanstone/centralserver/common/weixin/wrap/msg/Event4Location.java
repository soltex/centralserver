/**
 * 
 */
package com.vanstone.centralserver.common.weixin.wrap.msg;

/**
 * 上报地址信息事件
	<xml>
		<ToUserName><![CDATA[toUser]]></ToUserName>
		<FromUserName><![CDATA[fromUser]]></FromUserName>
		<CreateTime>123456789</CreateTime>
		<MsgType><![CDATA[event]]></MsgType>
		<Event><![CDATA[LOCATION]]></Event>
		<Latitude>23.137466</Latitude>
		<Longitude>113.352425</Longitude>
		<Precision>119.385040</Precision>
	</xml>
 * @author shipeng
 */
public class Event4Location extends AbstractEvent {

	/** 地理位置纬度 */
	private Double latitude;
	/** 地理位置经度 */
	private Double longitude;
	/** 地理位置精度 */
	private Double Precision;

	public Event4Location() {
		this.setMsgType(TYPE_EVENT);
		this.setEvent(RECEIVE_EVENT_LOCATION);
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Double getPrecision() {
		return Precision;
	}

	public void setPrecision(Double precision) {
		Precision = precision;
	}
	
}
