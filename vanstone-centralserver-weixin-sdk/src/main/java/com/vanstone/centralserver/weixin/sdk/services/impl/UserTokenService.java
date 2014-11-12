/**
 * 
 */
package com.vanstone.centralserver.weixin.sdk.services.impl;

import java.io.IOException;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vanstone.centralserver.common.Constants;
import com.vanstone.centralserver.common.JsonUtil;
import com.vanstone.centralserver.common.weixin.WeixinException;
import com.vanstone.centralserver.common.weixin.WeixinException.ErrorCode;
import com.vanstone.centralserver.common.weixin.wrap.token.UserToken;
import com.vanstone.centralserver.weixin.sdk.services.IPersistenceService;
import com.vanstone.centralserver.weixin.sdk.services.IUserTokenService;
import com.vanstone.framework.business.services.DefaultBusinessService;

/**
 * @author shipengpipi@126.com
 */
@Service("userTokenService")
public class UserTokenService extends DefaultBusinessService implements IUserTokenService {
	
    private static final long serialVersionUID = -1403028030806712136L;
    
	private static Logger LOG = LoggerFactory.getLogger(UserTokenService.class);
	
	@Autowired
	private IPersistenceService persistenceService;
	
	/**
	 * 通过Appname获取用户Token信息
	 * @return
	 */
	public UserToken loadUserTokenFromWebServer(String appid,String appSecret) throws WeixinException {
		
		String url = Constants.getUserTokenURL(appid, appSecret);
		
		HttpParams params = new BasicHttpParams();
		params.setParameter("charset", Constants.SYS_CHARSET_UTF8_STRING);
		HttpClient httpClient = new DefaultHttpClient(params);
		
		HttpGet httpGet = new HttpGet(url);
		try {
	       HttpResponse httpResponse = httpClient.execute(httpGet);
	       if (httpResponse == null || httpResponse.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
	        	throw new WeixinException(WeixinException.ErrorCode.WEIXIN_SERVER_ERROR);
	        }
			String json = null;
			try {
				json = EntityUtils.toString(httpResponse.getEntity());
			} catch (ParseException e) {
				throw new WeixinException(WeixinException.ErrorCode.WEIXIN_SERVER_ERROR,e);
			} catch (IOException e) {
				throw new WeixinException(WeixinException.ErrorCode.WEIXIN_SERVER_ERROR,e);
			}
			if (LOG.isDebugEnabled()) {
				LOG.debug(json);
			}
			Map<String,Object> map = JsonUtil.json2Map(json);
			WeixinException.ErrorCode errorCode = WeixinException.ErrorCode.parseErrorCode(((Number)map.get("errcode")) != null ? ((Number)map.get("errcode")).intValue() : null);
			if (errorCode != null && !errorCode.equals(WeixinException.ErrorCode.WEIXIN_SUCCESS_0)) {
				LOG.error(errorCode.getCode() + " -- " + errorCode.getDesc());
				throw new WeixinException(errorCode);
			}
	       String token = (String)map.get("access_token");
    	   Integer expireTimeInSecond = ((Number)map.get("expires_in")).intValue();
    	   UserToken userToken = new UserToken(appid, appSecret, token, expireTimeInSecond);
    	   LOG.info(JsonUtil.object2PrettyString(userToken,true));
    	   return userToken;
        } catch (ClientProtocolException e) {
	        e.printStackTrace();
	        throw new WeixinException(ErrorCode.WEIXIN_SERVER_ERROR);
        } catch (IOException e) {
	        e.printStackTrace();
	        throw new WeixinException(ErrorCode.WEIXIN_SERVER_ERROR);
        } finally {
        	httpClient.getConnectionManager().shutdown();
        }
	}
}
