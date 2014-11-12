/**
 * 
 */
package com.vanstone.centralserver.common;

import java.nio.charset.Charset;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import com.vanstone.centralserver.common.conf.VanstoneConf;
import com.vanstone.centralserver.common.configuration.GroupIdDataIdObject;

/**
 * Weixin 全局常量
 * @author shipeng
 */
public abstract class Constants {
	
	/**管理员默认名称*/
	public static final String DEFAULT_ADMIN_NAME = "root";
	
	/**管理员默认密码*/
	public static final String DEFAULT_ADMIN_PWD = "root";
	
	/**管理员在httpsession中的名称*/
	public static final String ADMIN_IN_SESSION_NAME = "centralserver.admin.sessionname";
	
	/**默认Container*/
	public static final String DWZ_DEFAULT_CONTAINER_ID = "container";
	
	/**http网页协议前缀*/
	public static final String HTTP_PROTOCOLS_URL_PREFIX = "http://";
	
	/**微信AccessToken z节点*/
	public static final String VANSTONE_WEIXIN_TOKEN_NODE_PREFIX = "/vanstone/app/weixin/token";
	
	/**微信AccessToken终端节点*/
	public static final String VANSTONE_WEIXIN_TOKEN_NODE_SUFFIX = "/accesstoken";
	
	/**微信leader z节点*/
	public static final String VANSTONE_WEIXIN_LEADER_NODE_PREFIX = "/vanstone/app/weixin/leader";
	
	/**服务器刷新Token使用的线程池容量*/
	public static final int SERVER_FLUSH_EXECUTOR_SERVICE_SIZE = 100;
	
	/**服务器刷新Token分页显示的页面大小*/
	public static final int SERVER_FLUSH_PAGE_SIZE = 10;
	
	/**leader访问周期,DEFAULT = 7000*/
	public static final int ACCESS_TOKEN_SCHEDULE_PERIOD = 7000;
	
	/**客户端定时更新,默认10分钟*/
	public static final int CLIENT_SCHEDULER_PERIOD_IN_SENCOND = 60*10;
	
	/**最大ArticleItem数量*/
	public static final int MAX_ARTICLE_ITEM_NUM = 10;
	
	/**最大父菜单项数量*/
	public static final int MAX_MENUITEM_NUM = 3;
	
	/**最大菜单项数量*/
	public static final int MAX_SUB_MENUITEM_NUM = 5;
	
	/**默认重试数量 */
	public static final int DEFAULT_RETRY_REQUEST_NUM = 3;
	
	/**默认线程停顿时间,单位秒*/
	public static final int DEFAULT_RETRY_SLEEP_TIME = 1;
	
	/**Weixin Server Admin Weixin Size*/
	public static final int DEFAULT_ADMIN_PAGESIZE = 20;
	
	/**1M大小*/
	public static final long MAX_IMAGE_FILE_SIZE = 1024*1024;;
	
	/**2M大小*/
	public static final long MAX_VOICE_FILE_SIZE = 1024*1024*2;
	
	/**10M大小*/
	public static final long MAX_VIDEO_FILE_SIZE = 1024*1024*10;
	
	/**64K大小*/
	public static final long MAX_THUMB_FILE_SIZE = 1024*64;
	
	/**获取USER TOKEN 微信 URL*/
	public static final String USER_TOKEN_URL_TEMPLATE  = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=#APPID#&secret=#APPSECRET#";
	
	/**创建菜单URL*/
	public static final String CREATE_MENU_URL_TEMPLATE = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=#ACCESS_TOKEN#";
	
	/**被动客服消息URL*/
	public static final String CC_MSG_URL_TEMPLATE = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=#ACCESS_TOKEN#";
	
	/**上传Media附件URL*/
	public static final String UPLOAD_MEDIA_URL_TEMPLATE = "http://file.api.weixin.qq.com/cgi-bin/media/upload?access_token=#ACCESS_TOKEN#&type=#TYPE#";
	
