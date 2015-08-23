/**
 * 
 */
package com.vanstone.centralserver.common.corp.passive.reply;

import org.dom4j.Element;

import com.vanstone.centralserver.common.MyAssert;
import com.vanstone.centralserver.common.corp.msg.CorpMsgType;
import com.vanstone.centralserver.common.corp.passive.AbstractPassiveReply;

/**
 * @author shipeng
 *
 */
public class PassiveVideoReply extends AbstractPassiveReply {
	
	private String mediaID;
	private String title;
	private String description;
	
	public PassiveVideoReply() {
		super(CorpMsgType.Video);
	}
	
	public String getMediaID() {
		return mediaID;
	}

	public void setMediaID(String mediaID) {
		this.mediaID = mediaID;
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

	/* (non-Javadoc)
	 * @see com.vanstone.centralserver.common.corp.passive.AbstractPassiveReply#__to_encrypt_json_internal(org.dom4j.Element)
	 */
	@Override
	protected void __to_encrypt_json_internal(Element rootElement) {
		MyAssert.hasText(this.mediaID);
		Element VideoElement = rootElement.addElement("Video");
		
		Element MediaIdElement = VideoElement.addElement("MediaId");
		MediaIdElement.setText(this.getMediaID());
		
		if (this.getTitle() != null && !this.getTitle().equals("")) {
			Element TitleElement = VideoElement.addElement("Title");
			TitleElement.setText(this.getTitle());
		}
		
		if (this.getDescription() != null && !this.getDescription().equals("")) {
			Element DescriptionElement = VideoElement.addElement("Description");
			DescriptionElement.setText(this.getDescription());
		}
		
	}

}
