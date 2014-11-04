/**
 * 
 */
package com.vanstone.centralserver.common.weixin.wrap.msg.mass;

import java.util.Date;

/**
 * @author shipeng
 * 
 */
public class UploadBeanResult {

	private String type;
	private String mediaId;
	private Date createAt;
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Date getCreateAt() {
		return createAt;
	}
	
	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	public String getMediaId() {
		return mediaId;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}
	
}
