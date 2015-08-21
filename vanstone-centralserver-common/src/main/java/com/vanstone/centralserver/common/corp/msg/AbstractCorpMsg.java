/**
 * 
 */
package com.vanstone.centralserver.common.corp.msg;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.vanstone.centralserver.common.MyAssert;
import com.vanstone.centralserver.common.corp.ICorpApp;

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
	private boolean safe = false;
	
	/**是否为全部*/
	private boolean alluser=false;
	
	protected AbstractCorpMsg(CorpMsgType msgType, ICorpApp agentid, boolean safe, boolean alluser, Collection<String> touserids, Collection<String> topartyids, Collection<String> tagids) {
		MyAssert.notNull(msgType);
		MyAssert.notNull(agentid);
		this.msgType = msgType;
		this.agentid = agentid.getId();
		this.safe = safe;
		
		if (alluser) {
			this.touser = "@all";
		}else{
			this.touser = __list_to_string(touserids);
			this.toparty = __list_to_string(topartyids);
			this.totag = __list_to_string(tagids);
		}
	}
	
	public boolean isAlluser() {
		return alluser;
	}

	public CorpMsgType getMsgType() {
		return msgType;
	}
	
	public String getTouser() {
		return touser;
	}
	
	public String getToparty() {
		return toparty;
	}

	public String getTotag() {
		return totag;
	}

	public int getAgentid() {
		return agentid;
	}
	
	public boolean isSafe() {
		return safe;
	}
	
	/**
	 * list转换为分隔符形式的string
	 * @param list
	 * @return
	 */
	private String __list_to_string(Collection<String> list) {
		if (list == null || list.size() <=0) {
			return null;
		}
		StringBuffer sb = new StringBuffer();
		for (String str : list) {
			sb.append(str).append("|");
		}
		return sb.toString();
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
 