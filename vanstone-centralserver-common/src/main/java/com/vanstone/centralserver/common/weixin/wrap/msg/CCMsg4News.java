/**
 * 
 */
package com.vanstone.centralserver.common.weixin.wrap.msg;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import com.vanstone.centralserver.common.Constants;
import com.vanstone.centralserver.common.JsonUtil;
import com.vanstone.centralserver.common.weixin.WeixinException;

/**
 * @author shipeng
 *
 */
public class CCMsg4News extends AbstractCustomerServiceMsg {
	
	private Collection<ArticleItem> articleItems = new ArrayList<ArticleItem>();
	
	public CCMsg4News() {
		this.setMsgtype(AbstractMsg.COMMON_TYPE_NEWS);
	}
	
	/**
	 * 图文消息个数，限制为10条以内
	 * @return
	 */
	public int getArticleCount() {
		return this.articleItems.size();
	}
	
	public void addArticleItem(ArticleItem item) throws WeixinException {
		if (item == null) {
			throw new IllegalArgumentException();
		}
		if (this.articleItems.size() >=Constants.MAX_ARTICLE_ITEM_NUM) {
			throw new WeixinException(WeixinException.ErrorCode.MAX_ARTICLE_ITEM_COUNT_GT_10);
		}
		this.articleItems.add(item);
	}
	
	/**
	 * 获取ArticleItem列表
	 * @return
	 */
	public Collection<ArticleItem> getArticleItems() {
		return articleItems;
	}

	@Override
	public String toJson() {
		if (this.getArticleCount() <=0 ) {
			throw new IllegalArgumentException();
		}
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("touser", this.getTouser());
		map.put("msgtype", this.getMsgtype());
		Collection<Map<String, Object>> newsList = new ArrayList<Map<String,Object>>();
		for (ArticleItem item : this.getArticleItems()) {
			Map<String, Object> itemMap = new LinkedHashMap<String, Object>();
			itemMap.put("title", item.getTitle());
			itemMap.put("description", item.getDescription());
			itemMap.put("url", item.getUrl());
			itemMap.put("picurl", item.getPicUrl());
			
			newsList.add(itemMap);
		}
		Map<String, Collection<Map<String, Object>>> articlesMap = new LinkedHashMap<String, Collection<Map<String,Object>>>();
		articlesMap.put("articles", newsList);
		map.put("news", articlesMap);
		return JsonUtil.object2PrettyString(map,false);
	}
	
}
