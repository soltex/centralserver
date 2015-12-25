/**
 * 
 */
package com.vanstone.weixin.client.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;
import com.google.gson.JsonObject;
import com.vanstone.centralserver.common.Constants;
import com.vanstone.centralserver.common.JsonUtil;
import com.vanstone.centralserver.common.MyAssert;
import com.vanstone.centralserver.common.ServletUtil;
import com.vanstone.centralserver.common.URLUtil;
import com.vanstone.centralserver.common.conf.VanstoneConf;
import com.vanstone.centralserver.common.util.HttpClientTemplate;
import com.vanstone.centralserver.common.util.HttpClientTemplate.HttpClientCallback;
import com.vanstone.centralserver.common.weixin.AppDevInfo;
import com.vanstone.centralserver.common.weixin.WeixinException;
import com.vanstone.centralserver.common.weixin.WeixinException.ErrorCode;
import com.vanstone.centralserver.common.weixin.wrap.Language;
import com.vanstone.centralserver.common.weixin.wrap.MediaType;
import com.vanstone.centralserver.common.weixin.wrap.QRType;
import com.vanstone.centralserver.common.weixin.wrap.Sex;
import com.vanstone.centralserver.common.weixin.wrap.menu.Menu;
import com.vanstone.centralserver.common.weixin.wrap.msg.AbstractCustomerServiceMsg;
import com.vanstone.centralserver.common.weixin.wrap.msg.CCMsg4Image;
import com.vanstone.centralserver.common.weixin.wrap.msg.CCMsg4Music;
import com.vanstone.centralserver.common.weixin.wrap.msg.CCMsg4News;
import com.vanstone.centralserver.common.weixin.wrap.msg.CCMsg4Text;
import com.vanstone.centralserver.common.weixin.wrap.msg.CCMsg4Video;
import com.vanstone.centralserver.common.weixin.wrap.msg.CCMsg4Voice;
import com.vanstone.centralserver.common.weixin.wrap.msg.ReplyMsg4Image;
import com.vanstone.centralserver.common.weixin.wrap.msg.ReplyMsg4Music;
import com.vanstone.centralserver.common.weixin.wrap.msg.ReplyMsg4News;
import com.vanstone.centralserver.common.weixin.wrap.msg.ReplyMsg4Text;
import com.vanstone.centralserver.common.weixin.wrap.msg.ReplyMsg4Video;
import com.vanstone.centralserver.common.weixin.wrap.msg.ReplyMsg4Voice;
import com.vanstone.centralserver.common.weixin.wrap.msg.mass.AbstractGroupMassMsg;
import com.vanstone.centralserver.common.weixin.wrap.msg.mass.AbstractOpenIdsMassMsg;
import com.vanstone.centralserver.common.weixin.wrap.msg.mass.MassResult;
import com.vanstone.centralserver.common.weixin.wrap.msg.mass.UploadBeanResult;
import com.vanstone.centralserver.common.weixin.wrap.msg.mass.UploadNewsBean;
import com.vanstone.centralserver.common.weixin.wrap.msg.mass.UploadVideoBean;
import com.vanstone.centralserver.common.weixin.wrap.msg.template.TemplateBodyData;
import com.vanstone.centralserver.common.weixin.wrap.oauth2.OAuth2AccessTokenResult;
import com.vanstone.centralserver.common.weixin.wrap.oauth2.OAuth2CallbackResult;
import com.vanstone.centralserver.common.weixin.wrap.oauth2.OAuth2UserInfo;
import com.vanstone.centralserver.common.weixin.wrap.oauth2.Scope;
import com.vanstone.centralserver.common.weixin.wrap.qrcode.Ticket;
import com.vanstone.centralserver.common.weixin.wrap.user.UserGroupInfo;
import com.vanstone.centralserver.common.weixin.wrap.user.UserOpenIdCollection;
import com.vanstone.centralserver.common.weixin.wrap.user.UserWeixinBaseInfo;
import com.vanstone.weixin.client.IWeixinAPIManager;

/**
 * 微信操作管理类
 * @author shipeng
 */
public class WeixinAPIManagerImpl implements IWeixinAPIManager {
	
	private static Logger LOG = LoggerFactory.getLogger(WeixinAPIManagerImpl.class);
	/**AccessTokenSubcriber*/
	private AccessTokenSubscriber accessTokenSubscriber = AccessTokenSubscriber.getInstance();
	/**HttpClientTemplate*/
	private HttpClientTemplate httpClientTemplate = new HttpClientTemplate();
	
	@Override
	public void addAppname(String appname) {
		accessTokenSubscriber.addAppname(appname);
	}
	
	@Override
	public Collection<String> getAppnames() {
		return VanstoneConf.getInstance().getAppnames();
	}
	
	@Override
	public void removeAppname(String appname) {
		accessTokenSubscriber.removeAppname(appname);
	}
	
	@Override
	public void clearAppnames() {
		accessTokenSubscriber.clearAppnames();
	}
	
	public AccessTokenSubscriber getAccessTokenSubscriber() {
		return accessTokenSubscriber;
	}

	/**
	 * 验证当前Appname 并返回获取AccessToken
	 * @param appname
	 * @return
	 * @throws WeixinException
	 */
	private String validateAndReturnAccessToken(String appname) throws WeixinException {
		MyAssert.hasText(appname);
		String accessToken = accessTokenSubscriber.getAccessToken(appname);
		if (accessToken == null || "".equals(accessToken)) {
			throw new IllegalArgumentException();
		}
		LOG.info("validate and return accesstoken -> appname : " + appname + " accesstoken : " + accessToken);
		return accessToken;
	}
	
