/**
 * 
 */
package com.vanstone.weixin.corp.client.impl;

import com.vanstone.centralserver.common.corp.ICorpApp;

/**
 * @author shipeng
 *
 */
public enum CorpAppInstance implements ICorpApp {

	App0(0, "应用0"), App1(1, "应用1"), App3(3, "应用3"), App4(4, "应用4"), App5(5, "应用5");

	private int id;
	private String desc;
	
	private CorpAppInstance(int id, String desc) {
		this.id = id;
		this.desc = desc;
	}

	@Override
	public int getId() {
		return this.id;
	}
	
	@Override
	public String getDesc() {
		return this.desc;
	}

	@Override
	public String getCallbackURL() {
		return null;
	}

	@Override
	public String getToken() {
		return null;
	}

	@Override
	public String getEncodingAESKey() {
		return null;
	}
	
}
