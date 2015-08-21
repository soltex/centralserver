/**
 * 
 */
package com.vanstone.centralserver.common.corp.msg;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.vanstone.centralserver.common.Constants;
import com.vanstone.centralserver.common.JsonUtil;
import com.vanstone.centralserver.common.MyAssert;
import com.vanstone.centralserver.common.corp.ICorpApp;
import com.vanstone.centralserver.common.corp.media.MPNewsArticle;
import com.vanstone.centralserver.common.weixin.WeixinException;
import com.vanstone.centralserver.common.weixin.WeixinException.ErrorCode;

/**
 * @author shipeng
 */
public class CorpMsg4Mpnews extends AbstractCorpMsg {

	private String mediaID;
	private List<MPNewsArticle> mpNewsArticles = new ArrayList<MPNewsArticle>();
	private boolean forever = false;

	/**
	 * 全部
	 * @param agentid
	 * @param safe
	 */
	public CorpMsg4Mpnews(ICorpApp agentid, boolean safe) {
		super(CorpMsgType.News ,agentid, safe, true, null, null, null);
		this.forever = false;
	}
	
	/**
	 * 全部
	 * @param agentid
	 * @param safe
	 * @param mediaID
	 */
	public CorpMsg4Mpnews(ICorpApp agentid, boolean safe, String mediaID) {
		super(CorpMsgType.News ,agentid, safe, true, null, null, null);
		this.forever = true;
		this.mediaID = mediaID;
	}
	
	/**
	 * 非全部,需要指定用户集合
	 * @param agentid
	 * @param safe
	 * @param touserids
	 * @param topartyids
	 * @param tagids
	 */
	public CorpMsg4Mpnews(ICorpApp agentid, boolean safe, Collection<String> touserids, Collection<String> topartyids, Collection<String> tagids) {
		super(CorpMsgType.News, agentid, safe, false, touserids, topartyids, tagids);
		this.forever = false;
	}
	
	/**
	 * @param agentid
	 * @param safe
	 * @param touserids
	 * @param topartyids
	 * @param tagids
	 * @param mediaID
	 */
	public CorpMsg4Mpnews(ICorpApp agentid, boolean safe, Collection<String> touserids, Collection<String> topartyids, Collection<String> tagids, String mediaID) {
		super(CorpMsgType.News, agentid, safe, false, touserids, topartyids, tagids);
		this.forever = true;
		this.mediaID = mediaID;
	}
	
	public String getMediaID() {
		return mediaID;
	}

	public List<MPNewsArticle> getMpNewsArticles() {
		return mpNewsArticles;
	}

	public boolean isForever() {
		return forever;
	}

	/**
	 * 添加元素值
	 * @param title
	 * @param thumbMediaId
	 * @param author
	 * @param contentSourceUrl
	 * @param content
	 * @param digest
	 * @param showCoverPic
	 * @throws WeixinException
	 */
	public void addMpnewsArticle(String title, String thumbMediaId, String author, String contentSourceUrl, String content, String digest, boolean showCoverPic) throws WeixinException {
		if (this.isForever()) {
			throw new IllegalArgumentException();
		}
		if (this.mpNewsArticles.size() > Constants.MAX_ARTICLE_ITEM_NUM) {
			throw new WeixinException(ErrorCode.MAX_ARTICLE_ITEM_COUNT_GT_10);
		}
		MPNewsArticle mpNewsArticle = new MPNewsArticle(title, thumbMediaId, author, contentSourceUrl, content, digest, showCoverPic);
		this.mpNewsArticles.add(mpNewsArticle);
	}

	public void clearMpnewsArticles() {
		this.mpNewsArticles.clear();
	}
	
	@Override
	public String toJson() {
		Map<String, Object> map = new HashMap<String, Object>();
		if (this.isForever()) {
			// 是否永久的
			MyAssert.hasText(mediaID);
			Map<String, Object> tempMap = new HashMap<String, Object>();
			tempMap.put("media_id", this.getMediaID());
			map.put("mpnews", tempMap);
		}else{
			MyAssert.notEmpty(mpNewsArticles);
			List<Map<String, Object>> articles = new ArrayList<Map<String,Object>>();
			for (MPNewsArticle mpNewsArticle : mpNewsArticles) {
				Map<String, Object> tempMap = new HashMap<String, Object>();
				tempMap.put("title", mpNewsArticle.getTitle());
				tempMap.put("thumb_media_id", mpNewsArticle.getThumbMediaId());
				tempMap.put("author", mpNewsArticle.getAuthor());
				tempMap.put("content_source_url", mpNewsArticle.getContentSourceUrl());
				tempMap.put("content", mpNewsArticle.getContent());
				tempMap.put("digest", mpNewsArticle.getDigest());
				tempMap.put("show_cover_pic", mpNewsArticle.isShowCoverPic() ? 1 : 0);
				articles.add(tempMap);
			}
			Map<String, Object> articleMap = new HashMap<String, Object>();
			articleMap.put("articles", articles);
			map.put("mpnews", articleMap);
		}
		return JsonUtil.object2PrettyString(map, false);
	}
}