	@Override
	public boolean validateWeixin(HttpServletRequest servletRequest, HttpServletResponse servletResponse) {
		if (servletRequest == null || servletResponse == null) {
			throw new IllegalArgumentException();
		}
		String echostr = servletRequest.getParameter("echostr");
		if (echostr == null || "".equals(echostr)) {
			return false;
		}
		servletResponse.setContentType("text/html;charset=utf-8");
		try {
	        servletResponse.getWriter().write(echostr);
	        return true;
        } catch (IOException e) {
	        e.printStackTrace();
	        return false;
        }
	}
	
	@Override
	public UserWeixinBaseInfo getUserWeixinBaseInfo(final String appname, final String openId, final Language language) throws WeixinException {
		if (appname == null || "".equals(appname) || openId == null || "".equals(openId) || language == null) {
			throw new IllegalArgumentException();
		}
		final String accessToken = validateAndReturnAccessToken(appname);
		final String url = Constants.getRetrievalWeixinUserInfoURL(accessToken, openId, language.getCode());
		HttpGet httpGet = new HttpGet(url);
		
		return this.httpClientTemplate.execute(httpGet, new HttpClientCallback<UserWeixinBaseInfo>() {

			@Override
			public UserWeixinBaseInfo executeHttpResponse(HttpResponse httpResponse, Map<String, Object> map) throws WeixinException {
				Integer subscribe = ((Number)map.get("subscribe")) != null ? ((Number)map.get("subscribe")).intValue() : null;
				String openid = (String)map.get("openid");
				String nickname = (String)map.get("nickname");
				Integer sex = ((Number)map.get("sex")) != null ? ((Number)map.get("sex")).intValue() : null;
				String city = (String)map.get("city");
				String province = (String)map.get("province");
				String country = (String)map.get("country");
				String headimgurl = (String)map.get("headimgurl");
				Long subscribe_time = ((Number)map.get("subscribe_time")) != null ? ((Number)map.get("subscribe_time")).longValue() : null;
				String unionid = (String)map.get("unionid");
				UserWeixinBaseInfo info = new UserWeixinBaseInfo();
				info.setSubscribe(subscribe == 0 ? false : true);
				info.setOpenid(openid);
				info.setNickname(nickname);
				if (sex != null && sex == 1) {
					info.setSex(Sex.Male);
				}else if (sex != null && sex == 2) {
					info.setSex(Sex.FMale);
				}
				info.setLanguage(language);
				info.setCity(city);
				info.setProvince(province);
				info.setCountry(country);
				if (headimgurl != null && !headimgurl.equals("")) {
					int n = headimgurl.lastIndexOf("/");
					headimgurl =headimgurl.substring(0,n+1);
					info.setHeadimgurl0(headimgurl + 0);
					info.setHeadimgurl130(headimgurl + 130);
					info.setHeadimgurl46(headimgurl + 46);
					info.setHeadimgurl64(headimgurl + 64);
					info.setHeadimgurl96(headimgurl + 96);
				}
				info.setSubscribeTime(subscribe_time != null ? new Date(subscribe_time) : null);
				info.setUnionid(unionid);
				return info;
			}
		});
	}
	
	@Override
	public UserGroupInfo createUserGroupInfo(String appname, String name) throws WeixinException {
		if (appname == null || "".equals(appname) || name == null || "".equals(name)) {
			throw new IllegalArgumentException();
		}
		final String accessToken = validateAndReturnAccessToken(appname);
		final String url = Constants.getCreateUserGroupURL(accessToken);
		HttpPost httpPost = new HttpPost(url);
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("name", name);
		JsonObject rootJsonObject = new JsonObject();
		rootJsonObject.add("group", jsonObject);
		httpPost.setEntity(new StringEntity(JsonUtil.object2PrettyString(rootJsonObject,false), Constants.SYS_CHARSET_UTF8));
		return this.httpClientTemplate.execute(httpPost, new HttpClientCallback<UserGroupInfo>() {
			
			@SuppressWarnings("unchecked")
			@Override
			public UserGroupInfo executeHttpResponse(HttpResponse httpResponse, Map<String, Object> map) throws WeixinException {
				Map<String, Object> groupMap = (Map<String, Object>)map.get("group");
				Integer id = ((Number)groupMap.get("id")).intValue();
				String name = (String)groupMap.get("name");
				UserGroupInfo info = new UserGroupInfo();
				info.setId(id);
				info.setName(name);
				return info;
			}
		});
	}
	
	@Override
	public Collection<UserGroupInfo> getUserGroupInfos(String appname) throws WeixinException {
		if (appname == null || "".equals(appname)) {
			throw new IllegalArgumentException();
		}
		final String accessToken = validateAndReturnAccessToken(appname);
		final String url = Constants.getAllUserGroupsURL(accessToken);
		
		HttpGet httpGet = new HttpGet(url);
		return this.httpClientTemplate.execute(httpGet, new HttpClientCallback<Collection<UserGroupInfo>>() {
			@SuppressWarnings("unchecked")
			@Override
			public Collection<UserGroupInfo> executeHttpResponse(HttpResponse httpResponse, Map<String, Object> map) throws WeixinException {
				Collection<Map<String, Object>> groups = (Collection<Map<String,Object>>)map.get("groups");
				if  (groups == null || groups.size() <=0) {
					return null;
				}
				Collection<UserGroupInfo> userGroupInfos = new ArrayList<UserGroupInfo>();
				for (Map<String, Object> gMap : groups) {
					Integer id = ((Number)gMap.get("id")).intValue();
					String name = (String)gMap.get("name");
					int count = gMap.get("count") != null ? ((Number)gMap.get("count")).intValue() : 0;
					UserGroupInfo info = new UserGroupInfo();
					info.setId(id);
					info.setName(name);
					info.setCount(count);
					userGroupInfos.add(info);
				}
				return userGroupInfos;
			}
		});
	}

