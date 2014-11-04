/**
 * 
 */
package com.vanstone.centralserver.common.weixin.wrap.msg;

import org.dom4j.Document;
import org.dom4j.Element;

/**
 * 回复音乐消息
 	<xml>
		<ToUserName><![CDATA[toUser]]></ToUserName>
		<FromUserName><![CDATA[fromUser]]></FromUserName>
		<CreateTime>12345678</CreateTime>
		<MsgType><![CDATA[music]]></MsgType>
		<Music>
		<Title><![CDATA[TITLE]]></Title>
		<Description><![CDATA[DESCRIPTION]]></Description>
		<MusicUrl><![CDATA[MUSIC_Url]]></MusicUrl>
		<HQMusicUrl><![CDATA[HQ_MUSIC_Url]]></HQMusicUrl>
		<ThumbMediaId><![CDATA[media_id]]></ThumbMediaId>
		</Music>
	</xml>
 * @author shipeng
 */
public class ReplyMsg4Music extends AbstractMsg {

	/** 音乐标题 */
	private String title;
	/** 音乐描述 */
	private String description;
	/** 音乐链接 */
	private String musicUrl;
	/** 高质量音乐链接，WIFI环境优先使用该链接播放音乐 */
	private String hqMusicUrl;
	/** 缩略图的媒体id，通过上传多媒体文件，得到的id */
	private String thumbMediaId;
	
	public ReplyMsg4Music() {
		this.setMsgType(COMMON_TYPE_MUSIC);
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

	public String getMusicUrl() {
		return musicUrl;
	}

	public void setMusicUrl(String musicUrl) {
		this.musicUrl = musicUrl;
	}

	public String getHqMusicUrl() {
		return hqMusicUrl;
	}

	public void setHqMusicUrl(String hqMusicUrl) {
		this.hqMusicUrl = hqMusicUrl;
	}

	public String getThumbMediaId() {
		return thumbMediaId;
	}

	public void setThumbMediaId(String thumbMediaId) {
		this.thumbMediaId = thumbMediaId;
	}
	
	public String toReplyXml() {
		Document document = this.toReplyDoc();
		Element xmlElement = document.getRootElement();
		
		Element musicElement = xmlElement.addElement("Music");
		Element titleElement = musicElement.addElement("Title");
		titleElement.setText(this.getTitle());
		
		Element descriptionElement = musicElement.addElement("Description");
		descriptionElement.setText(this.getDescription());
		
		Element musicUrlElement = musicElement.addElement("MusicUrl");
		musicUrlElement.setText(this.getMusicUrl());
		
		Element hqMusicUrlElement = musicElement.addElement("HQMusicUrl");
		hqMusicUrlElement.setText(this.getHqMusicUrl());
		
		Element thumbMediaIdElement = musicElement.addElement("ThumbMediaId");
		thumbMediaIdElement.setText(this.getThumbMediaId());
		
		return document.asXML();
	}
}
