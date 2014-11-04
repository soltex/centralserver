/**
 * 
 */
package com.vanstone.centralserver.common.weixin.wrap.msg;

import java.util.LinkedHashMap;
import java.util.Map;

import com.vanstone.centralserver.common.JsonUtil;

/**
 * 发送视频消息
{
    "touser":"OPENID",
    "msgtype":"video",
    "video":
    {
      "media_id":"MEDIA_ID",
      "title":"TITLE",
      "description":"DESCRIPTION"
    }
}
 * @author shipeng
 */
public class CCMsg4Video extends AbstractCustomerServiceMsg {

	/** 发送的视频的媒体ID */
	private String mediaId;
	/** 视频消息的标题 */
	private String title;
	/** 视频消息的描述 */
	private String description;

	public CCMsg4Video() {
		this.setMsgtype(AbstractMsg.COMMON_TYPE_VIDEO);
	}

	public String getMediaId() {
		return mediaId;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
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
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("touser", this.getTouser());
		map.put("msgtype", this.getMsgtype());
		Map<String, Object> videoMap = new LinkedHashMap<String, Object>();
		videoMap.put("media_id", this.getMediaId());
		videoMap.put("title", this.getTitle());
		videoMap.put("description", this.getDescription());
		map.put("video", videoMap);
		return JsonUtil.object2PrettyString(map,false);
	}
	
}