	@Override
	public void updateUserGroupInfo(String appname, Integer groupid, String newName) throws WeixinException {
		if (appname == null || "".equals(appname) || groupid == null || "".equals(groupid) || newName == null || "".equals(newName)) {
			throw new IllegalArgumentException();
		}
		final String accessToken = validateAndReturnAccessToken(appname);
		final String url = Constants.getUpdateUserGroupURL(accessToken);
		HttpPost httpPost = new HttpPost(url);
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("id", groupid);
		jsonObject.addProperty("name", newName);
		JsonObject rootJsonObject = new JsonObject();
		rootJsonObject.add("group", jsonObject);
		httpPost.setEntity(new StringEntity(JsonUtil.object2PrettyString(rootJsonObject,false), Constants.SYS_CHARSET_UTF8));
		this.httpClientTemplate.execute(httpPost, new HttpClientCallback<Object>() {
			@Override
			public Object executeHttpResponse(HttpResponse httpResponse, Map<String, Object> map) throws WeixinException {
				return null;
			}
		});
	}
	
	@Override
	public Integer getUserGroupInfo(String appname, String openId) throws WeixinException {
		if (appname == null || "".equals(appname) || openId == null || "".equals(openId)) {
			throw new IllegalArgumentException();
		}
		final String accessToken = validateAndReturnAccessToken(appname);
		final String url = Constants.getUserGroupIdURL(accessToken);
		
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("openid",openId);
		HttpPost httpPost = new HttpPost(url);
		httpPost.setEntity(new StringEntity(JsonUtil.object2PrettyString(jsonObject,false), Constants.SYS_CHARSET_UTF8));
		return this.httpClientTemplate.execute(httpPost, new HttpClientCallback<Integer>() {
			@Override
			public Integer executeHttpResponse(HttpResponse httpResponse, Map<String, Object> map) throws WeixinException {
				Integer groupid = map.get("groupid") != null ? ((Number)map.get("groupid")).intValue() : null;
				return groupid;
			}
		});
	}
	
	@Override
    public UserOpenIdCollection getUserOpenIdCollection(String appname, String nextUserOpenId) throws WeixinException {
		if (appname == null || "".equals(appname)) {
			throw new IllegalArgumentException();
		}
		final String accessToken = validateAndReturnAccessToken(appname);
		final String url = Constants.getRetrievalUserOpenIdsURL(accessToken, nextUserOpenId);
		HttpGet httpGet = new HttpGet(url);
		
		return this.httpClientTemplate.execute(httpGet, new HttpClientCallback<UserOpenIdCollection>() {

			@SuppressWarnings("unchecked")
			@Override
			public UserOpenIdCollection executeHttpResponse(HttpResponse httpResponse, Map<String, Object> map) throws WeixinException {
				int total = map.get("total") != null ? ((Number)map.get("total")).intValue() : 0;
		        int count = map.get("count") != null ? ((Number)map.get("count")).intValue() : 0;
		        String nextOpenId = (String)map.get("next_openid");
		        if (total == 0 || count == 0) {
		        	return null;
		        }
		        Map<String, List<String>> dataMap = (Map<String, List<String>>)map.get("data");
		        UserOpenIdCollection userOpenIdCollection = new UserOpenIdCollection();
		        userOpenIdCollection.setNextOpenId(nextOpenId);
		        List<String> openids = dataMap.get("openid");
		        userOpenIdCollection.addOpenIds(openids);
				return userOpenIdCollection;
			}
		});
    }

	@Override
	public void createWeixinMenu(String appName, Menu menu) throws WeixinException {
		if (menu == null) {
			throw new IllegalArgumentException();
		}
		if (appName == null || "".equals(appName)) {
			throw new IllegalArgumentException();
		}
		
		final String accessToken = validateAndReturnAccessToken(appName);
		
		HttpPost httpPost = new HttpPost(Constants.getCreateMenuURL(accessToken));
		StringEntity stringEntity = new StringEntity(menu.toJson(), Constants.SYS_CHARSET_UTF8);
		httpPost.setEntity(stringEntity);
		
		this.httpClientTemplate.execute(httpPost, new HttpClientCallback<Object>() {
			@Override
			public Object executeHttpResponse(HttpResponse httpResponse, Map<String, Object> map) throws WeixinException {
				return null;
			}
		});
	}
	
	@Override
	public void sendCCMsgText(String appname, CCMsg4Text ccMsg4Text) throws WeixinException {
		this._sendCCMsg(appname, ccMsg4Text);
	}

	@Override
	public void sendCCMsgImage(String appname, CCMsg4Image ccMsg4Image) throws WeixinException {
		this._sendCCMsg(appname, ccMsg4Image);
	}

	@Override
	public void sendCCMsgVoice(String appname, CCMsg4Voice ccMsg4Voice) throws WeixinException {
		this._sendCCMsg(appname, ccMsg4Voice);
	}

