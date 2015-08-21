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
public class PassiveSubscribeEvent extends AbstractPassiveEvent {
	
	public PassiveSubscribeEvent() {
		super(CorpWeixinEvent.SUBSCRIBE);
	}

	@Override
	protected void initial_internal(Element rootElement) {
		
	}
	
}
