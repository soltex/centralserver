/**
 * 
 */
package com.vanstone.centralserver.common.corp.passive.msg;

import org.dom4j.Element;

import com.vanstone.centralserver.common.corp.passive.AbstractPassiveMsg;
import com.vanstone.centralserver.common.corp.passive.CorpMsgType;

/**
 * @author shipeng
 */
public class PassiveVideoMsg extends AbstractPassiveMsg {

	private String mediaID;
	private String thumbMediaId;

	public PassiveVideoMsg() {
		super(CorpMsgType.VIDEO);
	}

	public String getMediaID() {
		return mediaID;
	}

	public void setMediaID(String mediaID) {
		this.mediaID = mediaID;
	}

	public String getThumbMediaId() {
		return thumbMediaId;
	}

	public void setThumbMediaId(String thumbMediaId) {
		this.thumbMediaId = thumbMediaId;
	}

	@Override
	protected void initial_internal(Element rootElement) {
		Element MediaIdElement = rootElement.element("MediaId");
		if (MediaIdElement != null) {
			this.mediaID = MediaIdElement.getTextTrim();
		}
		Element ThumbMediaIdElement = rootElement.element("ThumbMediaId");
		if (ThumbMediaIdElement != null) {
			this.thumbMediaId = ThumbMediaIdElement.getTextTrim();
		}
	}
	
}
