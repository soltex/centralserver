/**
 * 
 */
package com.vanstone.centralserver.common.weixin.wrap.msg;

/**
 * 
 <xml> 
 <ToUserName><![CDATA[toUser]]></ToUserName>
 * <FromUserName><![CDATA[fromUser]]></FromUserName>
 * <CreateTime>1351776360</CreateTime> 
 * <MsgType><![CDATA[location]]></MsgType>
 * <Location_X>23.134521</Location_X> 
 * <Location_Y>113.358803</Location_Y>
 * <Scale>20</Scale> <Label><![CDATA[位置信息]]></Label>
 * <MsgId>1234567890123456</MsgId> 
 * </xml>
 * @author shipeng
 */
public class Msg4Location extends AbstractMsg {

	public Msg4Location() {
		super(COMMON_TYPE_LOCATION);
	}
	
	/**地理位置维度*/
	private double locationX;
	/**地理位置经度*/
	private double locationY;
	/**地图缩放大小*/
	private double scale;
	/**地理位置信息*/
	private String label;
	/** 消息id，64位整型 */
	private String msgId;

	public double getLocationX() {
		return locationX;
	}

	public void setLocationX(double locationX) {
		this.locationX = locationX;
	}

	public double getLocationY() {
		return locationY;
	}

	public void setLocationY(double locationY) {
		this.locationY = locationY;
	}

	public double getScale() {
		return scale;
	}

	public void setScale(double scale) {
		this.scale = scale;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}

}
