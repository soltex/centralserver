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
public class PassiveTextReply extends AbstractPassiveReply {
	
	private String content;
	
	public PassiveTextReply() {
		super(CorpMsgType.Text);
	}

	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}

	@Override
	protected void __to_encrypt_json_internal(Element rootElement) {
		MyAssert.hasText(this.getContent());
		Element ContentElement = rootElement.addElement("Content");
		ContentElement.setText(this.getContent());
	}
	
}
