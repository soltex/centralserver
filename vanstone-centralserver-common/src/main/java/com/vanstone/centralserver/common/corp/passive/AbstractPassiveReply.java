/**
 * 
 */
package com.vanstone.centralserver.common.corp.passive;

import java.util.Date;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.vanstone.centralserver.common.MyAssert;
import com.vanstone.centralserver.common.corp.ICorp;
import com.vanstone.centralserver.common.corp.msg.CorpMsgType;
import com.vanstone.centralserver.common.util.aes.AesException;
import com.vanstone.centralserver.common.util.aes.WXBizMsgCrypt;

/**
 * @author shipeng
 *
 */
public abstract class AbstractPassiveReply {
	private String toUserName;
	private String fromUserName;
	private Date createTime;
	private CorpMsgType msgType;

	protected AbstractPassiveReply(CorpMsgType msgType) {
		this.msgType = msgType;
	}

	public String getToUserName() {
		return toUserName;
	}

	public void setToUserName(String toUserName) {
		this.toUserName = toUserName;
	}

	public String getFromUserName() {
		return fromUserName;
	}

	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public CorpMsgType getMsgType() {
		return msgType;
	}
	
	public void setMsgType(CorpMsgType msgType) {
		this.msgType = msgType;
	}
	
	/**
	 * 转换成加密后的字符串
	 * @param timestamp
	 * @param nonce
	 * @return
	 */
	public String toEncryptJson(String token, String encodingAesKey, ICorp corp, String timestamp, String nonce) {
		MyAssert.hasText(this.getFromUserName());
		MyAssert.hasText(this.getToUserName());
		MyAssert.notNull(this.getCreateTime());
		
		Document document = DocumentHelper.createDocument();
		Element xmlElement = document.addElement("xml");
		
		Element ToUserNameElement = xmlElement.addElement("ToUserName");
		ToUserNameElement.setText(this.getToUserName());
		
		Element FromUserNameElement = xmlElement.addElement("FromUserName");
		FromUserNameElement.setText(this.getFromUserName());
		
		Element CreateTimeElement = xmlElement.addElement("CreateTime");
		CreateTimeElement.setText(String.valueOf(this.getCreateTime().getTime()));
		
		Element MsgTypeElement = xmlElement.addElement("MsgType");
		MsgTypeElement.setText(this.getMsgType().getCode());
		
		__to_encrypt_json_internal(xmlElement);
		
		try {
			WXBizMsgCrypt wxBizMsgCrypt = new WXBizMsgCrypt(token, encodingAesKey, corp.getAppID());
			return wxBizMsgCrypt.EncryptMsg(document.asXML(), timestamp, nonce);
		} catch (AesException e) {
			e.printStackTrace();
			throw new Error(e);
		}
	}
	
	protected abstract void __to_encrypt_json_internal(Element rootElement);
}
