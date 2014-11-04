/**
 * 
 */
package com.vanstone.centralserver.common.weixin.wrap.msg.mass;

import com.vanstone.centralserver.common.JsonUtil;

/**
 * 上传Video资源
 * @author shipeng
 */
public class UploadVideoBean extends AbstractUploadBean {

	/**资源ID*/
	private String media_id;
	/**标题*/
	private String title;
	/**描述信息*/
	private String description;
	
	public UploadVideoBean(String media_id, String title, String description) {
		this.media_id = media_id ;
		this.title = title;
		this.description = description;
	}
	
	public String getMedia_id() {
		return media_id;
	}

	public void setMedia_id(String media_id) {
		this.media_id = media_id;
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
	
	/**
	 * 转换为Json
	 * @return
	 */
	public String toJson() {
		return JsonUtil.object2PrettyString(this, false);
	}
	
}
