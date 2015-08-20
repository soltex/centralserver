/**
 * 
 */
package com.vanstone.centralserver.common.corp.media;

import java.util.Date;

/**
 * @author shipeng
 *
 */
public class MediaResult {

	private String mediaID;
	private MediaType mediaType;
	private Date createAt;

	public String getMediaID() {
		return mediaID;
	}
	
	public void setMediaID(String mediaID) {
		this.mediaID = mediaID;
	}

	public MediaType getMediaType() {
		return mediaType;
	}

	public void setMediaType(MediaType mediaType) {
		this.mediaType = mediaType;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

}
