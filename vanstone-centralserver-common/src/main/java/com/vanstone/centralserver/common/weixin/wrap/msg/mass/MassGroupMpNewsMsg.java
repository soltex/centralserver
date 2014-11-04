/**
 * 
 */
package com.vanstone.centralserver.common.weixin.wrap.msg.mass;

import java.util.LinkedHashMap;
import java.util.Map;

import com.vanstone.centralserver.common.JsonUtil;

/**
 * 大规模MpNews消息定义
 * @author shipeng
 *
 */
public class MassGroupMpNewsMsg extends AbstractGroupMassMsg {
	
	public MassGroupMpNewsMsg() {
		super(MPNEWS_TYPE);
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
		
		Map<String, Object> mpnewsMap = new LinkedHashMap<String, Object>();
		mpnewsMap.put("media_id", this.getMediaId());
		map.put("mpnews", mpnewsMap);
		
		map.put("msgtype", this.getMsgtype());
		return JsonUtil.object2PrettyString(map, false);
	}
	
}
