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
public class PassiveImageReply extends AbstractPassiveReply {
	
	private String mediaID;
	
	public PassiveImageReply() {
		super(CorpMsgType.Image);
	}

	public String getMediaID() {
		return mediaID;
	}
	
	public void setMediaID(String mediaID) {
		this.mediaID = mediaID;
	}
	
	@Override
	protected void __to_encrypt_json_internal(Element rootElement) {
		MyAssert.hasText(this.mediaID);
		Element ImageElement = rootElement.addElement("Image");
		Element MediaIdElement = ImageElement.addElement("MediaId");
		MediaIdElement.setText(this.getMediaID());
	}
	
}
