/**
 * 
 */
package com.vanstone.centralserver.common.weixin.wrap.msg;

/**
 * 
 <xml> <ToUserName><![CDATA[toUser]]></ToUserName>
 * <FromUserName><![CDATA[fromUser]]></FromUserName>
 * <CreateTime>1357290913</CreateTime> <MsgType><![CDATA[voice]]></MsgType>
 * <MediaId><![CDATA[media_id]]></MediaId> <Format><![CDATA[Format]]></Format>
 * <MsgId>1234567890123456</MsgId> </xml>
 * 
 * @author shipeng
 */
public class Msg4Voice extends AbstractMsg {

	/**语音消息媒体id，可以调用多媒体文件下载接口拉取数据。 */
	private String mediaId;
	/** 语音格式，如amr，speex等 */
	private String format;
	/**消息id，64位整型 */
	private String msgId;

	public Msg4Voice() {
		this.setMsgId(COMMON_TYPE_VOICE);
	}

	public String getMediaId() {
		return mediaId;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}

}
