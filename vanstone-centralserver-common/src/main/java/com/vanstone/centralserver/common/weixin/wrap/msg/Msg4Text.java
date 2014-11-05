/**
 * 
 */
package com.vanstone.centralserver.common.weixin.wrap.msg;

import org.apache.commons.codec.binary.Base64;

import com.vanstone.centralserver.common.Constants;
import com.vanstone.emoji.EmojiHelper;

/**
 *  
	<xml>
	 <ToUserName><![CDATA[toUser]]></ToUserName>
	 <FromUserName><![CDATA[fromUser]]></FromUserName> 
	 <CreateTime>1348831860</CreateTime>
	 <MsgType><![CDATA[text]]></MsgType>
	 <Content><![CDATA[this is a test]]></Content>
	 <MsgId>1234567890123456</MsgId>
	 </xml>
 * @author shipeng
 */
public class Msg4Text extends AbstractMsg {
	
	public Msg4Text() {
		super(COMMON_TYPE_TEXT);
	}
	
	/**文本消息内容*/
	private String content;
	
	/**消息id，64位整型 */
	private String msgId;
	
	public String getContent() {
		return content;
	}
	
	/**
	 * 获取Content的Base64值
	 * @return
	 */
	public String getContentBase64() {
		if (this.getContent() != null && !this.getContent().equals("")) {
			return Base64.encodeBase64String(this.getContent().getBytes(Constants.SYS_CHARSET_UTF8));
		}
		return null;
	}
	
	/**
	 * 获取Content的Emoji 转换值
	 * @return
	 */
	public String getContentEmojiConvert() {
		if (this.getContent() != null && !this.getContent().equals("")) {
			return EmojiHelper.cn2Img(this.getContent());
		}
		return null;
	}
	
	public void setContent(String content) {
		this.content = content;
	}

	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}
	
}
