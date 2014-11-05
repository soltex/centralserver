package com.vanstone.centralserver.common.weixin.wrap.msg.mass;

import com.vanstone.centralserver.common.weixin.wrap.msg.AbstractEvent;

/**
 * 事件名称 MASSSENDJOBFINISH ，<br />
 * 由于群发任务提交后，群发任务可能在一定时间后才完成，<br />
 * 因此，群发接口调用时，仅会给出群发任务是否提交成功的提示，<br />
 * 若群发任务提交成功，则在群发任务结束时，<br />
 * 会向开发者在公众平台填写的开发者URL（callback URL）推送事件。 <br />
 * 
 * @author shipeng
 */
public class Event4MassSendJobFinish extends AbstractEvent {
	
	/** 群发的消息ID */
	private int msgId;
	/**
	 * 群发的结构， 为“send success”或“send fail”或“err(num)”。 但send success时，
	 * 也有可能因用户拒收公众号的消息、 系统错误等原因造成少量用户接收失败。 err(num)是审核失败的具体原因，可能的情况如下：
	 * err(10001), 涉嫌广告 err(20001), 涉嫌政治 err(20004), 涉嫌社会 err(20002), 涉嫌色情
	 * err(20006), 涉嫌违法犯罪 err(20008), 涉嫌欺诈 err(20013), 涉嫌版权 err(22000),
	 * 涉嫌互推(互相宣传) err(21000), 涉嫌其他
	 */
	private String status;
	/** group_id下粉丝数；或者openid_list中的粉丝数 */
	private int totalCount;
	/**
	 * 过滤（过滤是指特定地区、性别的过滤、用户设置拒收的过滤，用户接收已超4条的过滤）后， 准备发送的粉丝数，原则上，FilterCount =
	 * SentCount + ErrorCount
	 */
	private int filterCount;
	/** 发送成功的粉丝数 */
	private int sentCount;
	/** 发送失败的粉丝数 */
	private int errorCount;
	
	public Event4MassSendJobFinish() {
		super(MASSSENDJOBFINISH_EVENT);
	}
	
	public int getMsgId() {
		return msgId;
	}

	public void setMsgId(int msgId) {
		this.msgId = msgId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getFilterCount() {
		return filterCount;
	}

	public void setFilterCount(int filterCount) {
		this.filterCount = filterCount;
	}

	public int getSentCount() {
		return sentCount;
	}

	public void setSentCount(int sentCount) {
		this.sentCount = sentCount;
	}

	public int getErrorCount() {
		return errorCount;
	}

	public void setErrorCount(int errorCount) {
		this.errorCount = errorCount;
	}
	
}
