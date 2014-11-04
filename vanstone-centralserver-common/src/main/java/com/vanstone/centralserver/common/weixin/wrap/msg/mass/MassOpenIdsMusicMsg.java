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
public class MassOpenIdsMusicMsg extends AbstractOpenIdsMassMsg {

	public MassOpenIdsMusicMsg() {
		super(MUSIC_TYPE);
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
		
		Map<String, Object> musicMap = new LinkedHashMap<String, Object>();
		musicMap.put("media_id", this.getMediaId());
		map.put("music", musicMap);
		
		map.put("msgtype", this.getMsgtype());
		return JsonUtil.object2PrettyString(map, false);
	}
	
}
