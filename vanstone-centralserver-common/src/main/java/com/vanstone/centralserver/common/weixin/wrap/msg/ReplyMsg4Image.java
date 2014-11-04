/**
 * 
 */
package com.vanstone.centralserver.common.weixin.wrap.msg;

import org.dom4j.Document;
import org.dom4j.Element;


/**
 * 回复图片消息
	<xml>
	<ToUserName><![CDATA[toUser]]></ToUserName>
	<FromUserName><![CDATA[fromUser]]></FromUserName>
	<CreateTime>12345678</CreateTime>
	<MsgType><![CDATA[image]]></MsgType>
	<Image>
	<MediaId><![CDATA[media_id]]></MediaId>
	</Image>
	</xml>
 * @author shipeng
 *
 */
public class ReplyMsg4Image extends AbstractMsg {

	private String mediaId;

	public ReplyMsg4Image() {
		this.setMsgType(COMMON_TYPE_IMAGE);
	}

	public String getMediaId() {
		return mediaId;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}
	
	public String toReplyXml() {
		Document document = this.toReplyDoc();
		Element xmlElement = document.getRootElement();
		
		Element imageElement = xmlElement.addElement("Image");
		Element mediaidElement = imageElement.addElement("MediaId");
		mediaidElement.setText(this.getMediaId());
		
		return document.asXML();
	}
}
