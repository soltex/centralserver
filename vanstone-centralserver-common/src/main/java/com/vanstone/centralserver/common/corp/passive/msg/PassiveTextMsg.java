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
public class PassiveTextMsg extends AbstractPassiveMsg {
	
	private String content;
	
	public PassiveTextMsg() {
		super(CorpMsgType.TEXT);
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	protected void initial_internal(Element rootElement) {
		Element contentElement = rootElement.element("Content");
		if (contentElement != null) {
			this.setContent(contentElement.getTextTrim());
		}
	}
	
}
