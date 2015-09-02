/**
 * 
 */
package com.vanstone.weixin.corp.client.conf.xml;

import java.util.Iterator;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.vanstone.centralserver.common.corp.PassiveMsgListener;
import com.vanstone.weixin.corp.client.conf.CorpClientConf;
import com.vanstone.weixin.corp.client.conf.IConfInitiator;
import com.vanstone.weixin.corp.client.conf.impl.CorpImpl;

/**
 * @author shipeng
 * 默认配置实现
 */
public class DefaultXmlConfInitiator implements IConfInitiator {
	
	public static final String CONF = "/weixin-corp.xml";
	
	@SuppressWarnings("unchecked")
	@Override
	public void initial() {
		SAXReader reader = new SAXReader();
		Document confXmlDocument = null;
		try {
			confXmlDocument = reader.read(this.getClass().getResourceAsStream(CONF));
		} catch (DocumentException e) {
			e.printStackTrace();
			throw new ExceptionInInitializerError(e);
		}
		
		Element rootElement = confXmlDocument.getRootElement();
		//默认监听器初始化
		String stringDefaultListenerClass = rootElement.elementText("default-listener");
		if (!StringUtils.isEmpty(stringDefaultListenerClass)) {
			try {
				PassiveMsgListener defaultPassiveMsgListenr = (PassiveMsgListener)Class.forName(stringDefaultListenerClass).newInstance();
				CorpClientConf.getInstance().setDefaultPassiveMsgListener(defaultPassiveMsgListenr);
			} catch (InstantiationException e) {
				e.printStackTrace();
				throw new ExceptionInInitializerError(e);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
				throw new ExceptionInInitializerError(e);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				throw new ExceptionInInitializerError(e);
			}
		}
		
		//corp 信息初始化
		Element corpElement = rootElement.element("corp");
		String appID = corpElement.attributeValue("appID");
		String appSecret = corpElement.attributeValue("appSecret");
		String jsapiNonceString = corpElement.attributeValue("jsapinoncestr");
		if (jsapiNonceString == null || jsapiNonceString.equals("")) {
			jsapiNonceString = UUID.randomUUID().toString();
		}
		
		if (StringUtils.isEmpty(appID) || StringUtils.isEmpty(appSecret)) {
			throw new ExceptionInInitializerError("APPID OR App Secret IS Blank");
		}
		CorpImpl corpImpl = new CorpImpl();
		corpImpl.setAppID(appID);
		corpImpl.setAppSecret(appSecret);
		corpImpl.setJsAPINoncestr(jsapiNonceString);
		
		CorpClientConf.getInstance().setCorp(corpImpl);
		
		//corp app infos
		Iterator<Element> corpAppIterator = rootElement.elementIterator("corp-app");
		while (corpAppIterator.hasNext()) {
			Element corpAppElement = corpAppIterator.next();
			String id = corpAppElement.attributeValue("id");
			String desc = corpAppElement.elementText("desc");
			String stringlistener = corpAppElement.elementText("listener");
			String token = corpAppElement.elementText("token");
			String encodingAESKey = corpAppElement.elementText("encoding-aes-key");
			if (StringUtils.isEmpty(id) || StringUtils.isEmpty(token) || StringUtils.isEmpty(encodingAESKey)) {
				throw new ExceptionInInitializerError("ID || TOKEN || encodingAESKey is blank.");
			}
			PassiveMsgListener passiveMsgListener = null;
			if (StringUtils.isEmpty(stringlistener)) {
				passiveMsgListener = CorpClientConf.getInstance().getDefaultPassiveMsgListener();
			}else {
				try {
					passiveMsgListener = (PassiveMsgListener)Class.forName(stringlistener).newInstance();
				} catch (InstantiationException e) {
					e.printStackTrace();
					throw new ExceptionInInitializerError(e);
				} catch (IllegalAccessException e) {
					e.printStackTrace();
					throw new ExceptionInInitializerError(e);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
					throw new ExceptionInInitializerError(e);
				}
			}
			CorpClientConf.getInstance().addCorpApp(Integer.parseInt(id), desc, passiveMsgListener, token, encodingAESKey);
		}
	}
}