	/**下载Media附件URL*/
	public static final String DOWNLOAD_MEDIA_URL_TEMPLATE = "http://file.api.weixin.qq.com/cgi-bin/media/get?access_token=#ACCESS_TOKEN#&media_id=#MEDIA_ID#";
	
	/**获取微信用户基本信息URL*/
	public static final String RETRIEVAL_WEIXIN_USER_INFO_URL_TEMPLATE = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=#ACCESS_TOKEN#&openid=#OPENID#&lang=#LANGUAGE#";
	
	/**创建用户分组*/
	public static final String RETRIEVAL_CREATE_WEIXIN_GROUP_URL_TEMPLATE = "https://api.weixin.qq.com/cgi-bin/groups/create?access_token=#ACCESS_TOKEN#";
	
	/**获取全部用户分组*/
	public static final String RETRIEVAL_ALL_WEIXIN_GROUPS_URL_TEMPLATE = "https://api.weixin.qq.com/cgi-bin/groups/get?access_token=#ACCESS_TOKEN#";
	
	/**更新用户分组URL*/
	public static final String RETRIEVAL_UPDATE_GROUP_NAME_URL_TEMPLATE = "https://api.weixin.qq.com/cgi-bin/groups/update?access_token=#ACCESS_TOKEN#";
	
	/**获取用户所在分组*/
	public static final String RETRIEVAL_GETID_GROUP_URL_TEMPLATE = "https://api.weixin.qq.com/cgi-bin/groups/getid?access_token=#ACCESS_TOKEN#";
	
	/**创建QC TicketURL*/
	public static final String CREATE_QC_TICKET_URL_TEMPLATE = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=#ACCESS_TOKEN#";
	
	/**获取QC Image URL*/
	public static final String RETRIEVAL_QC_IMAGE_URL_TEMPLATE = "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=#TICKET#";
	
	/**获取用户OpenId列表URL*/
	public static final String RETRIEVAL_OPENIDS_URL_TEMPLATE = "https://api.weixin.qq.com/cgi-bin/user/get?access_token=#ACCESS_TOKEN#&next_openid=#NEXT_OPENID#";
	
	/**上传图文消息素材*/
	public static final String UPLOAD_NEWS_TEMPLATE = "https://api.weixin.qq.com/cgi-bin/media/uploadnews?access_token=#ACCESS_TOKEN#";
	
	/**上传视频资源URL模板 */
	public static final String UPLOAD_VIDEO_TEMPLATE = "https://file.api.weixin.qq.com/cgi-bin/media/uploadvideo?access_token=#ACCESS_TOKEN#";
	
	/**删除Mass消息URL模板*/
	public static final String DELETE_MASS_MSG_TEMPLATE = "https://api.weixin.qq.com/cgi-bin/message/mass/delete?access_token=#ACCESS_TOKEN#";
	
	/**通过分组群发URL模板*/
	public static final String SEND_MASS_MSG_BY_GROUPID_TEMPLATE = "https://api.weixin.qq.com/cgi-bin/message/mass/sendall?access_token=#ACCESS_TOKEN#";
	
	/**通过OpenIds群发URL模板*/
	public static final String SEND_MASS_MSG_BY_OPENIDS_TEMPLATE = "https://api.weixin.qq.com/cgi-bin/message/mass/send?access_token=#ACCESS_TOKEN#";
	
	/** OAuth2 默认Response Type 值*/
	public static final String OAUTH2_RESPONSE_TYPE = "code";
	
	/** OAUTH2回调地址模板*/
	public static final String OAUTH2_CALLBACK_URL_TEMPLATE = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=#APPID#&redirect_uri=#REDIRECT_URI#&response_type=code&scope=#SCOPE#&state=#STATE##wechat_redirect";
	
	/** OAUTH2的AccessToken 地址模板*/
	public static final String OAUTH2_ACCESSTOKEN_URL_TEMPLATE = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=#APPID#&secret=#SECRET#&code=#CODE#&grant_type=authorization_code";
	
	/** OAUTH2的刷新AccessToken地址模板*/
	public static final String OAUTH2_REFRESH_ACCESSTOKEN_URL_TEMPLATE = "https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=#APPID#&grant_type=refresh_token&refresh_token=#REFRESH_TOKEN#";
	
