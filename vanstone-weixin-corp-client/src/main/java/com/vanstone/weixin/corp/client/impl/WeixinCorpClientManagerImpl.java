/**
 * 
 */
package com.vanstone.weixin.corp.client.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.util.EntityUtils;

import com.vanstone.centralserver.common.Constants;
import com.vanstone.centralserver.common.JsonUtil;
import com.vanstone.centralserver.common.MyAssert;
import com.vanstone.centralserver.common.ServletUtil;
import com.vanstone.centralserver.common.corp.CorpAppInfo;
import com.vanstone.centralserver.common.corp.ICorp;
import com.vanstone.centralserver.common.corp.ICorpApp;
import com.vanstone.centralserver.common.corp.ReportLocationFlag;
import com.vanstone.centralserver.common.corp.UserStatus;
import com.vanstone.centralserver.common.corp.WeixinOrEmail;
import com.vanstone.centralserver.common.corp.js.TicketObject;
import com.vanstone.centralserver.common.corp.media.MPNewsArticle;
import com.vanstone.centralserver.common.corp.media.MediaResult;
import com.vanstone.centralserver.common.corp.media.MediaStat;
import com.vanstone.centralserver.common.corp.media.MediaType;
import com.vanstone.centralserver.common.corp.msg.AbstractCorpMsg;
import com.vanstone.centralserver.common.corp.msg.CorpMsgResult;
import com.vanstone.centralserver.common.corp.oauth2.OAuth2Result;
import com.vanstone.centralserver.common.corp.oauth2.RedirectResult;
import com.vanstone.centralserver.common.corp.passive.AbstractPassiveReply;
import com.vanstone.centralserver.common.corp.user.CorpDepartment;
import com.vanstone.centralserver.common.corp.user.CorpUserInfo;
import com.vanstone.centralserver.common.corp.user.Tag;
import com.vanstone.centralserver.common.corp.user.UserCollectionWithTag;
import com.vanstone.centralserver.common.corp.user.UserExtAttr;
import com.vanstone.centralserver.common.util.HttpClientTemplate;
import com.vanstone.centralserver.common.util.HttpClientTemplate.HttpClientCallback;
import com.vanstone.centralserver.common.util.UnixJavaDateTimeUtil;
import com.vanstone.centralserver.common.weixin.WeixinException;
import com.vanstone.centralserver.common.weixin.WeixinException.ErrorCode;
import com.vanstone.centralserver.common.weixin.wrap.Sex;
import com.vanstone.centralserver.common.weixin.wrap.menu.Menu;
import com.vanstone.centralserver.common.weixin.wrap.oauth2.Scope;
import com.vanstone.weixin.corp.client.WeixinCorpClientManager;
import com.vanstone.weixin.corp.client.conf.CorpClientConf;

/**
 * @author shipeng
 */
public class WeixinCorpClientManagerImpl implements WeixinCorpClientManager {

	/** HttpClientTemplate */
	private HttpClientTemplate clientTemplate = new HttpClientTemplate();

	/**
	 * 获取当前AccessToken
	 * 
	 * @param corp
	 * @return
	 * @throws WeixinException
	 */
	public String getAccessToken() throws WeixinException {
		ICorp corp = CorpClientConf.getInstance().getCorp();
		HttpGet get = new HttpGet(Constants.getCorpRetrievalAccessToken(corp.getAppID(), corp.getAppSecret()));
		return this.clientTemplate.execute(get, new HttpClientCallback<String>() {
			@Override
			public String executeHttpResponse(HttpResponse httpResponse, Map<String, Object> map) throws WeixinException {
				String accessToken = (String) map.get("access_token");
				return accessToken;
			}
		});
	}

	@Override
	public void createMenu(ICorpApp corpApp, Menu menu) throws WeixinException {
		MyAssert.notNull(corpApp);
		MyAssert.notNull(menu);
		String accessToken = this.getAccessToken();
		MyAssert.hasText(accessToken);
		HttpPost httpPost = new HttpPost(Constants.getCorpCreateMenuUrl(accessToken, corpApp.getId()));
		StringEntity stringEntity = new StringEntity(menu.toJson(), Constants.SYS_CHARSET_UTF8);
		httpPost.setEntity(stringEntity);
		this.clientTemplate.execute(httpPost, new HttpClientCallback<Object>() {
			@Override
			public Object executeHttpResponse(HttpResponse httpResponse, Map<String, Object> map) throws WeixinException {
				return null;
			}
		});
	}

	@Override
	public CorpAppInfo getCorpAppInfo(ICorpApp corpApp) throws WeixinException {
		MyAssert.notNull(corpApp);
		String accessToken = this.getAccessToken();
		MyAssert.hasText(accessToken);
		HttpGet httpGet = new HttpGet(Constants.getCorpRetrievalAppInfoUrl(accessToken, corpApp.getId()));
		return this.clientTemplate.execute(httpGet, new HttpClientCallback<CorpAppInfo>() {
			@SuppressWarnings("unchecked")
			@Override
			public CorpAppInfo executeHttpResponse(HttpResponse httpResponse, Map<String, Object> map) throws WeixinException {

				CorpAppInfo corpAppInfo = new CorpAppInfo();

				int agentid = ((Number) map.get("agentid")).intValue();
				corpAppInfo.setAgentID(agentid);

				String name = (String) map.get("name");
				corpAppInfo.setName(name);

				String square_logo_url = (String) map.get("square_logo_url");
				corpAppInfo.setSquareLogoUrl(square_logo_url);
				;

				String round_logo_url = (String) map.get("round_logo_url");
				corpAppInfo.setRoundLogoUrl(round_logo_url);
				;

				String description = (String) map.get("description");
				corpAppInfo.setDescription(description);

				Integer close = ((Number) map.get("close")).intValue();
				if (close == 0) {
					corpAppInfo.setClose(false);
				} else {
					corpAppInfo.setClose(true);
				}

				String redirect_domain = (String) map.get("redirect_domain");
				corpAppInfo.setRedirectDomain(redirect_domain);

				Integer report_location_flag = ((Number) map.get("report_location_flag")).intValue();
				if (report_location_flag == 0) {
					corpAppInfo.setReportLocationFlag(ReportLocationFlag.Not_Uplaod);
				} else if (report_location_flag == 1) {
					corpAppInfo.setReportLocationFlag(ReportLocationFlag.In_Session_Upload);
				} else {
					corpAppInfo.setReportLocationFlag(ReportLocationFlag.Continue_Upload);
				}

				Integer isreportuser = ((Number) map.get("isreportuser")).intValue();
				if (isreportuser == 0) {
					corpAppInfo.setReportuser(false);
				} else {
					corpAppInfo.setReportuser(true);
				}

				Integer isreportenter = ((Number) map.get("isreportenter")).intValue();
				if (isreportenter == 0) {
					corpAppInfo.setReportenter(false);
				} else {
					corpAppInfo.setReportenter(true);
				}

				Map<String, Object> allow_userinfos = (Map<String, Object>) map.get("allow_userinfos");
				if (allow_userinfos != null && allow_userinfos.size() > 0) {
					List<Map<String, Object>> list = (List<Map<String, Object>>) allow_userinfos.get("user");
					if (list != null && list.size() > 0) {
						for (Map<String, Object> userMap : list) {
							String userid = (String) userMap.get("userid");
							String status = (String) userMap.get("status");
							corpAppInfo.addUserinfo(userid, status);
						}
					}
				}

				Map<String, Object> allow_partys = (Map<String, Object>) map.get("allow_partys");
				if (allow_partys != null && allow_partys.size() > 0) {
					List<Number> ids = (List<Number>) allow_partys.get("partyid");
					if (ids != null && ids.size() > 0) {
						for (Number id : ids) {
							corpAppInfo.addParty(id.intValue());
						}
					}
				}
				Map<String, Object> allow_tags = (Map<String, Object>) map.get("allow_tags");
				if (allow_tags != null && allow_tags.size() > 0) {
					List<Number> ids = (List<Number>) allow_tags.get("allow_tags");
					if (ids != null && ids.size() > 0) {
						for (Number id : ids) {
							corpAppInfo.addTag(id.intValue());
						}
					}
				}
				return corpAppInfo;
			}
		});
	}