	@Override
	public void sendCCMsgVideo(String appname, CCMsg4Video ccMsg4Video) throws WeixinException {
		this._sendCCMsg(appname, ccMsg4Video);
	}

	@Override
	public void sendCCMsgMusic(String appname, CCMsg4Music ccMsg4Music) throws WeixinException {
		this._sendCCMsg(appname, ccMsg4Music);
	}

	@Override
	public void sendCCMsgNews(String appname, CCMsg4News ccMsg4News) throws WeixinException {
		this._sendCCMsg(appname, ccMsg4News);
	}
	
	private void _sendCCMsg(String appname,AbstractCustomerServiceMsg msg) throws WeixinException {
		if (appname == null || "".equals(appname) || msg == null) {
			throw new IllegalArgumentException();
		}
		final String accessToken = validateAndReturnAccessToken(appname);
		final String url = Constants.getCCMsgUrl(accessToken);
		
		HttpPost httpPost = new HttpPost(url);
		StringEntity stringEntity = new StringEntity(msg.toJson(), Constants.SYS_CHARSET_UTF8);
		httpPost.setEntity(stringEntity);
		
		this.httpClientTemplate.execute(httpPost, new HttpClientCallback<Object>() {
			@Override
			public Object executeHttpResponse(HttpResponse httpResponse, Map<String, Object> map) throws WeixinException {
				return null;
			}
		});
	}

	@Override
	public void sendReplyMsg4Image(ReplyMsg4Image replyMsg4Image, HttpServletResponse servletResponse) {
		Preconditions.checkNotNull(replyMsg4Image);
		Preconditions.checkNotNull(servletResponse);
		ServletUtil.write(servletResponse, replyMsg4Image.toReplyXml(), Constants.SYS_CHARSET_UTF8_STRING);
	}

	@Override
	public void sendReplyMsg4Music(ReplyMsg4Music replyMsg4Music, HttpServletResponse servletResponse) {
		Preconditions.checkNotNull(replyMsg4Music);
		Preconditions.checkNotNull(servletResponse);
		ServletUtil.write(servletResponse, replyMsg4Music.toReplyXml(), Constants.SYS_CHARSET_UTF8_STRING);
	}

	@Override
	public void sendReplyMsg4News(ReplyMsg4News replyMsg4News, HttpServletResponse servletResponse) {
		Preconditions.checkNotNull(replyMsg4News);
		Preconditions.checkNotNull(servletResponse);
		ServletUtil.write(servletResponse, replyMsg4News.toReplyXml(), Constants.SYS_CHARSET_UTF8_STRING);
	}

	@Override
	public void sendReplyMsg4Text(ReplyMsg4Text replyMsg4Text, HttpServletResponse servletResponse) {
		Preconditions.checkNotNull(replyMsg4Text);
		Preconditions.checkNotNull(servletResponse);
		ServletUtil.write(servletResponse, replyMsg4Text.toReplyXml(), Constants.SYS_CHARSET_UTF8_STRING);
	}

	@Override
	public void sendReplyMsg4Video(ReplyMsg4Video replyMsg4Video, HttpServletResponse servletResponse) {
		Preconditions.checkNotNull(replyMsg4Video);
		Preconditions.checkNotNull(servletResponse);
		ServletUtil.write(servletResponse, replyMsg4Video.toReplyXml(), Constants.SYS_CHARSET_UTF8_STRING);
	}

	@Override
	public void sendReplyMsg4Voice(ReplyMsg4Voice replyMsg4Voice, HttpServletResponse servletResponse) {
		Preconditions.checkNotNull(replyMsg4Voice);
		Preconditions.checkNotNull(servletResponse);
		ServletUtil.write(servletResponse, replyMsg4Voice.toReplyXml(), Constants.SYS_CHARSET_UTF8_STRING);
	}
	
	@Override
    public String uploadMedia(String appname, File file, MediaType mediaType) throws WeixinException {
		if (file == null || mediaType == null || appname == null || "".equals(appname)) {
			throw new IllegalArgumentException();
		}
		
		if (mediaType.equals(MediaType.Image)) {
			//图片文件
			if (file.length() > Constants.MAX_IMAGE_FILE_SIZE) {
				throw new WeixinException(ErrorCode.WEIXIN_UPLOAD_IMAGE_GT_MAXSIZE);
			}
		}else if (mediaType.equals(MediaType.Voice)) {
			if (file.length() > Constants.MAX_VOICE_FILE_SIZE) {
				throw new WeixinException(ErrorCode.WEIXIN_UPLOAD_VOICE_GT_MAXSIZE);
			}
		}else if (mediaType.equals(MediaType.Video)) {
			if (file.length() > Constants.MAX_VIDEO_FILE_SIZE) {
				throw new WeixinException(ErrorCode.WEIXIN_UPLOAD_VIDEO_GT_MAXSIZE);
			}
		}else {
			if (file.length() > Constants.MAX_THUMB_FILE_SIZE) {
				throw new WeixinException(ErrorCode.WEIXIN_UPLOAD_THUMB_GT_MAXSIZE);
			}
		}
		
		final String accessToken = validateAndReturnAccessToken(appname);
		
		HttpPost httpPost = new HttpPost(Constants.getUploadMediaURL(accessToken, mediaType.getCode()));
		FileBody fileBody = new FileBody(file);
		MultipartEntity multipartEntity = new MultipartEntity();
		multipartEntity.addPart("media", fileBody);
		httpPost.setEntity(multipartEntity);
		
		return httpClientTemplate.execute(httpPost, new HttpClientCallback<String>() {
			@Override
			public String executeHttpResponse(HttpResponse httpResponse, Map<String, Object> jsonMap)
					throws WeixinException {
				WeixinException.ErrorCode errorCode = jsonMap.get("errcode") != null ? WeixinException.ErrorCode.parseErrorCode(((Number)jsonMap.get("errcode")).intValue()) : null;
				if (errorCode != null && !errorCode.equals(WeixinException.ErrorCode.WEIXIN_SUCCESS_0)) {
					throw new WeixinException(errorCode);
				}
				String mediaId = (String)jsonMap.get("media_id");
				return mediaId;
			}
		});
    }
	