	/** 获取OAUTH2获取的UserInfo地址信息模板*/
	public static final String OAUTH2_USERINFO_URL_TEMPLATE = "https://api.weixin.qq.com/sns/userinfo?access_token=#ACCESS_TOKEN#&openid=#OPENID#&lang=#zh_CN#";
	
	/** 获取OAUTH2验证AccessToken*/
	public static final String OAUTH2_VALIDATE_ACCESSTOKEN = "https://api.weixin.qq.com/sns/auth?access_token=#ACCESS_TOKEN#&openid=#OPENID#";
	
	/**发送Template消息地址模板*/
	public static final String SEND_TEMPLATE_MSG_TEMPLATE = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=#ACCESS_TOKEN#";
	
	
	
	/** UTF-8文本标示*/
	public static final String SYS_CHARSET_UTF8_STRING = "UTF-8";
	
	/**默认UTF-8编码*/
	public static final Charset SYS_CHARSET_UTF8 = Charset.forName(SYS_CHARSET_UTF8_STRING);
	
	/**节点模式*/
	private static final Pattern NODE_PATTERN = Pattern.compile(VANSTONE_WEIXIN_TOKEN_NODE_PREFIX + "/" + "(.+)" + VANSTONE_WEIXIN_TOKEN_NODE_SUFFIX);
	
	/**配置管理ZK节点前缀*/
	public static final String NODE_CONFIGURATION_PREFIX = "/vanstone/app/configuration";
	
	/**配置管理ZK节点后缀*/
	public static final String NODE_CONFIGURATION_SUFFIX = "value";
	
	/**配置节点模式*/
	private static final Pattern NODE_CONFIGURATION_PATTERN = Pattern.compile(NODE_CONFIGURATION_PREFIX + "/" + "(.+)/(.+)" + "/" + NODE_CONFIGURATION_SUFFIX);
	
	/** 默认Configuration分组*/
	public static final String DEFAULT_GROUP_NAME = "DEFAULT_GROUP";
	
	/**更新Conf Value URL 模板*/
	public static final String WEBSERVICE_UPDATE_CONF_TEMPLATE = "/service/conf/update";
	
	/**添加Conf Value URL 模板*/
	public static final String WEBSERVICE_ADD_CONF_TEMPLATE = "/service/conf/add";
	
	
	/**默认微信模板消息颜色值*/
	public static final String WEIXIN_TEMPLATE_MSG_DEFAULT_COLOR = "#1E2633";
	
	/**
	 * 获取Webservice添加地址
	 * @return
	 */
	public static String getWebserviceAddConf() {
		return VanstoneConf.getInstance().getCentralServer() + WEBSERVICE_ADD_CONF_TEMPLATE;
	}
	
	/**
	 * 获取Webservice更新地址
	 * @param groupId
	 * @param dataId
	 * @return
	 */
	public static String getWebserviceUpdateConf(String groupId, String dataId) {
		return VanstoneConf.getInstance().getCentralServer() + WEBSERVICE_UPDATE_CONF_TEMPLATE  + "/" + groupId + "/" + dataId;
	}
	
	public static GroupIdDataIdObject getGroupIdDataIdObjectByNode(String node) {
		MyAssert.hasText(node);
		Matcher matcher = NODE_CONFIGURATION_PATTERN.matcher(node);
		if (!matcher.matches()) {
			return null;
		}
		GroupIdDataIdObject object = new GroupIdDataIdObject();
		object.setGroupId(matcher.group(1));
		object.setDataId(matcher.group(2));
		return object;
	}
	
	/**
	 * 获取Configuration监控节点
	 * @param groupId
	 * @param dataId
	 * @return
	 */
	public static String getConfigurationMonitorNode(String groupId, String dataId) {
		MyAssert.hasText(groupId);
		MyAssert.hasText(dataId);
		return NODE_CONFIGURATION_PREFIX + "/" + groupId + "/" + dataId ;
	}
	
