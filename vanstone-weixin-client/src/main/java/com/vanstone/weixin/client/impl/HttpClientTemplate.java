/**
 * 
 */
package com.vanstone.weixin.client.impl;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vanstone.centralserver.common.Constants;
import com.vanstone.centralserver.common.JsonUtil;
import com.vanstone.centralserver.common.MyAssert;
import com.vanstone.centralserver.common.weixin.WeixinException;
import com.vanstone.centralserver.common.weixin.WeixinException.ErrorCode;

/**
 * @author shipeng
 */
public class HttpClientTemplate {
	
	private static Logger LOG = LoggerFactory.getLogger(HttpClientTemplate.class);
	
	/**
	 * 执行回调
	 * @author shipeng
	 *
	 * @param <T>
	 */
	public interface HttpClientCallback<T> {
		
		public T executeHttpResponse(HttpResponse httpResponse, Map<String, Object> map) throws WeixinException;
		
	}
	
	/**
	 * 执行HttpClient操作模板
	 * @param httpUriRequest
	 * @param callback
	 * @return
	 * @throws WeixinException
	 */
	public <T extends Object> T execute(HttpUriRequest httpUriRequest,  HttpClientCallback<T> callback) throws WeixinException {
		MyAssert.notNull(httpUriRequest);
		MyAssert.notNull(callback);
		
		T object = null;
		for (int i=0;i<Constants.DEFAULT_RETRY_REQUEST_NUM;i++) {
			try {
				HttpClient httpClient = this.createHttpClient();
				try {
					HttpResponse httpResponse = httpClient.execute(httpUriRequest);
					Map<String, Object> map = validateHttpResponse(httpResponse);
					return callback.executeHttpResponse(httpResponse, map);
				} catch (ClientProtocolException e) {
					e.printStackTrace();
					throw new WeixinException(ErrorCode.WEIXIN_SERVER_ERROR);
				} catch (IOException e) {
					e.printStackTrace();
					throw new WeixinException(ErrorCode.WEIXIN_SERVER_ERROR);
				} finally {
					if (httpClient != null) {
						httpClient.getConnectionManager().shutdown();
					}
				}
			} catch (WeixinException e) {
				LOG.error(e.getErrorCode().getCode() + " -> " + e.getErrorCode().getDesc());
				e.printStackTrace();
				LOG.error("Retry Weixin Num : " + i);
				if (i == Constants.DEFAULT_RETRY_REQUEST_NUM -1) {
					throw e;
				}
				if (e.getErrorCode().equals(WeixinException.ErrorCode.WEIXIN_ERROR_40001) || e.getErrorCode().equals(WeixinException.ErrorCode.WEIXIN_ERROR_40002) || e.getErrorCode().equals(WeixinException.ErrorCode.WEIXIN_ERROR_40003)) {
					try {
						TimeUnit.SECONDS.sleep(Constants.DEFAULT_RETRY_SLEEP_TIME);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
					continue;
				}
				throw e;
			}
		}
		return object;
	}
	
	private Map<String,Object> validateHttpResponse(HttpResponse httpResponse) throws WeixinException {
		 if (httpResponse == null || httpResponse.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
	        	throw new WeixinException(WeixinException.ErrorCode.WEIXIN_SERVER_ERROR);
	        }
			String json = null;
			try {
				json = EntityUtils.toString(httpResponse.getEntity(),Constants.SYS_CHARSET_UTF8);
			} catch (ParseException e) {
				throw new WeixinException(WeixinException.ErrorCode.WEIXIN_SERVER_ERROR,e);
			} catch (IOException e) {
				throw new WeixinException(WeixinException.ErrorCode.WEIXIN_SERVER_ERROR,e);
			}
			
			LOG.debug(json);
			
			Map<String,Object> map = JsonUtil.json2Map(json);
			
			WeixinException.ErrorCode errorCode = map.get("errcode") != null ? ErrorCode.parseErrorCode(((Number)map.get("errcode")).intValue()) : null;
			
			LOG.info("http response return errorcode : " + errorCode);
			
			if (errorCode != null && !errorCode.equals(WeixinException.ErrorCode.WEIXIN_SUCCESS_0)) {
				throw new WeixinException(errorCode);
			}
			return map;
	}
	
	/**
	 * 获取HttpClient
	 * @return
	 */
	public HttpClient createHttpClient() {
		HttpParams params = new BasicHttpParams();
		params.setParameter("charset", "utf-8");
		HttpClient httpClient = new DefaultHttpClient(params);
		httpsWrapClient(httpClient);
		return httpClient;
	}
	
	public void httpsWrapClient(HttpClient httpclient) {
		try {
			SSLContext sslcontext = SSLContext.getInstance("TLS");
			sslcontext.init(null, new TrustManager[] { new DefaultX509TrustManager() }, null);
			SSLSocketFactory sf = new SSLSocketFactory(sslcontext, SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
			//sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
			//Scheme https = new Scheme("https", sf, 443);
			Scheme https = new Scheme("https", 443, sf);
			httpclient.getConnectionManager().getSchemeRegistry().register(https);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