	@Override
	public void updateCorpAppInfo(ICorpApp corpApp, ReportLocationFlag reportLocationFlag, String logoMediaID, String name, String description, String redirectDomain, boolean reportuser,
			boolean reportenter) throws WeixinException {
		MyAssert.notNull(corpApp);
		String accessToken = this.getAccessToken();
		MyAssert.hasText(accessToken);
		HttpPost httpPost = new HttpPost(Constants.getCorpUpdateCorpInfoUrl(accessToken));
		// {
		// "agentid": "5",
		// "report_location_flag": "0",
		// "logo_mediaid": "xxxxx",
		// "name": "NAME",
		// "description": "DESC",
		// "redirect_domain": "xxxxxx",
		// "isreportuser":0,
		// "isreportenter":0
		// }
		// StringEntity stringEntity = new StringEntity(menu.toJson(),
		// Constants.SYS_CHARSET_UTF8);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("agentid", corpApp.getId());
		params.put("report_location_flag", reportLocationFlag.getCode());
		params.put("logo_mediaid", logoMediaID);
		params.put("name", name);
		params.put("description", description);
		params.put("redirect_domain", redirectDomain);
		params.put("isreportuser", reportuser ? 1 : 0);
		params.put("isreportenter", reportenter ? 1 : 0);
		StringEntity stringEntity = new StringEntity(JsonUtil.object2PrettyString(params, false), Constants.SYS_CHARSET_UTF8);
		httpPost.setEntity(stringEntity);
		this.clientTemplate.execute(httpPost, new HttpClientCallback<Object>() {
			@Override
			public Object executeHttpResponse(HttpResponse httpResponse, Map<String, Object> map) throws WeixinException {
				return null;
			}
		});
	}

	@Override
	public Collection<CorpAppInfo> getCorpAppInfos() throws WeixinException {
		String accessToken = this.getAccessToken();
		MyAssert.hasText(accessToken);
		HttpGet httpGet = new HttpGet(Constants.getCorpAgentsListUrl(accessToken));
		return this.clientTemplate.execute(httpGet, new HttpClientCallback<Collection<CorpAppInfo>>() {
			@SuppressWarnings("unchecked")
			@Override
			public Collection<CorpAppInfo> executeHttpResponse(HttpResponse httpResponse, Map<String, Object> map) throws WeixinException {
				List<CorpAppInfo> appinfos = new ArrayList<CorpAppInfo>();
				List<Map<String, Object>> maps = (List<Map<String, Object>>) map.get("agentlist");
				if (maps == null || maps.size() <= 0) {
					return null;
				}
				for (Map<String, Object> datamap : maps) {
					Integer agentid = ((Number) datamap.get("agentid")).intValue();
					String name = (String) datamap.get("name");
					String square_logo_url = (String) datamap.get("square_logo_url");
					String round_logo_url = (String) datamap.get("round_logo_url");
					CorpAppInfo appInfo = new CorpAppInfo();
					appInfo.setAgentID(agentid);
					appInfo.setName(name);
					appInfo.setSquareLogoUrl(square_logo_url);
					appInfo.setRoundLogoUrl(round_logo_url);
					appinfos.add(appInfo);
				}
				return appinfos;
			}
		});
	}

	@Override
	public MediaResult uploadTempMedia(MediaType mediaType, File file) throws WeixinException {
		MyAssert.notNull(mediaType);
		MyAssert.notNull(file);
		String accessToken = this.getAccessToken();
		MyAssert.hasText(accessToken);
		HttpPost post = new HttpPost(Constants.getCorpUploadTempMediaUrl(accessToken, mediaType));
		FileBody fileBody = new FileBody(file);
		MultipartEntity multipartEntity = new MultipartEntity();
		multipartEntity.addPart("media", fileBody);
		post.setEntity(multipartEntity);

		return this.clientTemplate.execute(post, new HttpClientCallback<MediaResult>() {
			@Override
			public MediaResult executeHttpResponse(HttpResponse httpResponse, Map<String, Object> map) throws WeixinException {
				String type = (String) map.get("type");
				String media_id = (String) map.get("media_id");
				long created_at = ((Number) map.get("created_at")).longValue();
				MediaResult result = new MediaResult();
				result.setMediaID(media_id);
				result.setCreateAt(UnixJavaDateTimeUtil.unixToJavaDateTime(created_at));
				result.setMediaType(MediaType.parse(type));
				return result;
			}
		});
	}

