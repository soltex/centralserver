/**
 * 
 */
package com.vanstone.centralserver.common.corp.msg;

import java.util.HashMap;
import java.util.Map;

import com.vanstone.centralserver.common.MyAssert;

/**
 * @author shipeng
 *
 */
public abstract class AbstractCorpMsg {
	
	private CorpMsgType msgType;
	private String touser;
	private String toparty;
	private String totag;
	private int agentid;
	private boolean safe;
	
	protected AbstractCorpMsg(CorpMsgType msgType, int agentid) {
		MyAssert.notNull(msgType);
		this.agentid = agentid;
	}
	
	public CorpMsgType getMsgType() {
		return msgType;
	}
	
	public String getTouser() {
		return touser;
	}
	
	public void setTouser(String touser) {
		this.touser = touser;
	}

	public String getToparty() {
		return toparty;
	}

	public void setToparty(String toparty) {
		this.toparty = toparty;
	}

	public String getTotag() {
		return totag;
	}

	public void setTotag(String totag) {
		this.totag = totag;
	}

	public int getAgentid() {
		return agentid;
	}
	
	public boolean isSafe() {
		return safe;
	}

	public void setSafe(boolean safe) {
		this.safe = safe;
	}
	
	/**
	 * 转换成CorpMsg的Json
	 * @return
	 */
	public abstract String toJson();
	
	/**
	 * 用于内部使用的转换Map
	 * @return
	 */
	protected Map<String, Object> toInteralMap() {
		Map<String, Object> map = new HashMap<String, Object>();
		if (this.getTouser() != null && !this.getTouser().equals("")) {
			map.put("touser", this.getTouser());
		}
		if (this.getToparty() != null && !this.getToparty().equals("")) {
			map.put("toparty", this.getToparty());
		}
		if (this.getTotag() != null && !this.getTotag().equals("")) {
			map.put("totag", this.getTotag());
		}
		map.put("msgtype", this.getMsgType().getCode());
		map.put("agentid", this.getAgentid());
		map.put("safe", this.isSafe() ? 1 : 0);
		return map;
	}
}
