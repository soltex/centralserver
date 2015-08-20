/**
 * 
 */
package com.vanstone.centralserver.common.corp.msg;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.vanstone.centralserver.common.Constants;
import com.vanstone.centralserver.common.JsonUtil;
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

	public CorpMsg4Mpnews(int agentid) {
		super(CorpMsgType.Mpnews, agentid);
		this.forever = false;
	}

	public CorpMsg4Mpnews(String mediaID, int agentid) {
		super(CorpMsgType.Mpnews, agentid);
		this.mediaID = mediaID;
		this.forever = true;
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
			Map<String, Object> tempMap = new HashMap<String, Object>();
			tempMap.put("media_id", this.getMediaID());
			map.put("mpnews", tempMap);
		}else{
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
			map.put("mpnews", articles);
		}
		return JsonUtil.object2PrettyString(map, false);
	}

}
