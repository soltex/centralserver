/**
 * 
 */
package com.vanstone.centralserver.common.weixin.wrap.msg;

import org.dom4j.Document;
import org.dom4j.Element;


/**
 * 回复视频消息
	<xml>
		<ToUserName><![CDATA[toUser]]></ToUserName>
		<FromUserName><![CDATA[fromUser]]></FromUserName>
		<CreateTime>12345678</CreateTime>
		<MsgType><![CDATA[video]]></MsgType>
		<Video>
		<MediaId><![CDATA[media_id]]></MediaId>
		<Title><![CDATA[title]]></Title>
		<Description><![CDATA[description]]></Description>
		</Video> 
	</xml>
 * @author shipeng
 *
 */
public class ReplyMsg4Video extends AbstractMsg {

	/** 通过上传多媒体文件，得到的id */
	private String mediaId;
	/** 视频消息的标题 */
	private String title;
	/** 视频消息的描述 */
	private String description;

	public ReplyMsg4Video() {
		this.setMsgType(COMMON_TYPE_VIDEO);
	}

	public String getMediaId() {
		return mediaId;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
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
	
	public String toReplyXml() {
		Document document = this.toReplyDoc();
		Element xmlElement = document.getRootElement();
		
		Element videoElement = xmlElement.addElement("Video");
		Element mediaidElement = videoElement.addElement("MediaId");
		mediaidElement.setText(this.getMediaId());
		
		Element titleElement = videoElement.addElement("Title");
		titleElement.setText(this.getTitle());
		
		Element descriptionElement = videoElement.addElement("Description");
		descriptionElement.setText(this.getDescription());
		
		return document.asXML();
	}
}
