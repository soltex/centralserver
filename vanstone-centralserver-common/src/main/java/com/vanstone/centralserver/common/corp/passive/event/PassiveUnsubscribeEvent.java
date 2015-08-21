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
public class PassiveUnsubscribeEvent extends AbstractPassiveEvent {
	
	public PassiveUnsubscribeEvent() {
		super(CorpWeixinEvent.UNSUBSCRIBE);
	}

	@Override
	protected void initial_internal(Element rootElement) {
		
	}
	
}
