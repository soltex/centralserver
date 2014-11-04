/**
 * 
 */
package com.vanstone.centralserver.common.weixin.wrap.msg.mass;

import java.util.LinkedHashMap;
import java.util.Map;

import com.vanstone.centralserver.common.JsonUtil;

/**
 * @author shipeng
 *
 */
public class MassOpenIdsImageMsg extends AbstractOpenIdsMassMsg {

	public MassOpenIdsImageMsg() {
		super(IMAGE_TYPE);
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
		
		map.put("touser", this.getOpenIds());
		
		Map<String, Object> imageMap = new LinkedHashMap<String, Object>();
		imageMap.put("media_id", this.getMediaId());
		map.put("image", imageMap);
		
		map.put("msgtype", this.getMsgtype());
		return JsonUtil.object2PrettyString(map, false);
	}

}
