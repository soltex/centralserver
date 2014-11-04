/**
 * 
 */
package com.vanstone.configuration.sdk.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.vanstone.centralserver.common.Constants;
import com.vanstone.centralserver.common.MyAssert;
import com.vanstone.centralserver.common.WebserviceResponse;
import com.vanstone.configuration.sdk.ConfNotFoundException;
import com.vanstone.configuration.sdk.IConfSDKManager;

/**
 * @author shipeng
 */
public class ConfSDKManagerImpl implements IConfSDKManager{

	private static Logger LOG = LoggerFactory.getLogger(ConfSDKManagerImpl.class);
	
	@Override
	public void updateConf(String groupId, String dataId, String value) throws ConfNotFoundException {
		if (groupId == null || "".equals(groupId)) {
			groupId = Constants.DEFAULT_GROUP_NAME;
		}
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(Constants.getWebserviceUpdateConf(groupId, dataId));
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("value", value));
		try {
			httpPost.setEntity(new UrlEncodedFormEntity(params,Constants.SYS_CHARSET_UTF8));
			HttpResponse httpResponse = httpClient.execute(httpPost);
			LOG.info("Update conf status code " + httpResponse.getStatusLine().getStatusCode());
			if (httpResponse != null && httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				String strJson = EntityUtils.toString(httpResponse.getEntity());
				LOG.debug("Update conf response string : " + strJson);
				if (strJson != null && !"".equals(strJson)) {
					Gson gson = new Gson();
					WebserviceResponse webserviceResponse = gson.fromJson(strJson, WebserviceResponse.class);
					if (webserviceResponse.getResponseCode().equals(WebserviceResponse.ResponseCode.Success)) {
						return;
					}
				}
			}
			throw new ConfNotFoundException();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			httpClient.getConnectionManager().shutdown();
		}
	}
	
	@Override
	public void updateConf(String dataId, String value) throws ConfNotFoundException {
		this.updateConf(Constants.DEFAULT_GROUP_NAME, dataId, value);
	}

	@Override
	public boolean addConf(String groupId, String dataId, String value) {
		MyAssert.hasText(dataId);
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(Constants.getWebserviceAddConf());
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("value", value));
		params.add(new BasicNameValuePair("groupId", groupId));
		params.add(new BasicNameValuePair("dataId", dataId));
		try {
			httpPost.setEntity(new UrlEncodedFormEntity(params,Constants.SYS_CHARSET_UTF8));
			HttpResponse httpResponse = httpClient.execute(httpPost);
			LOG.info("Add conf status code " + httpResponse.getStatusLine().getStatusCode());
			if (httpResponse != null && httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				String strJson = EntityUtils.toString(httpResponse.getEntity());
				LOG.debug("Add conf response string : " + strJson);
				if (strJson != null && !"".equals(strJson)) {
					Gson gson = new Gson();
					WebserviceResponse webserviceResponse = gson.fromJson(strJson, WebserviceResponse.class);
					if (webserviceResponse.getResponseCode().equals(WebserviceResponse.ResponseCode.Success)) {
						return true;
					}
				}
			}
			return false;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} finally {
			httpClient.getConnectionManager().shutdown();
		}
	}
	
}
