/**
 * 
 */
package com.vanstone.weixin.corp.client.conf.impl;

import com.vanstone.centralserver.common.corp.ICorpApp;
import com.vanstone.centralserver.common.corp.PassiveMsgListener;

/**
 * @author shipeng
 *
 */
public class CorpAppImpl implements ICorpApp {

	private int id;
	private String desc;
	private PassiveMsgListener listener;
	private String token;
	private String encodingAESKey;

	@Override
	public int getId() {
		return this.id;
	}

	@Override
	public String getDesc() {
		return this.desc;
	}

	@Override
	public PassiveMsgListener getListener() {
		return this.listener;
	}

	@Override
	public String getToken() {
		return token;
	}

	@Override
	public String getEncodingAESKey() {
		return this.encodingAESKey;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public void setListener(PassiveMsgListener listener) {
		this.listener = listener;
	}
	
	public void setToken(String token) {
		this.token = token;
	}

	public void setEncodingAESKey(String encodingAESKey) {
		this.encodingAESKey = encodingAESKey;
	}

}
