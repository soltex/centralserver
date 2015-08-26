package com.vanstone.weixin.corp.client;

import java.io.File;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.vanstone.centralserver.common.corp.CorpAppInfo;
import com.vanstone.centralserver.common.corp.ICorpApp;
import com.vanstone.centralserver.common.corp.ReportLocationFlag;
import com.vanstone.centralserver.common.corp.media.MPNewsArticle;
import com.vanstone.centralserver.common.corp.media.MediaResult;
import com.vanstone.centralserver.common.corp.media.MediaStat;
import com.vanstone.centralserver.common.corp.media.MediaType;
import com.vanstone.centralserver.common.corp.msg.AbstractCorpMsg;
import com.vanstone.centralserver.common.corp.msg.CorpMsgResult;
import com.vanstone.centralserver.common.corp.oauth2.OAuth2Result;
import com.vanstone.centralserver.common.corp.oauth2.RedirectResult;
import com.vanstone.centralserver.common.corp.passive.AbstractPassiveReply;
import com.vanstone.centralserver.common.corp.user.CorpUserInfo;
import com.vanstone.centralserver.common.weixin.WeixinException;
import com.vanstone.centralserver.common.weixin.wrap.menu.Menu;

/**
 * 
 * 获取素材列表  未实现
 * 
 * @author shipeng
 */
public interface WeixinCorpClientManager {
	
	/**
	 * 生成AccessToken
	 * @param corp
	 * @return
	 * @throws WeixinException
	 */
	String getAccessToken() throws WeixinException;
	
	/**
	 * 创建菜单
	 * @param corp
	 * @param corpApp
	 * @throws WeixinException
	 */
	void createMenu(ICorpApp corpApp, Menu menu) throws WeixinException;
	
	/**
	 * 获取CorpAppInfo信息
	 * @param corp
	 * @param corpApp
	 * @return
	 * @throws WeixinException
	 */
	CorpAppInfo getCorpAppInfo(ICorpApp corpApp) throws WeixinException;
	
	/**
	 * 更新企业应用设置
	 * @param corp
	 * @param corpApp
	 * @param reportLocationFlag
	 * @param logoMediaID
	 * @param name
	 * @param description
	 * @param redirectDomain
	 * @param reportuser
	 * @param reportenter
	 * @throws WeixinException
	 */
	void updateCorpAppInfo(ICorpApp corpApp, ReportLocationFlag reportLocationFlag, String logoMediaID, String name, String description, String redirectDomain, boolean reportuser, boolean reportenter) throws WeixinException;
	
	/**
	 * 获取企业应用信息列表
	 * @param corp
	 * @return
	 * @throws WeixinException
	 */
	Collection<CorpAppInfo> getCorpAppInfos() throws WeixinException;
	
	/**
	 * 上传临时文件
	 * @param corp
	 * @param corpApp
	 * @param mediaType
	 * @param mediaFile
	 * @return
	 * @throws WeixinException
	 */
	MediaResult uploadTempMedia(MediaType mediaType, File mediaFile) throws WeixinException;
	
	/**
	 * 下载临时文件到指定文件里
	 * @param corp
	 * @param corpApp
	 * @param mediaID
	 * @param file
	 * @throws WeixinException
	 */
	void downloadTempMedia(String mediaID, File file) throws WeixinException;
	
	/**
	 * 上传MPNewsArticles
	 * @param corp
	 * @param corpApp
	 * @param articles
	 * @return
	 * @throws WeixinException
	 */
	String uploadMPNewsArticle(ICorpApp corpApp, Collection<MPNewsArticle> articles) throws WeixinException;
	
	/**
	 * 修改MPNewsArticles
	 * @param mediaID
	 * @param corp
	 * @param corpApp
	 * @param articles
	 * @throws WeixinException
	 */
	void updateMPNewsArticle(String mediaID, ICorpApp corpApp, Collection<MPNewsArticle> articles) throws WeixinException;
	
	/**
	 * 上传永久素材
	 * @param corp
	 * @param corpApp
	 * @param mediaType
	 * @param media
	 * @return
	 * @throws WeixinException
	 */
	String uploadForeverMedia(ICorpApp corpApp, MediaType mediaType, File media) throws WeixinException;
	
	/**
	 * 下载文件或者永久MPNewsArticle
	 * @param corp
	 * @param corpApp
	 * @param mediaID
	 * @param file
	 * @throws WeixinException
	 */
	Collection<MPNewsArticle> downloadForeverMedia(ICorpApp corpApp, String mediaID, File file) throws WeixinException;
	
	/**
	 * 删除永久素材
	 * @param corp
	 * @param corpApp
	 * @param mediaID
	 * @throws WeixinException
	 */
	void deleteForeverMedia(ICorpApp corpApp, String mediaID) throws WeixinException;
	
	/**
	 * 获取素材统计
	 * @param corp
	 * @param corpApp
	 * @return
	 * @throws WeixinException
	 */
	MediaStat getMediaStat(ICorpApp corpApp) throws WeixinException;
	
	/**
	 * 主动下发信息
	 * @param corpMsg
	 * @return
	 * @throws WeixinException
	 */
	CorpMsgResult sendCorpMsg(AbstractCorpMsg corpMsg) throws WeixinException;
	
	/**
	 * @param corp
	 * @param corpApp
	 * @param passiveReply
	 * @throws WeixinException
	 */
	void sendCorpReply(ICorpApp corpApp, AbstractPassiveReply passiveReply, String timestamp, String nonce, HttpServletResponse servletResponse) throws WeixinException;
	
	/**
	 * getCorpOAuth2RedirectURL
	 * @return
	 * @throws WeixinException
	 */
	String createOAuth2RedirectUrl(String redirectUri, String state) throws WeixinException;
	
	/**
	 * 获取回调RedirectResult
	 * @param servletRequest
	 * @return
	 */
	RedirectResult getRedirectResult(HttpServletRequest servletRequest);
	
	/**
	 * 获取用户信息
	 * @param corp
	 * @param code
	 * @return
	 * @throws WeixinException
	 */
	OAuth2Result getUserInfo(String code) throws WeixinException;
	
	
	//管理通讯录部分
	/**
	 * 获取企业用户详情
	 * @param corp
	 * @param userid
	 * @return
	 * @throws WeixinException
	 */
	CorpUserInfo getCorpUserInfo(String userid) throws WeixinException;
}
