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
	
	public static final String ENCODING_AESKEY = "jWmYm7qr5nMoAUwZRjGtBxmz3KA1tkAj3ykkR6q2B2C";
	
	public static final String CORPID = "wx70d50c6d416b9c42";
	
	@Test
	public void testValiateEchoString() throws AesException { 
		WXBizMsgCrypt msgCrypt = new WXBizMsgCrypt(TOKEN, ENCODING_AESKEY, CORPID);
		String msg_signature = "b6d84b2834cea51dec3b8f20a1910f76f63226af";
		String timestamp = "1450250856";
		String nonce = "1180994578";
		String echostr = "<xml><ToUserName><![CDATA[wx70d50c6d416b9c42]]></ToUserName><Encrypt><![CDATA[NeIjyaaCQMCNEHQIsNwnoazpLHWSe9xa6nsLWwFWgszCkdjt0G4Kn5Mc3Dta9IEU9PgVX3HHxB3hlaKD+R4HEQmuUJvyjVY3hXGqgzu/U5r95aCRL7QFN00SpMi5Cews4kx9qIDPYqclZxtuh6U9EYIEcw9UbntfpwwXjQAt22+3KZ/wMdsOmMgCc5IMZXXymRdh1R3/Os3o8uJkjXrZ9caVJVj0t8d8w4HhUYyDwH/mfA+fp27onIxuMrDMKDZWNQKm5uTgJ2DA8Db8shVmk786nt2LdnIPi70AOGvZHzZ48Rytb3mXokmXGjzYVCU/jRPiBA3LQK61cKGArMRgDMtL5/2QfuFqCbYvLfseIQjboQxtok2Yb+doXNoaWwJtxG4BRqnC4jmyDWnsn1SZaHMUySwfWEBKnaXLZ4GXzeA=]]></Encrypt><AgentID><![CDATA[6]]></AgentID></xml>";
		
//		String string  = msgCrypt.VerifyURL(msg_signature, timestamp, nonce, echostr);
		String string= msgCrypt.DecryptMsg(msg_signature, timestamp, nonce, echostr);
		System.out.println(string);
	}
}
