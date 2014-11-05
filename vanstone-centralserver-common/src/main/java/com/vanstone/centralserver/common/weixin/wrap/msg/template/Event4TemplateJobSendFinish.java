/**
 * 
 */
package com.vanstone.centralserver.common.weixin.wrap.msg.template;

import com.vanstone.centralserver.common.weixin.wrap.msg.AbstractEvent;

/**
 * 在模版消息发送任务完成后，<br>
 * 微信服务器会将是否送达成功作为通知，<br>
 * 发送到开发者中心中填写的服务器配置地址中。<br>
 * @author shipeng
 */
public class Event4TemplateJobSendFinish extends AbstractEvent {
	
	private Integer msgId;
	private String status;
	
	public Event4TemplateJobSendFinish() {
		super(TEMPLATESENDJOBFINISH_EVENT);
	}
	
	public Integer getMsgId() {
		return msgId;
	}

	public void setMsgId(Integer msgId) {
		this.msgId = msgId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}
