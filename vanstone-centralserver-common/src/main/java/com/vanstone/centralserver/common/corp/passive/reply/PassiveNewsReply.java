/**
 * 
 */
package com.vanstone.centralserver.common.corp.passive.reply;

import java.util.ArrayList;
import java.util.List;

import org.dom4j.Element;

import com.vanstone.centralserver.common.Constants;
import com.vanstone.centralserver.common.MyAssert;
import com.vanstone.centralserver.common.corp.msg.CorpMsgType;
import com.vanstone.centralserver.common.corp.passive.AbstractPassiveReply;
import com.vanstone.centralserver.common.weixin.WeixinException;
import com.vanstone.centralserver.common.weixin.WeixinException.ErrorCode;
import com.vanstone.centralserver.common.weixin.wrap.msg.ArticleItem;

/**
 * @author shipeng
 *
 */
public class PassiveNewsReply extends AbstractPassiveReply {
	
	private List<ArticleItem> items = new ArrayList<ArticleItem>();
	
	public PassiveNewsReply() {
		super(CorpMsgType.News);
	}

	public void addArticleItem(String title, String description, String picUrl, String url) throws WeixinException {
		MyAssert.hasText(title);
		if (this.items.size() > Constants.MAX_ARTICLE_ITEM_NUM) {
			throw new WeixinException(ErrorCode.MAX_ARTICLE_ITEM_COUNT_GT_10);
		}
		ArticleItem item = new ArticleItem();
		item.setTitle(title);
		item.setDescription(description);
		item.setPicUrl(picUrl);
		item.setUrl(url);
		this.items.add(item);
	}
	
	/* (non-Javadoc)
	 * @see com.vanstone.centralserver.common.corp.passive.AbstractPassiveReply#__to_encrypt_json_internal(org.dom4j.Element)
	 */
	@Override
	protected void __to_encrypt_json_internal(Element rootElement) {
		MyAssert.notEmpty(this.items);
		Element ArticleCountElemnet = rootElement.addElement("ArticleCount");
		ArticleCountElemnet.setText(String.valueOf(this.items.size()));
		
		Element ArticlesElement = rootElement.addElement("Articles");
		for (ArticleItem item : items) {
			Element itemElement = ArticlesElement.addElement("item");
			
			Element TitleElement = itemElement.addElement("Title");
			TitleElement.setText(item.getTitle());
			
			if (item.getDescription() != null && !item.getDescription().equals("")) {
				Element DescriptionElement = itemElement.addElement("Description");
				DescriptionElement.setText(item.getDescription());
			}
			
			if (item.getPicUrl() != null && !item.getPicUrl().equals("")) {
				Element PicUrlElement = itemElement.addElement("PicUrl");
				PicUrlElement.setText(item.getPicUrl());
			}
			
			if (item.getUrl() != null && !item.getUrl().equals("")) {
				Element UrlElement = itemElement.addElement("Url");
				UrlElement.setText(item.getUrl());
			}
		}
	}

}
