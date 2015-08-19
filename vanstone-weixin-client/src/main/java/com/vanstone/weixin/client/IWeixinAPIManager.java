/**
 * 
 */
package com.vanstone.weixin.client;

import java.io.File;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.vanstone.centralserver.common.weixin.WeixinException;
import com.vanstone.centralserver.common.weixin.wrap.Language;
import com.vanstone.centralserver.common.weixin.wrap.MediaType;
import com.vanstone.centralserver.common.weixin.wrap.menu.Menu;
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

/**
 * Weixin API管理
 * @author shipeng
 */
public interface IWeixinAPIManager {
	
	/**
	 * 添加Appname
	 * @param appname
	 */
	void addAppname(String appname);
	
	/**
	 * 获取Appnames
	 * @return
	 */
	Collection<String> getAppnames();
	
	/**
	 * 删除Appname
	 * @param appname
	 */
	void removeAppname(String appname);
	
	/**
	 * 清理Local Appnames
	 */
	void clearAppnames();
	
	/**
	 * 验证微信合法性.
	 * 验证echostr,并输出
	 * @param servletRequest
	 * @param servletResponse
	 * @return
	 */
	boolean validateWeixin(HttpServletRequest servletRequest, HttpServletResponse servletResponse);
	
	/**
	 * 创建用户组信息
	 * @param appname 应用名称
	 * @param name 组名称
	 * @return
	 */
	UserGroupInfo createUserGroupInfo(String appname,String name) throws WeixinException;
	
	/**
	 * 获取用户分组信息
	 * @param appname
	 * @return
	 */
	Collection<UserGroupInfo> getUserGroupInfos(String appname) throws WeixinException;
	
	/**
	 * 获取用户组信息
	 * @param appname
	 * @param groupid
	 * @param newName
	 */
	void updateUserGroupInfo(String appname, Integer groupid,String newName) throws WeixinException;
	
	/**
	 * 获取用户组信息
	 * @param appname
	 * @param openId
	 * @return
	 */
	Integer getUserGroupInfo(String appname,String openId) throws WeixinException;
	
	/**
	 * 获取用户信息
	 * @param appname
	 * @param openId
	 * @param language
	 * @return
	 */
	UserWeixinBaseInfo getUserWeixinBaseInfo(String appname,String openId, Language language) throws WeixinException;
	
	/**
	 * 获取UserOpenId集合
	 * @param nextUserOpenId
	 * @return
	 * @throws WeixinException
	 */
	UserOpenIdCollection getUserOpenIdCollection(String appname , String nextUserOpenId) throws WeixinException;
	
	/**
	 * 创建微信菜单
	 * @return
	 */
	void createWeixinMenu(String appname,  Menu menu) throws WeixinException;
	
	/**
	 * 发送文本消息
	 * @param appname
	 * @param ccMsg4Text
	 * @throws WeixinException
	 */
	void sendCCMsgText(String appname, CCMsg4Text ccMsg4Text) throws WeixinException;
	
	/**
	 * 发送图片消息
	 * @param appname
	 * @param ccMsg4Image
	 * @throws WeixinException
	 */
	void sendCCMsgImage(String appname, CCMsg4Image ccMsg4Image) throws WeixinException;
	
	/**
	 * 发送语音消息
	 * @param appname
	 * @param ccMsg4Voice
	 * @throws WeixinException
	 */
	void sendCCMsgVoice(String appname, CCMsg4Voice ccMsg4Voice) throws WeixinException;
	
	/**
	 * 发送视频消息
	 * @param appname
	 * @param ccMsg4Video
	 * @throws WeixinException
	 */
	void sendCCMsgVideo(String appname, CCMsg4Video ccMsg4Video) throws WeixinException;
	
	/**
	 * 发送音乐消息
	 * @param appname
	 * @param ccMsg4Music
	 * @throws WeixinException
	 */
	void sendCCMsgMusic(String appname, CCMsg4Music ccMsg4Music) throws WeixinException;
	
	/**
	 * 发送图文消息
	 * @param appname
	 * @param ccMsg4News
	 * @throws WeixinException
	 */
	void sendCCMsgNews(String appname, CCMsg4News ccMsg4News) throws WeixinException;
	
	/**
	 *  被动回复图片消息
	 * @param replyMsg4Image
	 * @param servletResponse
	 */
	void sendReplyMsg4Image(ReplyMsg4Image replyMsg4Image, HttpServletResponse servletResponse);
	
	/**
	 * 被动回复音乐消息
	 * @param replyMsg4Music
	 * @param servletResponse
	 */
	void sendReplyMsg4Music(ReplyMsg4Music replyMsg4Music, HttpServletResponse servletResponse);
	
	/**
	 * 被动回复新闻消息
	 * @param replyMsg4News
	 * @param servletResponse
	 */
	void sendReplyMsg4News(ReplyMsg4News replyMsg4News, HttpServletResponse servletResponse);
	
	/**
	 * 被动回复文本消息
	 * @param replyMsg4Text
	 * @param servletResponse
	 */
	void sendReplyMsg4Text(ReplyMsg4Text replyMsg4Text, HttpServletResponse servletResponse);
	