	/**
	 * 获取配置管理的节点路径
	 * @param groupId
	 * @param dataId
	 * @return
	 */
	public static String getConfigurationNode(String groupId, String dataId) {
		MyAssert.hasText(groupId);
		MyAssert.hasText(dataId);
		return NODE_CONFIGURATION_PREFIX + "/" + groupId + "/" + dataId + "/" + NODE_CONFIGURATION_SUFFIX;
	}
	
	/**
	 * 获取配置管理的节点路径
	 * @param groupId
	 * @return
	 */
	public static String getConfigurationNode(String groupId) {
		MyAssert.hasText(groupId);
		return NODE_CONFIGURATION_PREFIX + "/" + groupId;
	}
	
	/**
	 * 通过Node获取Appname
	 * @param node
	 * @return
	 */
	public static String getAppnameByNode(String node) {
		MyAssert.hasText(node);
		Matcher matcher = NODE_PATTERN.matcher(node);
		if (matcher.matches()) {
			return matcher.group(1);
		}
		return null;
	}
	
	/**
	 * 获取客户端监控路径: 
	 * /vanstone/app/weixin/token/quidos
	 * /vanstone/app/weixin/token/ecointel
	 * @param appname
	 * @return
	 */
	public static String getClientMonitorPath(String appname) {
		MyAssert.hasText(appname);
		return VANSTONE_WEIXIN_TOKEN_NODE_PREFIX + "/" + appname;
	}
	
	/**
	 * 获取Appname配置项节点值
	 * /vanstone/app/weixin/token/ecointel/accesstoken
	 * @param appname
	 * @return
	 */
	public static String getAppnameNode(String appname) {
		MyAssert.hasText(appname);
		return VANSTONE_WEIXIN_TOKEN_NODE_PREFIX + "/" + appname + VANSTONE_WEIXIN_TOKEN_NODE_SUFFIX;
	}
	
	
	/**=====================================================================================================================
	 * 
	 * 以下为腾讯 Weixin API 接口
	 * 
	 **=====================================================================================================================
	 */
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * 获取发送模板消息URL
	 * @param accessToken
	 * @return
	 */
	public static String getSendTemplateMSGURL(String accessToken) {
		MyAssert.hasText(accessToken);
		String url = StringUtils.replace(SEND_TEMPLATE_MSG_TEMPLATE, "#ACCESS_TOKEN#", accessToken);
		return url;
	}
	
	/**
	 * 获取OAuth2 验证AccessToken以及OpenID 验证URL
	 * @param oauth2AccessToken
	 * @param openId
	 * @return
	 */
	public static String getOAuth2ValidateAccessTokenURL(String oauth2AccessToken, String openId) {
		MyAssert.hasText(oauth2AccessToken);
		MyAssert.hasText(openId);
		String url = StringUtils.replace(OAUTH2_VALIDATE_ACCESSTOKEN, "#ACCESS_TOKEN#", oauth2AccessToken);
		url = StringUtils.replace(url, "#OPENID#", openId);
		return url;
	}
	
	/**
	 * 获取OAuth2 UserInfo 地址
	 * @return
	 */
	public static String getOAuth2UserInfoURL(String oauth2AccessToken, String openId, String language) {
		MyAssert.hasText(oauth2AccessToken);
		MyAssert.hasText(openId);
		MyAssert.hasText(language);
		String url = StringUtils.replace(OAUTH2_USERINFO_URL_TEMPLATE, "#ACCESS_TOKEN#", oauth2AccessToken);
		url = StringUtils.replace(url, "#OPENID#", openId);
		url = StringUtils.replace(url, "#zh_CN#", language);
		return url;
	}
	
	/**
	 * 获取OAuth2刷新AccessToken URL地址
	 * @param appid
	 * @param refreshToken
	 * @return
	 */
	public static String getOAuth2RefreshAccessTokenURL(String appid, String refreshToken) {
		MyAssert.hasText(appid);
		MyAssert.hasText(refreshToken);
		String url = StringUtils.replace(OAUTH2_REFRESH_ACCESSTOKEN_URL_TEMPLATE, "#APPID#", appid);
		url = StringUtils.replace(url, "#REFRESH_TOKEN#", refreshToken);
		return url;
	}
	
