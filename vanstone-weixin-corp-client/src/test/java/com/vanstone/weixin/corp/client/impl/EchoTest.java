/**
 * 
 */
package com.vanstone.weixin.corp.client.impl;

import org.junit.Test;

import com.vanstone.centralserver.common.util.aes.AesException;
import com.vanstone.centralserver.common.util.aes.WXBizMsgCrypt;

/**
 * @author shipeng
 *
 */
public class EchoTest {
	
	public static final String TOKEN = "sagacity";
	
	public static final String ENCODING_AESKEY = "yvggURuWFXPqlFVHr8WXe1aNnoU8MzDIDpwYhi4rcNu";
	
	public static final String CORPID = "1";
	
	@Test
	public void testValiateEchoString() throws AesException { 
		WXBizMsgCrypt msgCrypt = new WXBizMsgCrypt(TOKEN, ENCODING_AESKEY, CORPID);
		String msg_signature = "2d034fa35785a8b8bd47ccbaadb78cce456baf39";
		String timestamp = "1440334725";
		String nonce = "454297962";
		String echostr = "EHhEV/sQiqqr1J1iN1MsZsUwKKYQ2voL9kJYAAADw41uiMdirTiFyd2pnxscppKsZjnNmb4P6qFFjeeMXFYM0Q==";
		
		String string  = msgCrypt.VerifyURL(msg_signature, timestamp, nonce, echostr);
		System.out.println(string);
	}
}
