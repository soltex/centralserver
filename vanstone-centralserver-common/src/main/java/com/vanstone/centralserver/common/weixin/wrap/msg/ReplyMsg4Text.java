/**
 * 
 */
package com.vanstone.centralserver.common.weixin.wrap.msg;

import org.dom4j.Document;
import org.dom4j.Element;

/**
 * 回复文本消息
	<xml>
		<ToUserName><![CDATA[toUser]]></ToUserName>
		<FromUserName><![CDATA[fromUser]]></FromUserName>
		<CreateTime>12345678</CreateTime>
		<MsgType><![CDATA[text]]></MsgType>
		<Content><![CDATA[你好]]></Content>
	</xml>
 * @author shipeng
 */
public class ReplyMsg4Text extends AbstractMsg {
	
	/**回复的消息内容（换行：在content中能够换行，微信客户端就支持换行显示） */
	private String content;
	
	public ReplyMsg4Text() {
		super(COMMON_TYPE_TEXT);
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	public String toReplyXml() {
		Document document = this.toReplyDoc();
		Element xmlElement = document.getRootElement();
		Element contentElement = xmlElement.addElement("Content");
		contentElement.setText(this.getContent());
		return document.asXML();
	}
}