	/**
	 * 被动回复视频消息
	 * @param replyMsg4Video
	 * @param servletResponse
	 */
	void sendReplyMsg4Video(ReplyMsg4Video replyMsg4Video, HttpServletResponse servletResponse);
	
	/**
	 * 被动回复音频消息
	 * @param replyMsg4Voice
	 * @param servletResponse
	 */
	void sendReplyMsg4Voice(ReplyMsg4Voice replyMsg4Voice, HttpServletResponse servletResponse);
	
	/**
	 * 上传附件并返回MediaID
	 * @param file
	 * @return
	 * @throws WeixinException
	 */
	String uploadMedia(String appname, File file,MediaType mediaType) throws WeixinException ;
	
	/**
	 * 通过MediaId下载Media附件
	 * @param mediaId
	 * @param file
	 * @throws WeixinException
	 */
	void downloadMediaById(String appname, String mediaId,File file) throws WeixinException;
	
	/**
	 * 获取永久二维码票据
	 * @param appname
	 * @param actionInfo
	 * @param sceneId
	 * @return
	 * @throws WeixinException
	 */
	Ticket getPermanentQCTicket(String appname, Integer sceneId) throws WeixinException;
	
	/**
	 * 获取临时二维码票据
	 * @param appname
	 * @param actionInfo
	 * @param sceneId
	 * @return
	 * @throws WeixinException
	 */
	Ticket getTmpQCTicket(String appname, int expireTime, Integer sceneId) throws WeixinException;
	
	/**
	 * 下载二维码图片
	 * @param ticket
	 * @param image
	 * @throws WeixinException
	 */
	void downloadQCImage(String ticket, File image) throws WeixinException;
	
	/**
	 * 生成短url
	 * @param appname
	 * @param url
	 * @return
	 * @throws WeixinException
	 */
	String buildShortUrl(String appname, String url) throws WeixinException;
	
	/**
	 * 关闭
	 */
	void close();
	
	/**
	 * 群发上传图文资源
	 * @param bean
	 * @return
	 * @throws WeixinException
	 */
	UploadBeanResult uploadNewsBean(String appname, UploadNewsBean bean) throws WeixinException;
	
	/**
	 * 群发上传视频资源
	 * @param bean
	 * @return
	 * @throws WeixinException
	 */
	UploadBeanResult uploadVideoBean(String appname, UploadVideoBean bean) throws WeixinException;
	
	/**
	 * 群发上传视频资源，从Local直接上传
	 * @param appName
	 * @param file
	 * @param title
	 * @param description
	 * @return
	 * @throws WeixinException
	 */
	UploadBeanResult uploadVideoBean(String appName, File file, String title, String description) throws WeixinException;
	
	/**
	 * 通过分组ID群发消息
	 * @param massMsg
	 * @return
	 * @throws WeixinException
	 */
	MassResult sendMassMsgByGroup(String appname, AbstractGroupMassMsg massMsg) throws WeixinException;
	
	/**
	 * 通过OpenIds群发消息
	 * @param appname
	 * @param massMsg
	 * @return
	 * @throws WeixinException
	 */
	MassResult sendMassMsgByOpenIds(String appname, AbstractOpenIdsMassMsg massMsg) throws WeixinException;
	
	/**
	 * 删除群发消息
	 * @param msgid
	 * @throws WeixinException
	 */
	void deleteMassMsg(String appname, int msgid) throws WeixinException;
	
	/**
	 * 获取OAuth2类型的URL信息
	 * @param appname
	 * @param redirectUrl
	 * @param scope
	 * @param state
	 * @return
	 */
	String getOAuth2Url(String appname, String redirectUrl, Scope scope, String state);
	
	/**
	 * 通过Appname和回调返回的Code获取OAuth2AccessTokenResult
	 * @param appname
	 * @param code
	 * @return
	 */
	OAuth2AccessTokenResult getOAuth2AccessTokenResult(String appname, String code) throws WeixinException;
	
	/**
	 * 刷新OA
	 * @param appname
	 * @param refreshToken
	 * @return
	 * @throws WeixinException
	 */
	OAuth2AccessTokenResult refreshOAuth2AccessTokenResult(String appname, String refreshToken) throws WeixinException;
	
	/**
	 * 获取OAuth2UserInfo信息
	 * @param oauth2AccessToken
	 * @param openId
	 * @param language
	 * @return
	 * @throws WeixinException
	 */
	OAuth2UserInfo getOAuth2UserInfo(String oauth2AccessToken, String openId, Language language) throws WeixinException;
	
	/**
	 * 验证OAuth2AccessToken 是否合法
	 * @param oauth2AccessToken
	 * @param openId
	 * @return
	 * @throws WeixinException
	 */
	boolean valdiateOAuth2AccessToken(String oauth2AccessToken, String openId) throws WeixinException;
	
	/**
	 * 获取回调结果值
	 * @param servletRequest
	 * @return
	 */
	OAuth2CallbackResult getOAuth2CallbackResult(HttpServletRequest servletRequest);
	
	/**
	 * 发布模板消息
	 * @param appname
	 * @param templateBodyData
	 * @throws WeixinException
	 */
	void sendTemplateMsg(String appname, TemplateBodyData templateBodyData) throws WeixinException;
}
