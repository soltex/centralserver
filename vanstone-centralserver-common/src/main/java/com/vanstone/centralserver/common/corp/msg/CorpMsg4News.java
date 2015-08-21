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
import com.vanstone.centralserver.common.NotSupportError;
import com.vanstone.centralserver.common.corp.ICorpApp;
import com.vanstone.centralserver.common.weixin.WeixinException;
import com.vanstone.centralserver.common.weixin.WeixinException.ErrorCode;
import com.vanstone.centralserver.common.weixin.wrap.msg.ArticleItem;

/**
 * @author shipeng
 * News消息
 */
public class CorpMsg4News extends AbstractCorpMsg {

	private List<ArticleItem> items = new ArrayList<ArticleItem>();

	/**
	 * 全部
	 * @param agentid
	 * @param safe
	 */
	public CorpMsg4News(ICorpApp agentid, boolean safe) {
		super(CorpMsgType.News ,agentid, safe, true, null, null, null);
	}
	
	/**
	 * 非全部,需要指定用户集合
	 * @param agentid
	 * @param safe
	 * @param touserids
	 * @param topartyids
	 * @param tagids
	 */
	public CorpMsg4News(ICorpApp agentid, boolean safe, Collection<String> touserids, Collection<String> topartyids, Collection<String> tagids) {
		super(CorpMsgType.News, agentid, safe, false, touserids, topartyids, tagids);
	}
	
	public void addArticleItem(String title, String description, String url, String picurl) throws WeixinException {
		if (items.size() > Constants.MAX_ARTICLE_ITEM_NUM) {
			throw new WeixinException(ErrorCode.MAX_ARTICLE_ITEM_COUNT_GT_10);
		}
		ArticleItem item = new ArticleItem();
		item.setTitle(title);
		item.setDescription(description);
		item.setPicUrl(picurl);
		item.setUrl(url);
		this.items.add(item);
	}

	public void clearArticleItems() {
		this.items.clear();
	}

	public List<ArticleItem> getArticleItems() {
		return this.items;
	}

	@Override
	public boolean isSafe() {
		throw new NotSupportError();
	}
	
	@Override
	public String toJson() {
		MyAssert.notEmpty(this.items);
		Map<String, Object> map = this.toInteralMap();
		List<Map<String, Object>> articles = new ArrayList<Map<String, Object>>();
		for (ArticleItem item : items) {
			Map<String, Object> itemMap = new HashMap<String, Object>();
			itemMap.put("title", item.getTitle());
			itemMap.put("description", item.getDescription());
			itemMap.put("url", item.getUrl());
			itemMap.put("picurl", item.getPicUrl());
			articles.add(itemMap);
		}
		Map<String, Object> newsMap = new HashMap<String, Object>();
		newsMap.put("news", articles);
		map.put("news", newsMap);
		map.remove("safe");
		return JsonUtil.object2PrettyString(map, false);
	}
	
}
