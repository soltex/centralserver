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
public class CorpMsg4Img extends AbstractCorpMsg {

	private String mediaID;

	/**
	 * 全部
	 * @param agentid
	 * @param safe
	 */
	public CorpMsg4Img(ICorpApp agentid, boolean safe) {
		super(CorpMsgType.Image ,agentid, safe, true, null, null, null);
	}
	
	/**
	 * 非全部,需要指定用户集合
	 * @param agentid
	 * @param safe
	 * @param touserids
	 * @param topartyids
	 * @param tagids
	 */
	public CorpMsg4Img(ICorpApp agentid, boolean safe, Collection<String> touserids, Collection<String> topartyids, Collection<String> tagids) {
		super(CorpMsgType.Image, agentid, safe, false, touserids, topartyids, tagids);
	}
	
	public String getMediaID() {
		return mediaID;
	}

	public void setMediaID(String mediaID) {
		this.mediaID = mediaID;
	}

	@Override
	public String toJson() {
		MyAssert.hasText(this.mediaID);
		Map<String, Object> map = this.toInteralMap();
		Map<String, Object> imageMap = new HashMap<String, Object>();
		imageMap.put("media_id", this.mediaID);
		map.put("image", imageMap);
		
		return JsonUtil.object2PrettyString(map, false);
	}

}