	@Override
	public void downloadTempMedia(String mediaID, final File file) throws WeixinException {
		MyAssert.notNull(file);
		String accessToken = this.getAccessToken();
		MyAssert.hasText(accessToken);
		if (mediaID == null || "".equals(mediaID) || file == null) {
			throw new IllegalArgumentException();
		}
		HttpClient httpClient = this.clientTemplate.createHttpClient();
		HttpGet httpGet = new HttpGet(Constants.getCorpDownloadTempMediaUrl(accessToken, mediaID));
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
				throw new IllegalArgumentException();
			}
		}
		try {
			HttpResponse httpResponse = httpClient.execute(httpGet);
			Header[] headers = httpResponse.getHeaders("Content-Type");
			if (headers != null && headers.length > 0) {
				Header header=  headers[0];
				String value = header.getValue();
				if (value != null && !value.equals("") && value.indexOf("json") != -1) {
					String json = EntityUtils.toString(httpResponse.getEntity());
					if (json == null || "".equals(json)) {
						throw new IllegalArgumentException();
					}
					Map<String, Object> jsonMap = JsonUtil.json2Map(json);
					WeixinException.ErrorCode errorCode = WeixinException.ErrorCode.parseErrorCode(((Number) jsonMap.get("errcode")).intValue());
					if (!errorCode.equals(WeixinException.ErrorCode.WEIXIN_SUCCESS_0)) {
						throw new WeixinException(errorCode);
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
	public File downloadTempMedia(String mediaID, File filePath, String filenameWithoutExtName) throws WeixinException {
		MyAssert.notNull(filePath);
		String accessToken = this.getAccessToken();
		MyAssert.hasText(accessToken);
		if (mediaID == null || "".equals(mediaID) || filePath == null) {
			throw new IllegalArgumentException();
		}
		if (filenameWithoutExtName == null || filenameWithoutExtName.equals("")) {
			filenameWithoutExtName = UUID.randomUUID().toString();
		}
		HttpClient httpClient = this.clientTemplate.createHttpClient();
		HttpGet httpGet = new HttpGet(Constants.getCorpDownloadTempMediaUrl(accessToken, mediaID));
		filePath.mkdirs();
		String extName = null;
		try {
			HttpResponse httpResponse = httpClient.execute(httpGet);
			Header[] headers = httpResponse.getHeaders("Content-Type");
			if (headers != null && headers.length > 0) {
				Header header=  headers[0];
				String value = header.getValue();
				if (value != null && !value.equals("") && value.indexOf("json") != -1) {
					String json = EntityUtils.toString(httpResponse.getEntity());
					if (json == null || "".equals(json)) {
						throw new IllegalArgumentException();
					}
					Map<String, Object> jsonMap = JsonUtil.json2Map(json);
					WeixinException.ErrorCode errorCode = WeixinException.ErrorCode.parseErrorCode(((Number) jsonMap.get("errcode")).intValue());
					if (!errorCode.equals(WeixinException.ErrorCode.WEIXIN_SUCCESS_0)) {
						throw new WeixinException(errorCode);
					}
				}
			}
			//文件下载指定名称
			Header[] dispositionHeaders = httpResponse.getHeaders("Content-disposition");
			if (dispositionHeaders == null || dispositionHeaders.length <=0) {
				throw new IllegalArgumentException();
			}
			Header dispositionHeader = dispositionHeaders[0];
			String headerValue = dispositionHeader.getValue();
			headerValue = headerValue.replaceAll("\"", "");
			extName = FilenameUtils.getExtension(headerValue);
			
			File file = new File(filePath, filenameWithoutExtName + "." + extName);
			if(!file.exists()) {
				file.createNewFile();
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
			return file;
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
	public String uploadMPNewsArticle(ICorpApp corpApp, Collection<MPNewsArticle> articles) throws WeixinException {
		MyAssert.notNull(corpApp);
		MyAssert.notNull(articles);
		if (articles.size() > Constants.MAX_ARTICLE_ITEM_NUM) {
			throw new WeixinException(WeixinException.ErrorCode.MAX_ARTICLE_ITEM_COUNT_GT_10);
		}
		String accessToken = this.getAccessToken();
		HttpPost post = new HttpPost(Constants.getCorpForeverUploadNpnewsArticlesUrl(accessToken));
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("agentid", corpApp.getId());
		Collection<Map<String, Object>> articleMaps = new ArrayList<Map<String, Object>>();
		for (MPNewsArticle article : articles) {
			Map<String, Object> articleMap = new HashMap<String, Object>();
			articleMap.put("title", article.getTitle());
			articleMap.put("thumb_media_id", article.getThumbMediaId());
			articleMap.put("author", article.getAuthor());
			articleMap.put("content_source_url", article.getContentSourceUrl());
			articleMap.put("content", article.getContent());
			articleMap.put("digest", article.getDigest());
			articleMap.put("show_cover_pic", article.isShowCoverPic() ? 1 : 0);
			articleMaps.add(articleMap);
		}

		Map<String, Object> articlesCollection = new HashMap<String, Object>();
		articlesCollection.put("articles", articleMaps);
		map.put("mpnews", articlesCollection);

		StringEntity stringEntity = new StringEntity(JsonUtil.object2PrettyString(map, false), Constants.SYS_CHARSET_UTF8);
		post.setEntity(stringEntity);

		return this.clientTemplate.execute(post, new HttpClientCallback<String>() {
			@Override
			public String executeHttpResponse(HttpResponse httpResponse, Map<String, Object> map) throws WeixinException {
				return (String) map.get("media_id");
			}
		});
	}

	@Override
	public void updateMPNewsArticle(String mediaID, ICorpApp corpApp, Collection<MPNewsArticle> articles) throws WeixinException {
		MyAssert.notNull(corpApp);
		MyAssert.notNull(articles);
		if (articles.size() > Constants.MAX_ARTICLE_ITEM_NUM) {
			throw new WeixinException(WeixinException.ErrorCode.MAX_ARTICLE_ITEM_COUNT_GT_10);
		}
		String accessToken = this.getAccessToken();
		HttpPost post = new HttpPost(Constants.getCorpForeverUpdateUploadMpnewsArticlesUrl(accessToken));
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("agentid", corpApp.getId());
		map.put("media_id", mediaID);

		Collection<Map<String, Object>> articleMaps = new ArrayList<Map<String, Object>>();
		for (MPNewsArticle article : articles) {
			Map<String, Object> articleMap = new HashMap<String, Object>();
			articleMap.put("title", article.getTitle());
			articleMap.put("thumb_media_id", article.getThumbMediaId());
			articleMap.put("author", article.getAuthor());
			articleMap.put("content_source_url", article.getContentSourceUrl());
			articleMap.put("content", article.getContent());
			articleMap.put("digest", article.getDigest());
			articleMap.put("show_cover_pic", article.isShowCoverPic() ? 1 : 0);
			articleMaps.add(articleMap);
		}

		Map<String, Object> articlesCollection = new HashMap<String, Object>();
		articlesCollection.put("articles", articleMaps);
		map.put("mpnews", articlesCollection);

		StringEntity stringEntity = new StringEntity(JsonUtil.object2PrettyString(map, false), Constants.SYS_CHARSET_UTF8);
		post.setEntity(stringEntity);

		this.clientTemplate.execute(post, new HttpClientCallback<String>() {
			@Override
			public String executeHttpResponse(HttpResponse httpResponse, Map<String, Object> map) throws WeixinException {
				return (String) map.get("media_id");
			}
		});
	}

	@Override
	public String uploadForeverMedia(ICorpApp corpApp, MediaType mediaType, File media) throws WeixinException {
		MyAssert.notNull(corpApp);
		MyAssert.notNull(mediaType);
		MyAssert.notNull(media);

		String accessToken = this.getAccessToken();
		HttpPost post = new HttpPost(Constants.getCorpUploadForeverMediaUrl(corpApp.getId(), mediaType, accessToken));
		FileBody fileBody = new FileBody(media);
		MultipartEntity multipartEntity = new MultipartEntity();
		multipartEntity.addPart("media", fileBody);
		post.setEntity(multipartEntity);
		return this.clientTemplate.execute(post, new HttpClientCallback<String>() {
			@Override
			public String executeHttpResponse(HttpResponse httpResponse, Map<String, Object> map) throws WeixinException {
				return (String) map.get("media_id");
			}
		});
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<MPNewsArticle> downloadForeverMedia(ICorpApp corpApp, String mediaID, File file) throws WeixinException {
		String accessToken = this.getAccessToken();
		MyAssert.hasText(accessToken);
		if (mediaID == null || "".equals(mediaID)) {
			throw new IllegalArgumentException();
		}
		HttpClient httpClient = this.clientTemplate.createHttpClient();
		HttpGet httpGet = new HttpGet(Constants.getCorpDownloadForeverMediaUrl(corpApp.getId(), mediaID, accessToken));
		if (file != null && !file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
				throw new IllegalArgumentException();
			}
		}
		try {
			HttpResponse httpResponse = httpClient.execute(httpGet);
			Header[] headers = httpResponse.getHeaders("Content-Type");
			for (Header header : headers) {
				String value = header.getValue();
				if (value.indexOf("Json") != -1 || value.indexOf("json") != -1) {
					String json = EntityUtils.toString(httpResponse.getEntity());
					if (json == null || "".equals(json)) {
						throw new IllegalArgumentException();
					}
					Map<String, Object> jsonMap = JsonUtil.json2Map(json);
					Number errcode = (Number) jsonMap.get("errcode");
					if (errcode != null) {
						WeixinException.ErrorCode errorCode = WeixinException.ErrorCode.parseErrorCode(((Number) jsonMap.get("errcode")).intValue());
						throw new WeixinException(errorCode);
					} else {
						Map<String, Object> mpnewsMap = (Map<String, Object>) jsonMap.get("mpnews");
						List<Map<String, Object>> articlesList = (List<Map<String, Object>>) mpnewsMap.get("articles");
						Collection<MPNewsArticle> mpNewsArticles = new ArrayList<MPNewsArticle>();
						for (Map<String, Object> tempMap : articlesList) {
							String thumb_media_id = (String) tempMap.get("thumb_media_id");
							String title = (String) tempMap.get("title");
							String author = (String) tempMap.get("author");
							String digest = (String) tempMap.get("digest");
							String content_source_url = (String) tempMap.get("content_source_url");
							boolean show_cover_pic = ((Number) tempMap.get("show_cover_pic")).intValue() == 1 ? true : false;
							String content = (String) tempMap.get("content");
							MPNewsArticle mpNewsArticle = new MPNewsArticle(title, thumb_media_id, author, content_source_url, content, digest, show_cover_pic);
							mpNewsArticles.add(mpNewsArticle);
						}
						return mpNewsArticles;
					}
				} else {
					InputStream is = httpResponse.getEntity().getContent();
					FileOutputStream fos = new FileOutputStream(file);
					byte[] buffer = new byte[4096];
					int n = -1;
					while ((n = is.read(buffer)) != -1) {
						fos.write(buffer, 0, n);
					}
					is.close();
					fos.close();
					return null;
				}
			}
			throw new WeixinException(ErrorCode.WEIXIN_UNKNOWN_ERROR);
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
	public void deleteForeverMedia(ICorpApp corpApp, String mediaID) throws WeixinException {
		MyAssert.notNull(corpApp);
		MyAssert.hasText(mediaID);
		String accessToken = this.getAccessToken();
		HttpGet httpGet = new HttpGet(Constants.getCorpDeleteForeverMediaUrl(accessToken, corpApp.getId(), mediaID));
		this.clientTemplate.execute(httpGet, new HttpClientCallback<Object>() {
			@Override
			public Object executeHttpResponse(HttpResponse httpResponse, Map<String, Object> map) throws WeixinException {
				return null;
			}
		});
	}

	@Override
	public MediaStat getMediaStat(ICorpApp corpApp) throws WeixinException {
		MyAssert.notNull(corpApp);
		String accessToken = this.getAccessToken();
		HttpGet get = new HttpGet(Constants.getCorpForeverStatUrl(accessToken, corpApp.getId()));
		return this.clientTemplate.execute(get, new HttpClientCallback<MediaStat>() {
			@Override
			public MediaStat executeHttpResponse(HttpResponse httpResponse, Map<String, Object> map) throws WeixinException {
				int total_count = ((Number) map.get("total_count")) != null ? ((Number) map.get("total_count")).intValue() : 0;
				int image_count = ((Number) map.get("image_count")) != null ? ((Number) map.get("image_count")).intValue() : 0;
				int voice_count = ((Number) map.get("voice_count")) != null ? ((Number) map.get("voice_count")).intValue() : 0;
				int video_count = ((Number) map.get("video_count")) != null ? ((Number) map.get("video_count")).intValue() : 0;
				int file_count = ((Number) map.get("file_count")) != null ? ((Number) map.get("file_count")).intValue() : 0;
				int mpnews_count = ((Number) map.get("mpnews_count")) != null ? ((Number) map.get("mpnews_count")).intValue() : 0;
				MediaStat stat = new MediaStat();
				stat.setTotalCount(total_count);
				stat.setImageCount(image_count);
				stat.setVoiceCount(voice_count);
				stat.setVideoCount(video_count);
				stat.setFileCount(file_count);
				stat.setMpnewsCount(mpnews_count);
				return stat;
			}
		});
	}

	@Override
	public CorpMsgResult sendCorpMsg(AbstractCorpMsg corpMsg) throws WeixinException {
		String accessToken = this.getAccessToken();
		HttpPost post = new HttpPost(Constants.getCorpSendMsgUrl(accessToken));
		StringEntity stringEntity = new StringEntity(corpMsg.toJson(), Constants.SYS_CHARSET_UTF8);
		post.setEntity(stringEntity);
		return this.clientTemplate.execute(post, new HttpClientCallback<CorpMsgResult>() {
			@Override
			public CorpMsgResult executeHttpResponse(HttpResponse httpResponse, Map<String, Object> map) throws WeixinException {
				int errcode = ((Number) map.get("errcode")).intValue();
				String errmsg = (String) map.get("errmsg");
				String invaliduser = (String) map.get("invaliduser");
				String invalidparty = (String) map.get("invalidparty");
				String invalidtag = (String) map.get("invalidtag");
				ErrorCode errorCode = ErrorCode.parseErrorCode(errcode);
				return new CorpMsgResult(errorCode, errmsg, invaliduser, invalidparty, invalidtag);
			}
		});
	}

	@Override
	public void sendCorpReply(ICorpApp corpApp, AbstractPassiveReply passiveReply, String timestamp, String nonce, HttpServletResponse servletResponse) throws WeixinException {
		String replyxml = passiveReply.toEncryptJson(corpApp.getToken(), corpApp.getEncodingAESKey(), CorpClientConf.getInstance().getCorp(), timestamp, nonce);
		ServletUtil.write(servletResponse, replyxml);
	}

	@Override
	public String createOAuth2RedirectUrl(String redirectUri, String state) throws WeixinException {
		MyAssert.hasText(redirectUri);
		String url = Constants.getCorpOAuth2RedirectURL(CorpClientConf.getInstance().getCorp().getAppID(), redirectUri, Scope.snsapi_base, state);
		return url;
	}

	@Override
	public RedirectResult getRedirectResult(HttpServletRequest servletRequest) {
		MyAssert.notNull(servletRequest);
		// code=CODE&state=STATE
		String code = servletRequest.getParameter("code");
		String state = servletRequest.getParameter("state");
		if (StringUtils.isEmpty(code)) {
			return null;
		}
		RedirectResult rr = new RedirectResult();
		rr.setCode(code);
		rr.setState(state);
		return rr;
	}

	@Override
	public OAuth2Result getUserInfo(String code) throws WeixinException {
		MyAssert.hasText(code);
		final String accesstoken = this.getAccessToken();
		String url = Constants.getCorpUserInfoUrl(accesstoken, code);
		HttpGet httpGet = new HttpGet(url);
		return this.clientTemplate.execute(httpGet, new HttpClientCallback<OAuth2Result>() {
			@Override
			public OAuth2Result executeHttpResponse(HttpResponse httpResponse, Map<String, Object> map) throws WeixinException {
				String userID = (String) map.get("UserId");
				String deviceID = (String) map.get("DeviceId");
				String openID = (String) map.get("OpenId");
				return new OAuth2Result(userID, openID, deviceID);
			}
		});
	}

	@Override
	public void authSuccess(String userID) throws WeixinException {
		final String accessToken = this.getAccessToken();
		final String url = Constants.getCorpAuthSuccessUrl(accessToken, userID);
		HttpGet httpGet = new HttpGet(url);

		this.clientTemplate.execute(httpGet, new HttpClientCallback<Object>() {
			@Override
			public Object executeHttpResponse(HttpResponse httpResponse, Map<String, Object> map) throws WeixinException {
				return null;
			}
		});
	}

	@Override
	public int createDepartment(String name, int parentid, Integer order, Integer id) throws WeixinException {
		if (parentid <= 1) {
			parentid = 1;
		}
		if (id != null && id < 1) {
			throw new IllegalArgumentException();
		}

		final String accessToken = this.getAccessToken();
		HttpPost httpPost = new HttpPost(Constants.getCreateDepartmentUrl(accessToken));
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("name", name);
		params.put("parentid", parentid);
		if (order != null) {
			params.put("order", order);
		}
		if (id != null) {
			params.put("id", id);
		}
		StringEntity stringEntity = new StringEntity(JsonUtil.object2PrettyString(params, false), Constants.SYS_CHARSET_UTF8);
		httpPost.setEntity(stringEntity);
		return this.clientTemplate.execute(httpPost, new HttpClientCallback<Integer>() {
			@Override
			public Integer executeHttpResponse(HttpResponse httpResponse, Map<String, Object> map) throws WeixinException {
				Number value = (Number) map.get("id");
				return value.intValue();
			}
		});
	}
	
	@Override
	public int createDepartment(String name) throws WeixinException {
		return this.createDepartment(name, 1, null, null);
	}

	@Override
	public void updateDepartment(int id, String name, int parentid, Integer order) throws WeixinException {
		if (parentid <= 1) {
			parentid = 1;
		}
		final String accessToken = this.getAccessToken();
		HttpPost httpPost = new HttpPost(Constants.getUpdateDepartmentUrl(accessToken));
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("name", name);
		params.put("id", id);
		params.put("parentid", parentid);
		if (order != null) {
			params.put("order", order);
		}
		StringEntity stringEntity = new StringEntity(JsonUtil.object2PrettyString(params, false), Constants.SYS_CHARSET_UTF8);
		httpPost.setEntity(stringEntity);
		this.clientTemplate.execute(httpPost, new HttpClientCallback<Integer>() {
			@Override
			public Integer executeHttpResponse(HttpResponse httpResponse, Map<String, Object> map) throws WeixinException {
				return null;
			}
		});
	}

	@Override
	public void upateDepartment(int id, String name) throws WeixinException {
		this.updateDepartment(id, name, 1, null);
	}

	@Override
	public void deleteDepartment(int id) throws WeixinException {
		final String accessToken = this.getAccessToken();
		HttpGet httpGet = new HttpGet(Constants.getDeleteDepartmentUrl(accessToken, id));
		this.clientTemplate.execute(httpGet, new HttpClientCallback<Object>() {
			@Override
			public Object executeHttpResponse(HttpResponse httpResponse, Map<String, Object> map) throws WeixinException {
				return null;
			}
		});
	}

	@Override
	public List<CorpDepartment> getDepartments(int id) throws WeixinException {
		if (id <= 1) {
			id = 1;
		}
		final String accessToken = this.getAccessToken();
		HttpGet httpGet = new HttpGet(Constants.getGetDepartmentsUrl(accessToken, id));
		return this.clientTemplate.execute(httpGet, new HttpClientCallback<List<CorpDepartment>>() {
			@SuppressWarnings("unchecked")
			@Override
			public List<CorpDepartment> executeHttpResponse(HttpResponse httpResponse, Map<String, Object> map) throws WeixinException {
				List<Map<String, Object>> departmentList = (List<Map<String, Object>>) map.get("department");
				if (departmentList == null || departmentList.size() <= 0) {
					return null;
				}
				List<CorpDepartment> corpDepartments = new ArrayList<CorpDepartment>();
				for (Map<String, Object> tempMap : departmentList) {
					Number id = (Number) tempMap.get("id");
					String name = (String) tempMap.get("name");
					Number parentid = (Number) tempMap.get("parentid");
					Number order = (Number) tempMap.get("order");
					CorpDepartment corpDepartment = new CorpDepartment();
					corpDepartment.setId(id.intValue());
					corpDepartment.setName(name);
					corpDepartment.setOrder(order.intValue());
					corpDepartment.setParentid(parentid.intValue());
					corpDepartments.add(corpDepartment);
				}
				return corpDepartments;
			}
		});
	}

	@Override
	public List<CorpDepartment> getDepartments() throws WeixinException {
		return this.getDepartments(1);
	}

	@Override
	public CorpUserInfo getCorpUserInfo(String userid) throws WeixinException {
		final String accesstoken = this.getAccessToken();
		String url = Constants.getUserInfoUrl(accesstoken, userid);
		HttpGet httpGet = new HttpGet(url);
		return this.clientTemplate.execute(httpGet, new HttpClientCallback<CorpUserInfo>() {
			@SuppressWarnings("unchecked")
			@Override
			public CorpUserInfo executeHttpResponse(HttpResponse httpResponse, Map<String, Object> map) throws WeixinException {
				String userid = (String) map.get("userid");
				String name = (String) map.get("name");
				List<Number> departmentids = (List<Number>) map.get("department");
				String position = (String) map.get("position");
				String mobile = (String) map.get("mobile");
				String gender = (String) map.get("gender");
				String email = (String) map.get("email");
				String weixinid = (String) map.get("weixinid");
				String avatar = (String) map.get("avatar");
				Number status = (Number) map.get("status");
				Map<String, Object> attrsMap = (Map<String, Object>) map.get("extattr");

				CorpUserInfo userInfo = new CorpUserInfo();
				userInfo.setUserid(userid);
				userInfo.setName(name);
				if (departmentids != null && departmentids.size() > 0) {
					for (Number departmentid : departmentids) {
						userInfo.addDepartmentID(departmentid.intValue());
					}
				}
				userInfo.setPosition(position);
				userInfo.setMobile(mobile);
				if (!StringUtils.isEmpty(gender)) {
					if (gender.equals("1")) {
						userInfo.setSex(Sex.Male);
					} else if (gender.equals("2")) {
						userInfo.setSex(Sex.FMale);
					}
				}
				userInfo.setEmail(email);
				userInfo.setWeixinid(weixinid);
				userInfo.setAvatar(avatar);
				if (status != null) {
					int statusValue = status.intValue();
					switch (statusValue) {
					case 1:
						userInfo.setUserStatus(UserStatus.Subscribe);
						break;
					case 2:
						userInfo.setUserStatus(UserStatus.Forbit);
						break;
					default:
						userInfo.setUserStatus(UserStatus.Unsubscribe);
						break;
					}
				}
				if (attrsMap != null && attrsMap.size() > 0) {
					List<Map<String, Object>> attrs = (List<Map<String, Object>>) attrsMap.get("attrsMap");
					if (attrs != null && attrs.size() > 0) {
						for (Map<String, Object> temp : attrs) {
							String attrName = (String) temp.get("name");
							String attrValue = (String) temp.get("value");
							userInfo.addUserExtAttr(attrName, attrValue);
						}
					}
				}
				return userInfo;
			}
		});
	}

	@Override
	public void addCorpUserInfo(String userid, String name, Integer department, String position, String mobile, String email, String weixinid, Sex sex, String avatar_mediaid, List<UserExtAttr> extAttrs) throws WeixinException {
		final String accesstoken = this.getAccessToken();
		String url = Constants.getCreateUserUrl(accesstoken);
		HttpPost httpPost = new HttpPost(url);

		Map<String, Object> param = new HashMap<String, Object>();

		if (!StringUtils.isEmpty(userid)) {
			param.put("userid", userid);
		}
		if (!StringUtils.isEmpty(name)) {
			param.put("name", name);
		}
		if (department != null) {
			param.put("department", department);
		}
		if (!StringUtils.isEmpty(position)) {
			param.put("position", position);
		}
		if (!StringUtils.isEmpty(mobile)) {
			param.put("mobile", mobile);
		}
		if (sex != null) {
			if (sex.equals(Sex.Male)) {
				param.put("gender", 1);
			} else {
				param.put("gender", 0);
			}
		}
		if (!StringUtils.isEmpty(email)) {
			param.put("email", email);
		}
		if (!StringUtils.isEmpty(weixinid)) {
			param.put("weixinid", weixinid);
		}
		if (!StringUtils.isEmpty(avatar_mediaid)) {
			param.put("avatar_mediaid ", avatar_mediaid);
		}
		if (extAttrs != null && extAttrs.size() > 0) {
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			for (UserExtAttr attr : extAttrs) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("name", attr.getName());
				map.put("value", attr.getValue());
				list.add(map);
			}
			Map<String, Object> attrsMap = new HashMap<String, Object>();
			attrsMap.put("attrs", list);
			param.put("extattr", attrsMap);
		}

		StringEntity stringEntity = new StringEntity(JsonUtil.object2PrettyString(param, false), Constants.SYS_CHARSET_UTF8);
		httpPost.setEntity(stringEntity);
		this.clientTemplate.execute(httpPost, new HttpClientCallback<Object>() {
			@Override
			public Object executeHttpResponse(HttpResponse httpResponse, Map<String, Object> map) throws WeixinException {
				return null;
			}
		});
	}
	
	@Override
	public void updateCorpUserInfo(String userid, String name, Integer department, String position, String mobile, String email, String weixinid, Sex sex, String avatar_mediaid, List<UserExtAttr> extAttrs, Boolean enable) throws WeixinException {
		final String accesstoken = this.getAccessToken();
		String url = Constants.getUpdateUserUrl(accesstoken);
		HttpPost httpPost = new HttpPost(url);
		
		Map<String, Object> param = new HashMap<String, Object>();

		if (!StringUtils.isEmpty(userid)) {
			param.put("userid", userid);
		}
		if (!StringUtils.isEmpty(name)) {
			param.put("name", name);
		}
		if (department != null) {
			param.put("department", department);
		}
		if (!StringUtils.isEmpty(position)) {
			param.put("position", position);
		}
		if (!StringUtils.isEmpty(mobile)) {
			param.put("mobile", mobile);
		}
		if (sex != null) {
			if (sex.equals(Sex.Male)) {
				param.put("gender", 1);
			} else {
				param.put("gender", 0);
			}
		}
		if (!StringUtils.isEmpty(email)) {
			param.put("email", email);
		}
		if (!StringUtils.isEmpty(weixinid)) {
			param.put("weixinid", weixinid);
		}
		if (!StringUtils.isEmpty(avatar_mediaid)) {
			param.put("avatar_mediaid ", avatar_mediaid);
		}
		if (enable != null) {
			if (enable) {
				param.put("enable", 1);
			}else {
				param.put("enable", 0);
			}
		}
		
		if (extAttrs != null && extAttrs.size() > 0) {
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			for (UserExtAttr attr : extAttrs) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("name", attr.getName());
				map.put("value", attr.getValue());
				list.add(map);
			}
			Map<String, Object> attrsMap = new HashMap<String, Object>();
			attrsMap.put("attrs", list);
			param.put("extattr", attrsMap);
		}

		StringEntity stringEntity = new StringEntity(JsonUtil.object2PrettyString(param, false), Constants.SYS_CHARSET_UTF8);
		httpPost.setEntity(stringEntity);
		this.clientTemplate.execute(httpPost, new HttpClientCallback<Object>() {
			@Override
			public Object executeHttpResponse(HttpResponse httpResponse, Map<String, Object> map) throws WeixinException {
				return null;
			}
		});
	}
	
	@Override
	public void deleteCorpUser(String userid) throws WeixinException {
		final String accesstoken = this.getAccessToken();
		String url = Constants.getDeleteUserUrl(accesstoken, userid);
		HttpGet get = new HttpGet(url);
		this.clientTemplate.execute(get, new HttpClientCallback<Object>() {
			@Override
			public Object executeHttpResponse(HttpResponse httpResponse, Map<String, Object> map) throws WeixinException {
				return null;
			}
		});
	}

	@Override
	public void batchDeleteCorpUsers(List<String> userids) throws WeixinException {
		MyAssert.notEmpty(userids);
		final String accesstoken = this.getAccessToken();
		String url = Constants.getBatchDeleteUsersUrl(accesstoken);
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("useridlist", userids);
		StringEntity stringEntity = new StringEntity(JsonUtil.object2PrettyString(param, false), Constants.SYS_CHARSET_UTF8);
		HttpPost httpPost = new HttpPost(url);
		httpPost.setEntity(stringEntity);
		this.clientTemplate.execute(httpPost, new HttpClientCallback<Object>() {
			@Override
			public Object executeHttpResponse(HttpResponse httpResponse, Map<String, Object> map) throws WeixinException {
				return null;
			}
		});
	}

	@Override
	public List<CorpUserInfo> getSimpleListCorpUsers(int departmentid, Boolean fetchChild, boolean all, boolean subscribe, boolean forbit, boolean unsubscribe) throws WeixinException {
		final String accesstoken = this.getAccessToken();
		boolean contain_status = false;
		int status = 0;
		if (all) {
			status = status + 0;
			contain_status = true;
		}
		if (subscribe) {
			status = status + 1;
			contain_status = true;
		}
		if (forbit) {
			status = status + 2;
			contain_status = true;
		}
		if (subscribe) {
			status = status + 4;
			contain_status = true;
		}
		
		String url = Constants.getSimpleListUserUrl(accesstoken, departmentid, fetchChild, contain_status ? status : null);
		HttpGet get = new HttpGet(url);
		return this.clientTemplate.execute(get, new HttpClientCallback<List<CorpUserInfo>>() {
			@SuppressWarnings("unchecked")
			@Override
			public List<CorpUserInfo> executeHttpResponse(HttpResponse httpResponse, Map<String, Object> map) throws WeixinException {
				List<Map<String, Object>> userlist = (List<Map<String,Object>>)map.get("userlist");
				if (userlist == null || userlist.size() <=0) {
					return null;
				}
				List<CorpUserInfo> userInfos = new ArrayList<CorpUserInfo>();
				for (Map<String, Object> datamap : userlist) {
					String userid = (String)datamap.get("userid");
					String name = (String)datamap.get("name");
					List<Integer> departmentids = (List<Integer>)datamap.get("department");
					CorpUserInfo userInfo = new CorpUserInfo();
					userInfo.setUserid(userid);
					userInfo.setName(name);
					userInfo.addDepartmentIDs(departmentids);
					userInfos.add(userInfo);
				}
				return userInfos;
			}
		});
	}

	@Override
	public List<CorpUserInfo> getFullListCorpUsers(int departmentid, Boolean fetchChild, boolean all, boolean subscribe, boolean forbit, boolean unsubscribe) throws WeixinException {
		final String accesstoken = this.getAccessToken();
		boolean contain_status = false;
		int status = 0;
		if (all) {
			status = status + 0;
			contain_status = true;
		}
		if (subscribe) {
			status = status + 1;
			contain_status = true;
		}
		if (forbit) {
			status = status + 2;
			contain_status = true;
		}
		if (subscribe) {
			status = status + 4;
			contain_status = true;
		}
		
		String url = Constants.getFullListUserUrl(accesstoken, departmentid, fetchChild, contain_status ? status : null);
		HttpGet get = new HttpGet(url);
		return this.clientTemplate.execute(get, new HttpClientCallback<List<CorpUserInfo>>() {
			@SuppressWarnings("unchecked")
			@Override
			public List<CorpUserInfo> executeHttpResponse(HttpResponse httpResponse, Map<String, Object> map) throws WeixinException {
				List<Map<String, Object>> userlist = (List<Map<String,Object>>)map.get("userlist");
				if (userlist == null || userlist.size() <=0) {
					return null;
				}
				List<CorpUserInfo> userInfos = new ArrayList<CorpUserInfo>();
				for (Map<String, Object> datamap : userlist) {
					String userid = (String) datamap.get("userid");
					String name = (String) datamap.get("name");
					List<Integer> departmentids = (List<Integer>) datamap.get("department");
					String position = (String) datamap.get("position");
					String mobile = (String) datamap.get("mobile");
					String gender = (String) datamap.get("gender");
					String email = (String) datamap.get("email");
					String weixinid = (String) datamap.get("weixinid");
					String avatar = (String) datamap.get("avatar");
					Number status = (Number) datamap.get("status");
					Map<String, Object> attrsMap = (Map<String, Object>) datamap.get("extattr");
					
					CorpUserInfo userInfo = new CorpUserInfo();
					userInfo.setUserid(userid);
					userInfo.setName(name);
					if (departmentids != null && departmentids.size() > 0) {
						userInfo.addDepartmentIDs(departmentids);
					}
					userInfo.setPosition(position);
					userInfo.setMobile(mobile);
					if (!StringUtils.isEmpty(gender)) {
						if (gender.equals("1")) {
							userInfo.setSex(Sex.Male);
						} else if (gender.equals("2")) {
							userInfo.setSex(Sex.FMale);
						}
					}
					userInfo.setEmail(email);
					userInfo.setWeixinid(weixinid);
					userInfo.setAvatar(avatar);
					if (status != null) {
						int statusValue = status.intValue();
						switch (statusValue) {
						case 1:
							userInfo.setUserStatus(UserStatus.Subscribe);
							break;
						case 2:
							userInfo.setUserStatus(UserStatus.Forbit);
							break;
						default:
							userInfo.setUserStatus(UserStatus.Unsubscribe);
							break;
						}
					}
					if (attrsMap != null && attrsMap.size() > 0) {
						List<Map<String, Object>> attrs = (List<Map<String, Object>>) attrsMap.get("attrsMap");
						if (attrs != null && attrs.size() > 0) {
							for (Map<String, Object> temp : attrs) {
								String attrName = (String) temp.get("name");
								String attrValue = (String) temp.get("value");
								userInfo.addUserExtAttr(attrName, attrValue);
							}
						}
					}
					userInfos.add(userInfo);
				}
				return userInfos;
			}
		});
	}

	@Override
	public WeixinOrEmail sendInvite(String userid) throws WeixinException {
		MyAssert.hasText(userid);
		final String accesstoken = this.getAccessToken();
		String url = Constants.getSendInviteUrl(accesstoken);
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userid", userid);
		StringEntity stringEntity = new StringEntity(JsonUtil.object2PrettyString(param, false), Constants.SYS_CHARSET_UTF8);
		HttpPost httpPost = new HttpPost(url);
		httpPost.setEntity(stringEntity);
		return this.clientTemplate.execute(httpPost, new HttpClientCallback<WeixinOrEmail>() {
			@Override
			public WeixinOrEmail executeHttpResponse(HttpResponse httpResponse, Map<String, Object> map) throws WeixinException {
				Number type = (Number)map.get("type");
				if(type.intValue() == 1) {
					return WeixinOrEmail.Weixin;
				}else {
					return WeixinOrEmail.Email;
				}
			}
		});
	}

	
	
	
	
	
	
	
	
	
	
	
	
	@Override
	public int addTag(String tagname, Integer tagid) throws WeixinException {
		MyAssert.hasText(tagname);
		
		final String accesstoken = this.getAccessToken();
		String url = Constants.getCorpCreateTagUrl(accesstoken);
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("tagname", tagname);
		if (tagid != null) {
			param.put("tagid", tagid);
		}
		StringEntity stringEntity = new StringEntity(JsonUtil.object2PrettyString(param, false), Constants.SYS_CHARSET_UTF8);
		HttpPost httpPost = new HttpPost(url);
		httpPost.setEntity(stringEntity);
		return this.clientTemplate.execute(httpPost, new HttpClientCallback<Integer>() {
			@Override
			public Integer executeHttpResponse(HttpResponse httpResponse, Map<String, Object> map) throws WeixinException {
				Number tagid = (Number)map.get("tagid");
				return tagid.intValue();
			}
		});
	}
	
	@Override
	public void updateTag(int tagid, String tagname) throws WeixinException {
		MyAssert.hasText(tagname);
		
		final String accesstoken = this.getAccessToken();
		String url = Constants.getUpdateTagUrl(accesstoken);
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("tagname", tagname);
		param.put("tagid", tagid);
		
		StringEntity stringEntity = new StringEntity(JsonUtil.object2PrettyString(param, false), Constants.SYS_CHARSET_UTF8);
		HttpPost httpPost = new HttpPost(url);
		httpPost.setEntity(stringEntity);
		
		this.clientTemplate.execute(httpPost, new HttpClientCallback<Object>() {
			@Override
			public Integer executeHttpResponse(HttpResponse httpResponse, Map<String, Object> map) throws WeixinException {
				return null;
			}
		});
	}
	
	@Override
	public void deleteTag(int tagid) throws WeixinException {
		final String accesstoken = this.getAccessToken();
		String url = Constants.getDeleteTagUrl(accesstoken, tagid);
		HttpGet httpGet = new HttpGet(url);
		this.clientTemplate.execute(httpGet, new HttpClientCallback<Object>() {
			@Override
			public Object executeHttpResponse(HttpResponse httpResponse, Map<String, Object> map) throws WeixinException {
				return null;
			}
		});
	}
	
	@Override
	public UserCollectionWithTag getUserCollectionWithTag(final int tagid) throws WeixinException {
		final String accesstoken = this.getAccessToken();
		String url = Constants.getTagGetUsersUrl(accesstoken, tagid);
		HttpGet httpGet = new HttpGet(url);
		return this.clientTemplate.execute(httpGet, new HttpClientCallback<UserCollectionWithTag>() {
			@SuppressWarnings("unchecked")
			@Override
			public UserCollectionWithTag executeHttpResponse(HttpResponse httpResponse, Map<String, Object> map) throws WeixinException {
				List<Map<String, Object>> userlist = (List<Map<String,Object>>)map.get("userlist");
				List<Integer> partylist = (List<Integer>)map.get("partylist");
				UserCollectionWithTag userCollectionWithTag = new UserCollectionWithTag();
				userCollectionWithTag.setTagid(tagid);
				if (userlist != null && userlist.size() >0) {
					for (Map<String, Object> usermap : userlist) {
						String userid = (String)usermap.get("userid");
						String name = (String)usermap.get("name");
						CorpUserInfo userInfo = new CorpUserInfo();
						userInfo.setUserid(userid);
						userInfo.setName(name);
						userCollectionWithTag.addCorpUserInfo(userInfo);
					}
				}
				
				if (partylist != null && partylist.size() >0) {
					userCollectionWithTag.addPartys(partylist);
				}
				return userCollectionWithTag;
			}
		});
	}
	
	@Override
	public CorpMsgResult addTagRelUsers(int tagid, List<String> userids, List<Integer> partylist) throws WeixinException {
		if ((userids == null || userids.size() <=0) && (partylist == null || partylist.size() <= 0)) {
			throw new IllegalArgumentException();
		}
		final String accesstoken = this.getAccessToken();
		String url = Constants.getAddTagUsersUrl(accesstoken);
		/*
		 * {
			   "tagid": "1",
			   "userlist":[ "user1","user2"],
			   "partylist": [4]
			}
		 */
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("tagid", tagid);
		if (userids != null && userids.size() >0) {
			param.put("userlist", userids);
		}
		if (partylist != null && partylist.size() >0) {
			param.put("partylist", partylist);
		}
		StringEntity stringEntity = new StringEntity(JsonUtil.object2PrettyString(param, false), Constants.SYS_CHARSET_UTF8);
		HttpPost httpPost = new HttpPost(url);
		httpPost.setEntity(stringEntity);
		
		return this.clientTemplate.execute(httpPost, new HttpClientCallback<CorpMsgResult>() {
			@Override
			public CorpMsgResult executeHttpResponse(HttpResponse httpResponse, Map<String, Object> map) throws WeixinException {
				int errcode = ((Number) map.get("errcode")).intValue();
				String errmsg = (String) map.get("errmsg");
				String invaliduser = (String) map.get("invaliduser");
				String invalidparty = (String) map.get("invalidparty");
				String invalidtag = (String) map.get("invalidtag");
				ErrorCode errorCode = ErrorCode.parseErrorCode(errcode);
				return new CorpMsgResult(errorCode, errmsg, invaliduser, invalidparty, invalidtag);
			}
		});
	}
	
	@Override
	public CorpMsgResult deleteTagRelUsers(int tagid, List<String> userids, List<Integer> partylist) throws WeixinException {
		if ((userids == null || userids.size() <=0) && (partylist == null || partylist.size() <= 0)) {
			throw new IllegalArgumentException();
		}
		final String accesstoken = this.getAccessToken();
		String url = Constants.getDeleteTagUserUrl(accesstoken);
		/*
		 * {
			   "tagid": "1",
			   "userlist":[ "user1","user2"],
			   "partylist": [4]
			}
		 */
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("tagid", tagid);
		if (userids != null && userids.size() >0) {
			param.put("userlist", userids);
		}
		if (partylist != null && partylist.size() >0) {
			param.put("partylist", partylist);
		}
		StringEntity stringEntity = new StringEntity(JsonUtil.object2PrettyString(param, false), Constants.SYS_CHARSET_UTF8);
		HttpPost httpPost = new HttpPost(url);
		httpPost.setEntity(stringEntity);
		
		return this.clientTemplate.execute(httpPost, new HttpClientCallback<CorpMsgResult>() {
			@Override
			public CorpMsgResult executeHttpResponse(HttpResponse httpResponse, Map<String, Object> map) throws WeixinException {
				int errcode = ((Number) map.get("errcode")).intValue();
				String errmsg = (String) map.get("errmsg");
				String invaliduser = (String) map.get("invaliduser");
				String invalidparty = (String) map.get("invalidparty");
				String invalidtag = (String) map.get("invalidtag");
				ErrorCode errorCode = ErrorCode.parseErrorCode(errcode);
				return new CorpMsgResult(errorCode, errmsg, invaliduser, invalidparty, invalidtag);
			}
		});
	}
	
	@Override
	public List<Tag> getTags() throws WeixinException {
		final String accesstoken = this.getAccessToken();
		String url = Constants.getGetTagListUrl(accesstoken);
		HttpGet httpGet = new HttpGet(url);
		this.clientTemplate.execute(httpGet, new HttpClientCallback<Object>() {
			@SuppressWarnings("unchecked")
			@Override
			public Object executeHttpResponse(HttpResponse httpResponse, Map<String, Object> map) throws WeixinException {
				List<Map<String, Object>> taglist = (List<Map<String,Object>>)map.get("taglist");
				if (taglist == null || taglist.size() <=0) {
					return null;
				}
				List<Tag> tags = new ArrayList<Tag>();
				for (Map<String, Object> tmp : taglist) {
					Number tagid = (Number)tmp.get("tagid");
					String tagname = (String)tmp.get("tagname");
					Tag tag = new Tag();
					tag.setTagname(tagname);
					tag.setId(tagid.intValue());
					tags.add(tag);
				}
				return tags;
			}
		});
		return null;
	}

	@Override
	public TicketObject initialJSAPISignature(HttpServletRequest servletRequest) throws WeixinException {
		MyAssert.notNull(servletRequest);
		final String accesstoken = this.getAccessToken();
		String url = Constants.getJSAPITicketURL(accesstoken);
		HttpGet httpGet = new HttpGet(url);
		String ticket = this.clientTemplate.execute(httpGet, new HttpClientCallback<String>() {
			@Override
			public String executeHttpResponse(HttpResponse httpResponse, Map<String, Object> map) throws WeixinException {
				String ticket = (String)map.get("ticket");
				return ticket;
			}
		});
		
		String requestURL = servletRequest.getRequestURL().toString();
		String query=servletRequest.getQueryString();
		if(!StringUtils.isEmpty(query)){
			requestURL+="?"+query;
		}
		long timestamp = UnixJavaDateTimeUtil.javaDateTimeToUnixTimestamp(new Date());
		StringBuffer sb = new StringBuffer();
		sb.append("jsapi_ticket=").append(ticket).append("&");
		sb.append("noncestr=").append(CorpClientConf.getInstance().getCorp().getJsAPINoncestr()).append("&");
		sb.append("timestamp=").append(timestamp).append("&");
		sb.append("url=").append(requestURL);
		TicketObject object = new TicketObject();
		object.setSignature(DigestUtils.shaHex(sb.toString()));
		object.setTicket(ticket);
		object.setUrl(requestURL);
		object.setNoncestr(CorpClientConf.getInstance().getCorp().getJsAPINoncestr());
		object.setTimestamp(timestamp);
		return object;
	}
	
}
