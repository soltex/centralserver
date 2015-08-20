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
public class CorpMsg4Video extends AbstractCorpMsg {
	
	private String mediaID;
	private String title;
	private String description;
	
	public CorpMsg4Video(int agentid) {
		super(CorpMsgType.Video, agentid);
	}
	
	public String getMediaID() {
		return mediaID;
	}

	public void setMediaID(String mediaID) {
		this.mediaID = mediaID;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toJson() {
		Map<String, Object> map = this.toInteralMap();
		Map<String, Object> videoMap = new HashMap<String, Object>();
		videoMap.put("media_id ", this.mediaID);
		videoMap.put("title", this.getTitle());
		videoMap.put("description", this.getDescription());
		map.put("video", videoMap);
		return JsonUtil.object2PrettyString(map, false);
	}
}
