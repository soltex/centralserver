/**
 * 
 */
package com.vanstone.centralserver.common.corp.msg;

import java.util.Map;

import com.vanstone.centralserver.common.JsonUtil;

/**
 * @author shipeng
 *
 */
public class CropMsg4Img extends AbstractCorpMsg {

	private String mediaID;

	public CropMsg4Img(int agentid) {
		super(CorpMsgType.Image, agentid);
	}

	public String getMediaID() {
		return mediaID;
	}

	public void setMediaID(String mediaID) {
		this.mediaID = mediaID;
	}

	@Override
	public String toJson() {
		Map<String, Object> map = this.toInteralMap();
		map.put("media_id ", this.mediaID);
		return JsonUtil.object2PrettyString(map, false);
	}

}
