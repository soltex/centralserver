/**
 * 
 */
package com.vanstone.centralserver.common.corp.passive.msg;

import org.dom4j.Element;

import com.vanstone.centralserver.common.corp.passive.AbstractPassiveMsg;
import com.vanstone.centralserver.common.corp.passive.PassiveCorpMsgType;

/**
 * @author shipeng
 *
 */
public class PassiveVoiceMsg extends AbstractPassiveMsg {

	private String mediaID;
	private String format;

	public PassiveVoiceMsg() {
		super(PassiveCorpMsgType.VOICE);
	}

	public String getMediaID() {
		return mediaID;
	}

	public void setMediaID(String mediaID) {
		this.mediaID = mediaID;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	@Override
	protected void initial_internal(Element rootElement) {
		Element MediaIdElement = rootElement.element("MediaId");
		if (MediaIdElement != null) {
			this.mediaID = MediaIdElement.getTextTrim();
		}
		Element FormatElement = rootElement.element("Format");
		if (FormatElement != null) {
			this.format = FormatElement.getTextTrim();
		}
	}

}
