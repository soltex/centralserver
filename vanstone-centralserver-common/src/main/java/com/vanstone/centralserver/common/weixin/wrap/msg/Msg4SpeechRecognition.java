/**
 * 
 */
package com.vanstone.centralserver.common.weixin.wrap.msg;

/**
 * 开通语音识别功能，用户每次发送语音给公众号时，微信会在推送的语音消息XML数据包中，增加一个Recongnition字段。
	注：由于客户端缓存，开发者开启或者关闭语音识别功能，对新关注者立刻生效，对已关注用户需要24小时生效。开发者可以重新关注此帐号进行测试。 
	<xml>
		<ToUserName><![CDATA[toUser]]></ToUserName>
		<FromUserName><![CDATA[fromUser]]></FromUserName>
		<CreateTime>1357290913</CreateTime>
		<MsgType><![CDATA[voice]]></MsgType>
		<MediaId><![CDATA[media_id]]></MediaId>
		<Format><![CDATA[Format]]></Format>
		<Recognition><![CDATA[腾讯微信团队]]></Recognition>
		<MsgId>1234567890123456</MsgId>
	</xml>
 * @author shipeng
 */
public class Msg4SpeechRecognition extends Msg4Voice {
	
	/** 语音识别结果，UTF8编码 */
	private String recognition;
	
	public String getRecognition() {
		return recognition;
	}
	
	public void setRecognition(String recognition) {
		this.recognition = recognition;
	}
	
}
