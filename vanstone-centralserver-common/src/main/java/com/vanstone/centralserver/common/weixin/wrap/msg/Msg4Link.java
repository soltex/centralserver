/**
 * 
 */
package com.vanstone.centralserver.common.weixin.wrap.msg;

/**
 * 
<xml>
<ToUserName><![CDATA[toUser]]></ToUserName>
<FromUserName><![CDATA[fromUser]]></FromUserName>
<CreateTime>1351776360</CreateTime>
<MsgType><![CDATA[link]]></MsgType>
<Title><![CDATA[公众平台官网链接]]></Title>
<Description><![CDATA[公众平台官网链接]]></Description>
<Url><![CDATA[url]]></Url>
<MsgId>1234567890123456</MsgId>
</xml> 
 * @author shipeng
 */
public class Msg4Link extends AbstractMsg {

	/**消息标题 */
	private String title;
	/**消息描述*/
	private String description;
	/**消息链接*/
	private String url;
	/** 消息id，64位整型 */
	private String msgId;
	
	public Msg4Link() {
		this.setMsgType(COMMON_TYPE_LINK);
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}
	
}
