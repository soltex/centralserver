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
public class CorpMsg4Video extends AbstractCorpMsg {
	
	private String mediaID;
	private String title;
	private String description;
	
	/**
	 * 全部
	 * @param agentid
	 * @param safe
	 */
	public CorpMsg4Video(ICorpApp agentid, boolean safe) {
		super(CorpMsgType.Video ,agentid, safe, true, null, null, null);
	}
	
	/**
	 * 非全部,需要指定用户集合
	 * @param agentid
	 * @param safe
	 * @param touserids
	 * @param topartyids
	 * @param tagids
	 */
	public CorpMsg4Video(ICorpApp agentid, boolean safe, Collection<String> touserids, Collection<String> topartyids, Collection<String> tagids) {
		super(CorpMsgType.Video, agentid, safe, false, touserids, topartyids, tagids);
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
		MyAssert.hasText(this.mediaID);
		Map<String, Object> map = this.toInteralMap();
		Map<String, Object> videoMap = new HashMap<String, Object>();
		videoMap.put("media_id", this.mediaID);
		videoMap.put("title", this.getTitle());
		videoMap.put("description", this.getDescription());
		map.put("video", videoMap);
		return JsonUtil.object2PrettyString(map, false);
	}
}
