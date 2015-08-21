/**
 * 
 */
package com.vanstone.centralserver.common.corp.passive;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Element;

import com.vanstone.centralserver.common.util.UnixJavaDateTimeUtil;

/**
 * @author shipeng 被动消息
 */
public abstract class AbstractPassiveMsg {

	private String toUserName;
	private String fromUserName;
	private Date createTime;
	private String agentID;
	private CorpMsgType msgType;
	private Long msgID;
	
	// 参数中携带的值
	private String msgSignature;
	private long timestamp;
	private String nonce;
	
	protected AbstractPassiveMsg(CorpMsgType msgType) {
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

	public String getAgentID() {
		return agentID;
	}

	public void setAgentID(String agentID) {
		this.agentID = agentID;
	}

	public CorpMsgType getMsgType() {
		return msgType;
	}

	public String getMsgSignature() {
		return msgSignature;
	}

	public void setMsgSignature(String msgSignature) {
		this.msgSignature = msgSignature;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public String getNonce() {
		return nonce;
	}

	public void setNonce(String nonce) {
		this.nonce = nonce;
	}

	public void setMsgType(CorpMsgType msgType) {
		this.msgType = msgType;
	}
	
	public Long getMsgID() {
		return msgID;
	}

	public void setMsgID(Long msgID) {
		this.msgID = msgID;
	}
	
	public void initial(Element rootElement, HttpServletRequest servletRequest) {
		//初始化基本信息
		
		//初始化QueryString参数信息
		String msg_signature = servletRequest.getParameter("msg_signature");
		String timestamp = servletRequest.getParameter("timestamp");
		String nonce = servletRequest.getParameter("nonce");
		this.setMsgSignature(msg_signature);
		this.setTimestamp(Long.parseLong(timestamp));
		this.setNonce(nonce);
		
		//初始化xml中内部信息
		this.toUserName = rootElement.elementTextTrim("ToUserName");
		this.fromUserName = rootElement.elementTextTrim("FromUserName");
		this.createTime = UnixJavaDateTimeUtil.unixToJavaDateTime(Long.parseLong(rootElement.elementTextTrim("CreateTime")));
		this.agentID = rootElement.elementTextTrim("AgentID");
		if (rootElement.element("MsgId") != null && rootElement.element("MsgId").getText() != null && !rootElement.element("MsgId").getText().equals("")) {
			this.msgID = Long.parseLong(rootElement.elementText("MsgId"));
		}
		this.initial_internal(rootElement);
	}
	
	/**
	 * 基本进行初始化
	 * @param document
	 */
	protected abstract void initial_internal(Element rootElement);
}
