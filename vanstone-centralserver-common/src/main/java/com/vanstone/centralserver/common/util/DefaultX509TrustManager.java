/**
 * 
 */
package com.vanstone.centralserver.common.util;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.X509TrustManager;

/**
 * @author shipeng
 *
 */
public class DefaultX509TrustManager implements X509TrustManager {

	@Override
	public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
		
	}

	@Override
	public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
		
	}
	
	@Override
	public X509Certificate[] getAcceptedIssuers() {
		return null;
	}
	
}
