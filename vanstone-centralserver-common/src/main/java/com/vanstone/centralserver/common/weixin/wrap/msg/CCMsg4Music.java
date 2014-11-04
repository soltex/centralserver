/**
 * 
 */
package com.vanstone.centralserver.common.weixin.wrap.msg;

import java.util.LinkedHashMap;
import java.util.Map;

import com.vanstone.centralserver.common.JsonUtil;

/**
 * 发送音乐消息

{
    "touser":"OPENID",
    "msgtype":"music",
    "music":
    {
      "title":"MUSIC_TITLE",
      "description":"MUSIC_DESCRIPTION",
      "musicurl":"MUSIC_URL",
      "hqmusicurl":"HQ_MUSIC_URL",
      "thumb_media_id":"THUMB_MEDIA_ID" 
    }
}

 * @author shipeng
 *
 */
public class CCMsg4Music extends AbstractCustomerServiceMsg {
	/**音乐标题*/
	private String title;
	/**音乐描述*/
	private String description;
	/**音乐链接*/
	private String musicurl;
	/**高品质音乐链接，wifi环境优先使用该链接播放音乐 */
	private String hqmusicurl;
	/**缩略图的媒体ID */
	private String thumbMediaId;
	
	public CCMsg4Music() {
		this.setMsgtype(AbstractMsg.COMMON_TYPE_MUSIC);
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

	public String getMusicurl() {
		return musicurl;
	}

	public void setMusicurl(String musicurl) {
		this.musicurl = musicurl;
	}

	public String getHqmusicurl() {
		return hqmusicurl;
	}

	public void setHqmusicurl(String hqmusicurl) {
		this.hqmusicurl = hqmusicurl;
	}

	public String getThumbMediaId() {
		return thumbMediaId;
	}

	public void setThumbMediaId(String thumbMediaId) {
		this.thumbMediaId = thumbMediaId;
	}
	
	@Override
	public String toJson() {
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("touser", this.getTouser());
		map.put("msgtype", this.getMsgtype());
		Map<String, Object> musicMap = new LinkedHashMap<String, Object>();
		musicMap.put("title", this.getTitle());
		musicMap.put("description", this.getDescription());
		musicMap.put("musicurl", this.getMusicurl());
		musicMap.put("hqmusicurl", this.getHqmusicurl());
		musicMap.put("thumb_media_id", this.getThumbMediaId());
		map.put("music", musicMap);
		return JsonUtil.object2PrettyString(map,false);
	}
}
