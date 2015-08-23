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
public class PassiveVoiceReply extends AbstractPassiveReply {
	
	private String mediaID;
	
	public PassiveVoiceReply() {
		super(CorpMsgType.Voice);
	}
	
	public String getMediaID() {
		return mediaID;
	}

	public void setMediaID(String mediaID) {
		this.mediaID = mediaID;
	}

	/* (non-Javadoc)
	 * @see com.vanstone.centralserver.common.corp.passive.AbstractPassiveReply#__to_encrypt_json_internal(org.dom4j.Element)
	 */
	@Override
	protected void __to_encrypt_json_internal(Element rootElement) {
		MyAssert.hasText(this.mediaID);
		Element VoiceElement = rootElement.addElement("Voice");
		Element MediaIdElement = VoiceElement.addElement("MediaId");
		MediaIdElement.setText(this.getMediaID());
	}
	
}
