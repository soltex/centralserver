/**
 * 
 */
package com.vanstone.centralserver.common.corp.msg;

import java.util.HashMap;
import java.util.Map;

import com.vanstone.centralserver.common.JsonUtil;

/**
 * @author shipeng
 *
 */
public class CorpMsg4Text extends AbstractCorpMsg {

	public CorpMsg4Text(int agentid) {
		super(CorpMsgType.Text,agentid);
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
		Map<String, Object> map = this.toInteralMap();
		Map<String, Object> contentMap = new HashMap<String, Object>();
		contentMap.put("content", this.getContent());
		map.put("text", contentMap);
		return JsonUtil.object2PrettyString(map, false);
	}
	
}
