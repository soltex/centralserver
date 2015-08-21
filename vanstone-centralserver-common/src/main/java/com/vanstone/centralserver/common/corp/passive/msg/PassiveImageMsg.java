/**
 * 
 */
package com.vanstone.centralserver.common.corp.passive.msg;

import org.dom4j.Element;

import com.vanstone.centralserver.common.corp.passive.AbstractPassiveMsg;
import com.vanstone.centralserver.common.corp.passive.CorpMsgType;

/**
 * @author shipeng
 *
 */
public class PassiveImageMsg extends AbstractPassiveMsg {

	private String picUrl;
	private String mediaID;

	public PassiveImageMsg() {
		super(CorpMsgType.IMAGE);
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public String getMediaID() {
		return mediaID;
	}

	public void setMediaID(String mediaID) {
		this.mediaID = mediaID;
	}

	@Override
	protected void initial_internal(Element rootElement) {
		Element PicUrlElement = rootElement.element("PicUrl");
		if (PicUrlElement != null) {
			this.picUrl = PicUrlElement.getTextTrim();
		}
		Element MediaIdElement = rootElement.element("MediaId");
		if (MediaIdElement != null) {
			this.mediaID = MediaIdElement.getTextTrim();
		}
	}
}
