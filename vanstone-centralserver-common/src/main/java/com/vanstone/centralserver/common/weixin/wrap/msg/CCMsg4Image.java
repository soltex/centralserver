/**
 * 
 */
package com.vanstone.centralserver.common.weixin.wrap.msg;

import java.util.LinkedHashMap;
import java.util.Map;

import com.vanstone.centralserver.common.JsonUtil;

/**
 * 发送图片消息
{
    "touser":"OPENID",
    "msgtype":"image",
    "image":
    {
      "media_id":"MEDIA_ID"
    }
}
 * @author shipeng
 *
 */
public class CCMsg4Image extends AbstractCustomerServiceMsg {
	
	/**发送的图片的媒体ID */
	private String mediaId;
	
	public CCMsg4Image() {
		this.setMsgtype(AbstractMsg.COMMON_TYPE_IMAGE);
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
		map.put("image", media_idMap);
		return JsonUtil.object2PrettyString(map,false);
	}
	
}
