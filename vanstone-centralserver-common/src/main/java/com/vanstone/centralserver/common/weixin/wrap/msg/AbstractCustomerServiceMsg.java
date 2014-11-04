package com.vanstone.centralserver.common.weixin.wrap.msg;

/**
 *发送客服消息
 *当用户主动发消息给公众号的时候（包括发送信息、点击自定义菜单、订阅事件、扫描二维码事件、支付成功事件、用户维权），
 *微信将会把消息数据推送给开发者，开发者在一段时间内（目前修改为48小时）可以调用客服消息接口，
 *通过POST一个JSON数据包来发送消息给普通用户，在48小时内不限制发送次数。
 *此接口主要用于客服等有人工消息处理环节的功能，方便开发者为用户提供更加优质的服务。 
 * @author shipeng
 */
public abstract class AbstractCustomerServiceMsg {

	/** 普通用户openid */
	private String touser;
	/** 消息类型 */
	private String msgtype;

	public String getTouser() {
		return touser;
	}

	public void setTouser(String touser) {
		this.touser = touser;
	}

	public String getMsgtype() {
		return msgtype;
	}

	protected void setMsgtype(String msgtype) {
		this.msgtype = msgtype;
	}
	
	/**
	 * 转换为Json字符串
	 * @return
	 */
	public abstract String toJson();
}