	/**
	 * 获取OAuth2 Access Token URL
	 * @param appid
	 * @param secret
	 * @param code
	 * @return
	 */
	public static String getOAuth2AccessTokenURL(String appid, String secret, String code) {
		String url = StringUtils.replace(OAUTH2_ACCESSTOKEN_URL_TEMPLATE, "#APPID#", appid);
		url = StringUtils.replace(url, "#SECRET#", secret);
		url = StringUtils.replace(url, "#CODE#", code);
		return url;
	}
	
	/**
	 * 获取OAuth2回调地址
	 * @param appid
	 * @param redirect
	 * @param scope
	 * @param state
	 * @return
	 */
	public static String getOAuth2CallbackAddress(String appid, String redirect, String scope, String state) {
		String callbackUrl = StringUtils.replace(OAUTH2_CALLBACK_URL_TEMPLATE, "#APPID#", appid);
		callbackUrl = StringUtils.replace(callbackUrl, "#REDIRECT_URI#", redirect);
		callbackUrl = StringUtils.replace(callbackUrl, "#SCOPE#", scope);
		callbackUrl = StringUtils.replace(callbackUrl, "#STATE#", state == null || state.equals("") ? "" : state);
		return callbackUrl;
	}
	
	/**
	 * 获取发送Mass消息 -OpenIds方式URL地址
	 * @param token
	 * @return
	 */
	public static String getSendMassMsgByOpenIds(String token) {
		return SEND_MASS_MSG_BY_OPENIDS_TEMPLATE.replaceAll("#ACCESS_TOKEN#", token);
	}
	
	/**
	 * 获取发送Mass消息-Group方式URL地址
	 * @param token
	 * @return
	 */
	public static String getSendMassMsgByGroupId(String token) {
		return SEND_MASS_MSG_BY_GROUPID_TEMPLATE.replaceAll("#ACCESS_TOKEN#", token);
	}
	
	/**
	 * 获取删除Mass消息URL地址
	 * @param token
	 * @return
	 */
	public static String getDeleteMassMsgURL(String token) {
		return DELETE_MASS_MSG_TEMPLATE.replaceAll("#ACCESS_TOKEN#", token);
	}
	
	/**
	 * 获取上传视频URL地址
	 * @param token
	 * @return
	 */
	public static String getUploadVideoURL(String token) {
		return UPLOAD_VIDEO_TEMPLATE.replaceAll("#ACCESS_TOKEN#", token);
	}
	
	/**
	 * 获取上传图文资源URL
	 * @param token
	 * @return
	 */
	public static String getUploadNewsURL(String token) {
		return UPLOAD_NEWS_TEMPLATE.replaceAll("#ACCESS_TOKEN#", token);
	}
	
	/**
	 * 获取用户所在分组URL
	 * @param token
	 * @return
	 */
	public static String getUserGroupIdURL(String token) {
		return RETRIEVAL_GETID_GROUP_URL_TEMPLATE.replaceAll("#ACCESS_TOKEN#", token);
	}
	
	/**
	 * 获取更新用户分组URL
	 * @param token
	 * @return
	 */
	public static String getUpdateUserGroupURL(String token) {
		return RETRIEVAL_UPDATE_GROUP_NAME_URL_TEMPLATE.replaceAll("#ACCESS_TOKEN#", token);
	}
	
	/**
	 * 获取全部用户分组
	 * @param token
	 * @return
	 */
	public static String getAllUserGroupsURL(String token) {
		return RETRIEVAL_ALL_WEIXIN_GROUPS_URL_TEMPLATE.replaceAll("#ACCESS_TOKEN#", token);
	}
	
	/**
	 * 获取创建用户分组URL
	 * @param token
	 * @return
	 */
	public static String getCreateUserGroupURL(String token) {
		return RETRIEVAL_CREATE_WEIXIN_GROUP_URL_TEMPLATE.replaceAll("#ACCESS_TOKEN#", token);
	}
	
