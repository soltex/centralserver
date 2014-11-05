/**
 * 
 */
package com.vanstone.centralserver.common.weixin.wrap.msg;

/**
 * <xml> <ToUserName><![CDATA[toUser]]></ToUserName>
 * <FromUserName><![CDATA[fromUser]]></FromUserName>
 * <CreateTime>1357290913</CreateTime> <MsgType><![CDATA[video]]></MsgType>
 * <MediaId><![CDATA[media_id]]></MediaId>
 * <ThumbMediaId><![CDATA[thumb_media_id]]></ThumbMediaId>
 * <MsgId>1234567890123456</MsgId> </xml>
 * 
 * @author shipeng
 */
public class Msg4Video extends AbstractMsg {
	
	/** 视频消息媒体id，可以调用多媒体文件下载接口拉取数据。 */
	private String mediaId;
	/** 视频消息缩略图的媒体id，可以调用多媒体文件下载接口拉取数据。 */
	private String thumbMediaId;
	/** 消息id，64位整型 */
	private String msgId;
	
	public Msg4Video() {
		super(COMMON_TYPE_VIDEO);
	}
	
	public String getMediaId() {
		return mediaId;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}

	public String getThumbMediaId() {
		return thumbMediaId;
	}

	public void setThumbMediaId(String thumbMediaId) {
		this.thumbMediaId = thumbMediaId;
	}

	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}

}
