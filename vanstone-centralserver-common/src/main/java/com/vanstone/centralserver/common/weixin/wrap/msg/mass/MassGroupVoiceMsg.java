/**
 * 
 */
package com.vanstone.centralserver.common.weixin.wrap.msg.mass;

import java.util.LinkedHashMap;
import java.util.Map;

import com.vanstone.centralserver.common.JsonUtil;

/**
 * 批量Voice消息定义
 * @author shipeng
 *
 */
public class MassGroupVoiceMsg extends AbstractGroupMassMsg {

	public MassGroupVoiceMsg() {
		super(VOICE_TYPE);
	}
	
	private String mediaId;

	public String getMediaId() {
		return mediaId;
	}
	
	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}

	@Override
	public String toJson() {
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		
		Map<String, Object> filterMap = new LinkedHashMap<String, Object>();
		filterMap.put("group_id", this.getGroupId());
		map.put("filter", filterMap);
		
		Map<String, Object> vioceMap = new LinkedHashMap<String, Object>();
		vioceMap.put("media_id", this.getMediaId());
		map.put("voice", vioceMap);
		
		map.put("msgtype", this.getMsgtype());
		return JsonUtil.object2PrettyString(map, false);
	}
}
