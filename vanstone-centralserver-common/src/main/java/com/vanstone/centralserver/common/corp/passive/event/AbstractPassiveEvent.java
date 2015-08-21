package com.vanstone.centralserver.common.corp.passive.event;

import com.vanstone.centralserver.common.corp.passive.AbstractPassiveMsg;
import com.vanstone.centralserver.common.corp.passive.CorpMsgType;
import com.vanstone.centralserver.common.corp.passive.CorpWeixinEvent;

public abstract class AbstractPassiveEvent extends AbstractPassiveMsg {

	private CorpWeixinEvent event;

	public AbstractPassiveEvent(CorpWeixinEvent weixinEvent) {
		super(CorpMsgType.EVENT);
		this.event = weixinEvent;
	}

	public CorpWeixinEvent getEvent() {
		return event;
	}
	
}
