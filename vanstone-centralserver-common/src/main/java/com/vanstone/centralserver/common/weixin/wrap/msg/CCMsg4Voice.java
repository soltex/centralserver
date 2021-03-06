/**
 * 
 */
package com.vanstone.centralserver.common.weixin.wrap.msg;

import java.util.LinkedHashMap;
import java.util.Map;

import com.vanstone.centralserver.common.JsonUtil;

/**
 * 发送语音消息
{
    "touser":"OPENID",
    "msgtype":"voice",
    "voice":
    {
      "media_id":"MEDIA_ID"
    }
}
 * @author shipeng
 *
 */
public class CCMsg4Voice extends AbstractCustomerServiceMsg {
	/**发送的VOICE的媒体ID */
	private String mediaId;
	
	public CCMsg4Voice() {
		this.setMsgtype(AbstractMsg.COMMON_TYPE_VOICE);
	}
	
	public String getMediaId() {
		return mediaId;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}
	
	@Override
	public String toJson() {
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("touser", this.getTouser());
		map.put("msgtype", this.getMsgtype());
		Map<String, Object> media_idMap = new LinkedHashMap<String, Object>();
		media_idMap.put("media_id", this.getMediaId());
		map.put("voice", media_idMap);
		return JsonUtil.object2PrettyString(map,false);
	}
	
}