	/**
	 * 获取用户OpenId列表地址
	 * @param token
	 * @param nextOpenId
	 * @return
	 */
	public static String getRetrievalUserOpenIdsURL(String token,String nextOpenId) {
		if (token == null || "".equals(token)) {
			throw new IllegalArgumentException();
		}
		String url = RETRIEVAL_OPENIDS_URL_TEMPLATE.replaceAll("#ACCESS_TOKEN#", token);
		if (nextOpenId != null && !nextOpenId.equals("")) {
			url = url.replaceAll("#NEXT_OPENID#", nextOpenId);
		}else{
			url = url.replaceAll("#NEXT_OPENID#", "");
		}
		return url;
	}
	
	/**
	 * 获取二维码Ticket地址
	 * @param token
	 * @return
	 */
	public static String getCreateQCTicketURL(String token) {
		if (token == null || "".equals(token)) {
			throw new IllegalArgumentException();
		}
		return CREATE_QC_TICKET_URL_TEMPLATE.replaceAll("#ACCESS_TOKEN#", token);
	}
	
	/**
	 * 获取二维码票据图片地址信息
	 * @param ticket
	 * @return
	 */
	public static String getRetrievalQCImageURL(String ticket) {
		if (ticket == null || "".equals(ticket)) {
			throw new IllegalArgumentException();
		}
		return RETRIEVAL_QC_IMAGE_URL_TEMPLATE.replaceAll("#TICKET#", ticket);
	}
	
	/**
	 * 获取上传Media URL
	 * @param token
	 * @param type
	 * @return
	 */
	public static String getUploadMediaURL(String token,String type) {
		String url = UPLOAD_MEDIA_URL_TEMPLATE.replaceAll("#ACCESS_TOKEN#", token);
		return url.replaceAll("#TYPE#", type);
	}
	
	/**
	 * 获取下载Media URL
	 * @param token
	 * @param mediaId
	 * @return
	 */
	public static String getDownloadMediaURL(String token,String mediaId) {
		String url = DOWNLOAD_MEDIA_URL_TEMPLATE.replaceAll("#ACCESS_TOKEN#", token);
		return url.replaceAll("#MEDIA_ID#", mediaId);
	}
	
	/**
	 * 获取UserToken URL
	 * @param appId
	 * @param appSecret
	 * @return
	 */
	public static String getUserTokenURL(String appId,String appSecret) {
		if (appId == null || "".equals(appId) || appSecret == null || "".equals(appSecret)) {
			throw new IllegalArgumentException();
		}
		String url = USER_TOKEN_URL_TEMPLATE.replaceAll("#APPID#", appId);
		url = url.replaceAll("#APPSECRET#", appSecret);
		return url;
	}
	
	/**
	 * 获取创建菜单URL
	 * @param token
	 * @return
	 */
	public static String getCreateMenuURL(String token) {
		if (token == null || "".equals(token)) {
			throw new IllegalArgumentException();
		}
		return CREATE_MENU_URL_TEMPLATE.replaceAll("#ACCESS_TOKEN#", token);
	}
	
	/**
	 * 获取微信客户消息URL
	 * @param token
	 * @return
	 */
	public static String getCCMsgUrl(String token) {
		if (token == null || "".equals(token)) {
			throw new IllegalArgumentException();
		}
		return CC_MSG_URL_TEMPLATE.replaceAll("#ACCESS_TOKEN#", token);
	}
	
	/**
	 * 获取微信用户信息URL
	 * @param token
	 * @param openid
	 * @param language
	 * @return
	 */
	public static String getRetrievalWeixinUserInfoURL(String token,String openid,String language) {
		if (token == null || "".equals(token) || openid == null || "".equals(openid) || language == null || "".equals(openid)) {
			throw new IllegalArgumentException();
		}
		String url = RETRIEVAL_WEIXIN_USER_INFO_URL_TEMPLATE.replaceAll("#ACCESS_TOKEN#", token);
		url = url.replaceAll("#OPENID#", openid);
		return url.replaceAll("#LANGUAGE#", language);
	}
}