	@Override
    public void downloadMediaById(String appname, String mediaId, File file) throws WeixinException {
		if (mediaId == null || "".equals(mediaId) || file == null) {
			throw new IllegalArgumentException();
		}
		HttpClient httpClient = this.httpClientTemplate.createHttpClient();
		final String accessToken = validateAndReturnAccessToken(appname);
		
		HttpGet httpGet = new HttpGet(Constants.getDownloadMediaURL(accessToken, mediaId));
		try {
	        HttpResponse httpResponse = httpClient.execute(httpGet);
	        Header[] headers = httpResponse.getHeaders("Content-Type");
	        if (headers != null && headers.length >0) {
	        	for (Header header : headers) {
	        		String value = header.getValue();
	        		if (value.indexOf("Text") != -1 || value.indexOf("text") != -1) {
	        			String json = EntityUtils.toString(httpResponse.getEntity());
	        			if (json == null || "".equals(json)) {
	        				throw new IllegalArgumentException();
	        			}
	        	        if (LOG.isInfoEnabled()) {
	        				LOG.info(json);
	        			}
	        			Map<String, Object> jsonMap = JsonUtil.json2Map(json);
	        			WeixinException.ErrorCode errorCode = WeixinException.ErrorCode.parseErrorCode(((Number)jsonMap.get("errcode")).intValue());
	        			if (!errorCode.equals(WeixinException.ErrorCode.WEIXIN_SUCCESS_0)) {
	        				throw new WeixinException(errorCode);
	        			}
	        			return;
	        		}
	        	}
	        }
	        InputStream is = httpResponse.getEntity().getContent();
	        FileOutputStream fos = new FileOutputStream(file);
	        byte[] buffer = new byte[4096];
	        int n = -1;
	        while ((n = is.read(buffer)) != -1) {
	        	fos.write(buffer, 0, n);
	        }
	        is.close();
	        fos.close();
         } catch (ClientProtocolException e) {
	        e.printStackTrace();
	        throw new IllegalArgumentException();
        } catch (IOException e) {
	        e.printStackTrace();
	        throw new IllegalArgumentException();
        } finally {
        	httpClient.getConnectionManager().shutdown();
        }
    }

	@Override
	public Ticket getPermanentQCTicket(final String appname, final Integer sceneId) throws WeixinException {
		if (appname == null || "".equals(appname) || sceneId == null || "".equals(sceneId)) {
			throw new IllegalArgumentException();
		}
		
		final String accessToken = validateAndReturnAccessToken(appname);
		final String url = Constants.getCreateQCTicketURL(accessToken);
		
		//{"action_name": "QR_LIMIT_SCENE", "action_info": {"scene": {"scene_id": 123}}}
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("action_name", QRType.QR_SCENE.toString());
		
		Map<String, Object> sceneMap = new LinkedHashMap<String, Object>();
		sceneMap.put("scene_id", sceneId);
		Map<String, Object> actionInfoMap = new LinkedHashMap<String, Object>();
		actionInfoMap.put("scene", sceneMap);
		map.put("action_info", actionInfoMap);
		String json = JsonUtil.object2PrettyString(map,false);
		
		StringEntity stringEntity = new StringEntity(json, Constants.SYS_CHARSET_UTF8);
		
		HttpPost httpPost = new HttpPost(url);
		httpPost.setEntity(stringEntity);
		
		return httpClientTemplate.execute(httpPost, new HttpClientCallback<Ticket>() {
			@Override
			public Ticket executeHttpResponse(HttpResponse httpResponse, Map<String, Object> jsonMap) throws WeixinException {
				Ticket ticket = new Ticket();
				ticket.setTicket((String)jsonMap.get("ticket"));
				return ticket;
			}
		});
	}
	
	@Override
	public Ticket getTmpQCTicket(final String appname,  final int expireTime, final Integer sceneId) throws WeixinException {
		if (appname == null || "".equals(appname) || sceneId == null || "".equals(sceneId)) {
			throw new IllegalArgumentException();
		}
		if (expireTime > 1800 || expireTime < 0) {
			throw new IllegalArgumentException();
		}
		
		final String accessToken = validateAndReturnAccessToken(appname);
		final String url = Constants.getCreateQCTicketURL(accessToken);
		//{"expire_seconds": 1800, "action_name": "QR_SCENE", "action_info": {"scene": {"scene_id": 123}}}
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("expire_seconds", expireTime);
		
		map.put("action_name", QRType.QR_LIMIT_SCENE.toString());
		
		Map<String, Object> sceneMap = new LinkedHashMap<String, Object>();
		sceneMap.put("scene_id", sceneId);
		Map<String, Object> actionInfoMap = new LinkedHashMap<String, Object>();
		actionInfoMap.put("scene", sceneMap);
		map.put("action_info", actionInfoMap);
		String json = JsonUtil.object2PrettyString(map,false);
		
		StringEntity stringEntity = new StringEntity(json, Constants.SYS_CHARSET_UTF8);
		
		HttpPost httpPost = new HttpPost(url);
		httpPost.setEntity(stringEntity);
		
		return this.httpClientTemplate.execute(httpPost, new HttpClientCallback<Ticket>() {
			@Override
			public Ticket executeHttpResponse(HttpResponse httpResponse, Map<String, Object> jsonMap) throws WeixinException {
				Ticket ticket = new Ticket();
				ticket.setTicket((String)jsonMap.get("ticket"));
				ticket.setExpireSeconds(expireTime);
				return ticket;
			}
		});
	}
	
