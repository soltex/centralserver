/**
 * 
 */
package com.vanstone.centralserver.common.corp.passive.event;

import org.dom4j.Element;

import com.vanstone.centralserver.common.corp.passive.CorpWeixinEvent;

/**
 * @author shipeng
 *
 */
public class PassiveEnterAgentEvent extends AbstractPassiveEvent {
	
	private String eventKey;
	
	public PassiveEnterAgentEvent() {
		super(CorpWeixinEvent.ENTER_AGENT);
	}

	public String getEventKey() {
		return eventKey;
	}

	public void setEventKey(String eventKey) {
		this.eventKey = eventKey;
	}

	@Override
	protected void initial_internal(Element rootElement) {
		Element EventKeyElement = rootElement.element("EventKey");
		if (EventKeyElement != null) {
			this.eventKey = EventKeyElement.getTextTrim();
		}
	}
	
}
