/**
 * 
 */
package com.vanstone.centralserver.common.corp.msg;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.vanstone.centralserver.common.JsonUtil;
import com.vanstone.centralserver.common.MyAssert;
import com.vanstone.centralserver.common.corp.ICorpApp;

/**
 * @author shipeng
 *
 */
public class CorpMsg4Text extends AbstractCorpMsg {

	//CorpMsgType msgType, int agentid, boolean safe, boolean alluser, Collection<String> touserids, Collection<String> topartyids, Collection<String> tagids
	
	/**
	 * 全部
	 * @param agentid
	 * @param safe
	 */
	public CorpMsg4Text(ICorpApp agentid, boolean safe) {
		super(CorpMsgType.Text,agentid, safe, true, null, null, null);
	}
	
	/**
	 * 非全部,需要指定用户集合
	 * @param agentid
	 * @param safe
	 * @param touserids
	 * @param topartyids
	 * @param tagids
	 */
	public CorpMsg4Text(ICorpApp agentid, boolean safe, Collection<String> touserids, Collection<String> topartyids, Collection<String> tagids) {
		super(CorpMsgType.Text, agentid, safe, false, touserids, topartyids, tagids);
	}
	
	private String content;
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	@Override
	public String toJson() {
		MyAssert.hasText(this.getContent());
		Map<String, Object> map = this.toInteralMap();
		Map<String, Object> contentMap = new HashMap<String, Object>();
		contentMap.put("content", this.getContent());
		map.put("text", contentMap);
		return JsonUtil.object2PrettyString(map, false);
	}
	
}