	@Override
	public void downloadQCImage(String ticket, File image) throws WeixinException {
		if (ticket == null || "".equals(ticket) || image == null) {
			throw new IllegalArgumentException();
		}
		HttpClient httpClient = this.httpClientTemplate.createHttpClient();
		HttpGet httpGet = new HttpGet(Constants.getRetrievalQCImageURL(ticket));
		try {
			HttpResponse httpResponse = httpClient.execute(httpGet);
			if (httpResponse == null || httpResponse.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
				throw new WeixinException(ErrorCode.WEIXIN_SERVER_ERROR);
			}
			InputStream is = httpResponse.getEntity().getContent();
			FileOutputStream fos = new FileOutputStream(image);
			byte[] buffer = new byte[4096];
			int n = -1;
			while ((n = is.read(buffer)) != -1) {
				fos.write(buffer, 0, n);
			}
			fos.flush();
			fos.close();
			is.close();
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

	@Override
	public String buildShortUrl(String appname, String url) throws WeixinException {
		MyAssert.hasText(appname);
		MyAssert.hasText(url);
		
		final String accessToken = validateAndReturnAccessToken(appname);
		final Map<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("action", "long2short");
		map.put("long_url", url);
		
		String json = JsonUtil.object2PrettyString(map,false);
		
		StringEntity stringEntity = new StringEntity(json, Constants.SYS_CHARSET_UTF8);
		
		HttpPost httpPost = new HttpPost(Constants.getShortUrlURL(accessToken));
		httpPost.setEntity(stringEntity);
		
		return this.httpClientTemplate.execute(httpPost, new HttpClientCallback<String>() {
			@Override
			public String executeHttpResponse(HttpResponse httpResponse, Map<String, Object> jsonMap) throws WeixinException {
				Object shortUrl = jsonMap.get("short_url");
				if (shortUrl != null) {
					return (String)shortUrl;
				}
				return null;
			}
		});
	}
	
	@Override
	public void close() {
		this.accessTokenSubscriber.close();
		LOG.info("Weixin API Manager Close.");
	}

	@Override
	public UploadBeanResult uploadNewsBean(String appname, UploadNewsBean bean) throws WeixinException {
		MyAssert.notNull(bean);
		MyAssert.hasText(appname);
		final String accessToken = validateAndReturnAccessToken(appname);
		final String url = Constants.getUploadNewsURL(accessToken);
		
		StringEntity stringEntity = new StringEntity(bean.toJson(), Constants.SYS_CHARSET_UTF8);
		
		HttpPost httpPost = new HttpPost(url);
		httpPost.setEntity(stringEntity);
		
		return this.httpClientTemplate.execute(httpPost, new HttpClientCallback<UploadBeanResult>() {
			@Override
			public UploadBeanResult executeHttpResponse(HttpResponse httpResponse, Map<String, Object> map) throws WeixinException {
				String type = (String)map.get("type");
				String media_id  = (String)map.get("media_id");
				Long createdAt = ((Number)map.get("created_at")).longValue();
				UploadBeanResult bean = new UploadBeanResult();
				bean.setCreateAt(new Date(createdAt));
				bean.setMediaId(media_id);
				bean.setType(type);
				return bean;
			}
		});
	}
	
	@Override
	public UploadBeanResult uploadVideoBean(final String appname, UploadVideoBean bean) throws WeixinException {
		MyAssert.notNull(bean);
		MyAssert.hasText(appname);
		final String accessToken = validateAndReturnAccessToken(appname);
		final String url = Constants.getUploadNewsURL(accessToken);
		
		StringEntity stringEntity = new StringEntity(bean.toJson(), Constants.SYS_CHARSET_UTF8);
		
		HttpPost httpPost = new HttpPost(url);
		httpPost.setEntity(stringEntity);
		
		return this.httpClientTemplate.execute(httpPost, new HttpClientCallback<UploadBeanResult>() {
			@Override
			public UploadBeanResult executeHttpResponse(HttpResponse httpResponse, Map<String, Object> map) throws WeixinException {
				String type = (String)map.get("type");
				String media_id  = (String)map.get("media_id");
				Long createdAt = ((Number)map.get("created_at")).longValue();
				UploadBeanResult bean = new UploadBeanResult();
				bean.setCreateAt(new Date(createdAt));
				bean.setMediaId(media_id);
				bean.setType(type);
				return bean;
			}
		});
	}
	
	@Override
	public UploadBeanResult uploadVideoBean(String appname, File file, String title, String description) throws WeixinException {
		String mediaId = this.uploadMedia(appname, file, MediaType.Video);
		UploadVideoBean bean = new UploadVideoBean(mediaId, title, description);
		return this.uploadVideoBean(appname, bean);
	}
	
	@Override
	public MassResult sendMassMsgByGroup(String appname, AbstractGroupMassMsg massMsg) throws WeixinException {
		MyAssert.notNull(massMsg);
		MyAssert.hasText(appname);
		final String accessToken = validateAndReturnAccessToken(appname);
		final String url = Constants.getSendMassMsgByGroupId(accessToken);
		
		StringEntity stringEntity = new StringEntity(massMsg.toJson(), Constants.SYS_CHARSET_UTF8);
		HttpPost httpPost = new HttpPost(url);
		httpPost.setEntity(stringEntity);
		
		MassResult massResult = this.httpClientTemplate.execute(httpPost, new HttpClientCallback<MassResult>() {

			@Override
			public MassResult executeHttpResponse(HttpResponse httpResponse, Map<String, Object> map) throws WeixinException {
				String type = (String)map.get("type");
				int errcode = ((Number)map.get("errcode")).intValue();
				String errmsg = (String)map.get("errmsg");
				int msgId = ((Number)map.get("msg_id")).intValue();
				MassResult massResult = new MassResult();
				massResult.setErrcode(errcode);
				massResult.setErrmsg(errmsg);
				massResult.setMsg_id(msgId);
				massResult.setType(type);
				return massResult;
			}
			
		});
		return massResult;
	}
	
	
	@Override
	public MassResult sendMassMsgByOpenIds(String appname, AbstractOpenIdsMassMsg massMsg) throws WeixinException {
		
		MyAssert.notNull(massMsg);
		MyAssert.hasText(appname);
		final String accessToken = validateAndReturnAccessToken(appname);
		final String url = Constants.getSendMassMsgByOpenIds(accessToken);
		
		StringEntity stringEntity = new StringEntity(massMsg.toJson(), Constants.SYS_CHARSET_UTF8);
		HttpPost httpPost = new HttpPost(url);
		httpPost.setEntity(stringEntity);
		
		MassResult massResult = this.httpClientTemplate.execute(httpPost, new HttpClientCallback<MassResult>() {
			@Override
			public MassResult executeHttpResponse(HttpResponse httpResponse, Map<String, Object> map) throws WeixinException {
				String type = (String)map.get("type");
				int errcode = ((Number)map.get("errcode")).intValue();
				String errmsg = (String)map.get("errmsg");
				int msgId = ((Number)map.get("msg_id")).intValue();
				MassResult massResult = new MassResult();
				massResult.setErrcode(errcode);
				massResult.setErrmsg(errmsg);
				massResult.setMsg_id(msgId);
				massResult.setType(type);
				return massResult;
			}
		});
		return massResult;
	}
	
	@Override
	public void deleteMassMsg(String appname, int msgid) throws WeixinException {
		MyAssert.hasText(appname);
		final String accessToken = validateAndReturnAccessToken(appname);
		final String url = Constants.getDeleteMassMsgURL(accessToken);
		Map<String, Object> dataMap = new LinkedHashMap<String, Object>();
		dataMap.put("msg_id", msgid);
		StringEntity stringEntity = new StringEntity(JsonUtil.object2PrettyString(dataMap, false), Constants.SYS_CHARSET_UTF8);
		HttpPost httpPost = new HttpPost(url);
		httpPost.setEntity(stringEntity);
		
		this.httpClientTemplate.execute(httpPost, new HttpClientCallback<Map<String, Object>>() {
			@Override
			public Map<String, Object> executeHttpResponse(HttpResponse httpResponse, Map<String, Object> map) throws WeixinException {
				return null;
			}
		});
	}
	
	@Override
	public String getOAuth2Url(String appname, String redirectUrl, Scope scope, String state) {
		MyAssert.hasText(appname);
		MyAssert.hasText(redirectUrl);
		MyAssert.notNull(scope);
		String appid = this.accessTokenSubscriber.getAppDevInfo(appname).getAppid();
		return Constants.getOAuth2CallbackAddress(appid, URLUtil.urlencode(redirectUrl), scope.toString(), state != null && !state.equals("") ? state : null);
	}
	
	@Override
	public OAuth2AccessTokenResult getOAuth2AccessTokenResult(String appname, String code) throws WeixinException {
		MyAssert.hasText(appname);
		MyAssert.hasText(code);
		AppDevInfo appDevInfo = this.accessTokenSubscriber.getAppDevInfo(appname);
		final String url = Constants.getOAuth2AccessTokenURL(appDevInfo.getAppid(), appDevInfo.getSecret(), code);
		HttpGet httpGet = new HttpGet(url);
		return this.httpClientTemplate.execute(httpGet, new HttpClientCallback<OAuth2AccessTokenResult>() {
			@Override
			public OAuth2AccessTokenResult executeHttpResponse(HttpResponse httpResponse, Map<String, Object> map) throws WeixinException {
				String access_token = (String)map.get("access_token");
				int expires_in = ((Number)map.get("expires_in")).intValue();
				String refresh_token = (String)map.get("refresh_token");
				String openid = (String)map.get("openid");
				String scope = (String)map.get("scope");
				OAuth2AccessTokenResult result = new OAuth2AccessTokenResult();
				result.setAccessToken(access_token);
				result.setExpireIn(expires_in);
				result.setRefreshToken(refresh_token);
				result.setOpenId(openid);
				result.setScope(scope);
				return result;
			}
		});
	}
	
	
	@Override
	public OAuth2AccessTokenResult refreshOAuth2AccessTokenResult(String appname, String refreshToken) throws WeixinException {
		MyAssert.hasText(appname);
		MyAssert.hasText(refreshToken);
		AppDevInfo appDevInfo = this.accessTokenSubscriber.getAppDevInfo(appname);
		if (appDevInfo == null) {
			throw new IllegalArgumentException();
		}
		final String url = Constants.getOAuth2RefreshAccessTokenURL(appDevInfo.getAppid(), refreshToken);
		HttpGet httpGet = new HttpGet(url);
		return this.httpClientTemplate.execute(httpGet, new HttpClientCallback<OAuth2AccessTokenResult>() {
			@Override
			public OAuth2AccessTokenResult executeHttpResponse(HttpResponse httpResponse, Map<String, Object> map) throws WeixinException {
				String access_token = (String)map.get("access_token");
				int expires_in = ((Number)map.get("expires_in")).intValue();
				String refresh_token = (String)map.get("refresh_token");
				String openid = (String)map.get("openid");
				String scope = (String)map.get("scope");
				OAuth2AccessTokenResult result = new OAuth2AccessTokenResult();
				result.setAccessToken(access_token);
				result.setExpireIn(expires_in);
				result.setRefreshToken(refresh_token);
				result.setOpenId(openid);
				result.setScope(scope);
				return result;
			}
		});
	}
	
	@Override
	public OAuth2UserInfo getOAuth2UserInfo(String oauth2AccessToken, String openId, Language language) throws WeixinException {
		MyAssert.hasText(oauth2AccessToken);
		MyAssert.hasText(openId);
		MyAssert.notNull(language);
		final String url = Constants.getOAuth2UserInfoURL(oauth2AccessToken, openId, language.getCode());
		HttpGet httpGet = new HttpGet(url);
		return this.httpClientTemplate.execute(httpGet, new HttpClientCallback<OAuth2UserInfo>() {
			@Override
			public OAuth2UserInfo executeHttpResponse(HttpResponse httpResponse, Map<String, Object> map) throws WeixinException {
				String openid = (String)map.get("openid");
				String nickname = (String)map.get("nickname");
				Integer sex = ((Number)map.get("sex")) != null ? ((Number)map.get("sex")).intValue() : null;
				String province = (String)map.get("province");
				String city = (String)map.get("city");
				String country = (String)map.get("country");
				String headimgurl = (String)map.get("headimgurl");
				@SuppressWarnings("unchecked")
				List<String> privileges = (List<String>)map.get("privilege");
				
				OAuth2UserInfo info = new OAuth2UserInfo();
				info.setOpenId(openid);
				info.setNickname(nickname);
				if (sex != null && sex == 1) {
					info.setSex(Sex.Male);
				}else if (sex != null && sex == 2) {
					info.setSex(Sex.FMale);
				}
				info.setProvince(province);
				info.setCity(city);
				info.setCountry(country);
				if (headimgurl != null && !headimgurl.equals("")) {
					int n = headimgurl.lastIndexOf("/");
					headimgurl =headimgurl.substring(0,n+1);
					info.setHeadimgurl0(headimgurl + 0);
					info.setHeadimgurl130(headimgurl + 130);
					info.setHeadimgurl46(headimgurl + 46);
					info.setHeadimgurl64(headimgurl + 64);
					info.setHeadimgurl96(headimgurl + 96);
				}
				if (privileges != null && privileges.size() >0) {
					for (String str : privileges) {
						info.addPrivilege(str);
					}
				}
				return info;
			}
		});
	}
	
	@Override
	public boolean valdiateOAuth2AccessToken(String oauth2AccessToken, String openId) throws WeixinException {
		MyAssert.hasText(oauth2AccessToken);
		MyAssert.hasText(openId);
		String url  = Constants.getOAuth2ValidateAccessTokenURL(oauth2AccessToken, openId);
		HttpGet httpGet = new HttpGet(url);
		return this.httpClientTemplate.execute(httpGet, new HttpClientCallback<Boolean>() {
			@Override
			public Boolean executeHttpResponse(HttpResponse httpResponse, Map<String, Object> map) throws WeixinException {
				return true;
			}
		});
	}
	
	@Override
	public OAuth2CallbackResult getOAuth2CallbackResult(HttpServletRequest servletRequest) {
		MyAssert.notNull(servletRequest);
		String code = servletRequest.getParameter("code");
		if (code == null || code.equals("")) {
			return null;
		}
		String state = servletRequest.getParameter("state");
		OAuth2CallbackResult result = new OAuth2CallbackResult();
		result.setCode(code);
		result.setState(state);
		return result;
	}

	@Override
	public void sendTemplateMsg(String appname, TemplateBodyData templateBodyData) throws WeixinException {
		MyAssert.hasText(appname);
		String accessToken = this.validateAndReturnAccessToken(appname);
		String url = Constants.getSendTemplateMSGURL(accessToken);
		HttpPost httpPost = new HttpPost(url);
		StringEntity stringEntity = new StringEntity(templateBodyData.toJson(), Constants.SYS_CHARSET_UTF8);
		httpPost.setEntity(stringEntity);
		this.httpClientTemplate.execute(httpPost, new HttpClientCallback<Object>() {
			@Override
			public Object executeHttpResponse(HttpResponse httpResponse, Map<String, Object> map) throws WeixinException {
				return null;
			}
		});
	}
	
}