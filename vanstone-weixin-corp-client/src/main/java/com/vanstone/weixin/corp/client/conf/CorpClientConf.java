/**
 * 
 */
package com.vanstone.weixin.corp.client.conf;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import com.vanstone.centralserver.common.MyAssert;
import com.vanstone.centralserver.common.corp.ICorp;
import com.vanstone.centralserver.common.corp.ICorpApp;
import com.vanstone.centralserver.common.corp.PassiveMsgListener;
import com.vanstone.weixin.corp.client.conf.impl.CorpAppImpl;

/**
 * @author shipeng
 * 
 */
public class CorpClientConf {

	/** 企业号 */
	private ICorp corp;
	/** 应用项配置 */
	private Map<Integer, ICorpApp> corpAppMap = new LinkedHashMap<Integer, ICorpApp>();
	/** 默认监听器 */
	private PassiveMsgListener defaultPassiveMsgListener;

	public static CorpClientConf instance = new CorpClientConf();

	public static CorpClientConf getInstance() {
		return instance;
	}

	public ICorp getCorp() {
		return corp;
	}

	public void setCorp(ICorp corp) {
		this.corp = corp;
	}

	public Map<Integer, ICorpApp> getCorpAppMap() {
		return corpAppMap;
	}

	public PassiveMsgListener getDefaultPassiveMsgListener() {
		return defaultPassiveMsgListener;
	}

	public void setDefaultPassiveMsgListener(PassiveMsgListener defaultPassiveMsgListener) {
		this.defaultPassiveMsgListener = defaultPassiveMsgListener;
	}

	public void addCorpApp(int corpAppID, ICorpApp corpApp) {
		this.corpAppMap.put(corpAppID, corpApp);
	}

	public void addCorpApp(int corpAppID, String desc, PassiveMsgListener listener, String token, String encodingAESKey) {
		MyAssert.notNull(listener);
		MyAssert.hasText(token);
		MyAssert.hasText(encodingAESKey);
		CorpAppImpl corpAppImpl = new CorpAppImpl();
		corpAppImpl.setId(corpAppID);
		corpAppImpl.setDesc(desc);
		corpAppImpl.setListener(listener);
		corpAppImpl.setToken(token);
		corpAppImpl.setEncodingAESKey(encodingAESKey);
		this.corpAppMap.put(corpAppID, corpAppImpl);
	}
	
	public void clearCorpApps() {
		this.corpAppMap.clear();
	}
	
	public ICorpApp getCorpApp(int appID) {
		return this.corpAppMap.get(appID);
	}
	
	public Map<Integer, ICorpApp> getCorpAppAsMap() {
		return this.corpAppMap;
	}

	public Collection<ICorpApp> getCorpApps() {
		return this.corpAppMap.values();
	}
}
