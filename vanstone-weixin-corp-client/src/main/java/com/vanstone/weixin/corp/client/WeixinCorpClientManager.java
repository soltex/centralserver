package com.vanstone.weixin.corp.client;

import java.io.File;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.vanstone.centralserver.common.corp.CorpAppInfo;
import com.vanstone.centralserver.common.corp.ICorpApp;
import com.vanstone.centralserver.common.corp.ReportLocationFlag;
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
import com.vanstone.centralserver.common.weixin.WeixinException;
import com.vanstone.centralserver.common.weixin.wrap.Sex;
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
	 * 下载临时文件，到指定文件夹中
	 * @param mediaID 媒体ID
	 * @param filePath 文件路径
	 * @param filenameWithoutExtName 可null，如为null，则自动生成文件名称
	 * @throws WeixinException
	 */
	File downloadTempMedia(String mediaID, File filePath, String filenameWithoutExtName) throws WeixinException;
	
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
	 * 微信二次认证
	 * @param userID
	 * @throws WeixinException
	 */
	void authSuccess(String userID) throws WeixinException;
	
	//部门管理
	/**
	 * @param name
	 * @param parentid
	 * @param order
	 * @param id
	 * @return
	 * @throws WeixinException
	 */
	int createDepartment(String name, int parentid, Integer order, Integer id) throws WeixinException;
	
	/**
	 * @param name
	 * @return
	 * @throws WeixinException
	 */
	int createDepartment(String name) throws WeixinException;
	
	/**
	 * 更新部门信息
	 * @param id
	 * @param name
	 * @param parentid
	 * @param order
	 * @throws WeixinException
	 */
	void updateDepartment(int id, String name, int parentid, Integer order) throws WeixinException;
	
	/**
	 * 更新部门信息
	 * @param id
	 * @param name
	 * @throws WeixinException
	 */
	void upateDepartment(int id, String name) throws WeixinException;
	
	/**
	 * 删除部门信息
	 * @param id
	 * @throws WeixinException
	 */
	void deleteDepartment(int id) throws WeixinException;
	
	/**
	 * 获取部门列表
	 * @param id
	 * @return
	 * @throws WeixinException
	 */
	List<CorpDepartment> getDepartments(int id) throws WeixinException;
	
	/**
	 * 获取根节点下的部门列表
	 * @return
	 * @throws WeixinException
	 */
	List<CorpDepartment> getDepartments() throws WeixinException;
	
	/**
	 * 获取企业用户详情
	 * @param corp
	 * @param userid
	 * @return
	 * @throws WeixinException
	 */
	CorpUserInfo getCorpUserInfo(String userid) throws WeixinException;
	
	/**
	 * 添加CorpUser
	 * @param userid
	 * @param name
	 * @param department
	 * @param position
	 * @param mobile
	 * @param email
	 * @param weixinid
	 * @param sex
	 * @param avatar_mediaid
	 * @param extAttrs
	 * @return
	 * @throws WeixinException
	 */
	void addCorpUserInfo(String userid, String name, Integer department, String position, String mobile, String email, String weixinid, Sex sex, String avatar_mediaid, List<UserExtAttr> extAttrs) throws WeixinException;
	
	/**
	 * 更新CorpUser信息
	 * @param userid
	 * @param name
	 * @param department
	 * @param position
	 * @param mobile
	 * @param email
	 * @param weixinid
	 * @param sex
	 * @param avatar_mediaid
	 * @param extAttrs
	 * @param enable
	 * @throws WeixinException
	 */
	void updateCorpUserInfo(String userid, String name, Integer department, String position, String mobile, String email, String weixinid, Sex sex, String avatar_mediaid, List<UserExtAttr> extAttrs, Boolean enable) throws WeixinException;
	
	/**
	 * 删除用户
	 * @param userid
	 * @throws WeixinException
	 */
	void deleteCorpUser(String userid) throws WeixinException;
	
	/**
	 * 批量删除用户
	 * @param userids
	 * @throws WeixinException
	 */
	void batchDeleteCorpUsers(List<String> userids) throws WeixinException;
	
	/**
	 * @param departmentid
	 * @param fetchChild
	 * @param all
	 * @param subscribe
	 * @param forbit
	 * @param unsubscribe
	 * @return
	 */
	List<CorpUserInfo> getSimpleListCorpUsers(int departmentid, Boolean fetchChild, boolean all, boolean subscribe, boolean forbit, boolean unsubscribe) throws WeixinException;
	
	/**
	 * 获取详情用户信息列表
	 * @param departmentid
	 * @param fetchChild
	 * @param all
	 * @param subscribe
	 * @param forbit
	 * @param unsubscribe
	 * @return
	 * @throws WeixinException
	 */
	List<CorpUserInfo> getFullListCorpUsers(int departmentid, Boolean fetchChild, boolean all, boolean subscribe, boolean forbit, boolean unsubscribe) throws WeixinException;
	
	/**
	 * 发送邀请
	 * @param userid
	 * @return
	 * @throws WeixinException
	 */
	WeixinOrEmail sendInvite(String userid) throws WeixinException;
	
	
	//标签相关 =========================== 
	
	/**
	 * @param tagname
	 * @param tagid
	 * @return
	 * @throws WeixinException
	 */
	int addTag(String tagname, Integer tagid) throws WeixinException;
	
	/**
	 * @param tagid
	 * @param tagname
	 * @throws WeixinException
	 */
	void updateTag(int tagid, String tagname) throws WeixinException;
	
	/**
	 * @param tagid
	 * @throws WeixinException
	 */
	void deleteTag(int tagid) throws WeixinException;
	
	/**
	 * @param tagid
	 * @return
	 * @throws WeixinException
	 */
	UserCollectionWithTag getUserCollectionWithTag(int tagid) throws WeixinException;
	
	/**
	 * @param tagid
	 * @param userids
	 * @param partylist
	 * @return
	 * @throws WeixinException
	 */
	CorpMsgResult addTagRelUsers(int tagid, List<String> userids, List<Integer> partylist) throws WeixinException;
	
	/**
	 * @param tagid
	 * @param userids
	 * @param partlist
	 * @return
	 * @throws WeixinException
	 */
	CorpMsgResult deleteTagRelUsers(int tagid, List<String> userids, List<Integer> partlist) throws WeixinException;
	
	/**
	 * @return
	 * @throws WeixinException
	 */
	List<Tag> getTags() throws WeixinException;
	
	/**
	 * 获取JSAPI Ticket
	 * @return
	 * @throws WeixinException
	 */
	TicketObject initialJSAPISignature(HttpServletRequest servletRequest) throws WeixinException;
	
}
