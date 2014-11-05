/**
 * 
 */
package com.vanstone.centralserver.common.weixin.wrap.msg;

import java.util.ArrayList;
import java.util.Collection;

import org.dom4j.Document;
import org.dom4j.Element;

import com.vanstone.centralserver.common.Constants;
import com.vanstone.centralserver.common.weixin.WeixinException;

/**
 * 回复图文消息
	<xml>
		<ToUserName><![CDATA[toUser]]></ToUserName>
		<FromUserName><![CDATA[fromUser]]></FromUserName>
		<CreateTime>12345678</CreateTime>
		<MsgType><![CDATA[news]]></MsgType>
		<ArticleCount>2</ArticleCount>
		<Articles>
			<item>
			<Title><![CDATA[title1]]></Title> 
			<Description><![CDATA[description1]]></Description>
			<PicUrl><![CDATA[picurl]]></PicUrl>
			<Url><![CDATA[url]]></Url>
			</item>
			<item>
			<Title><![CDATA[title]]></Title>
			<Description><![CDATA[description]]></Description>
			<PicUrl><![CDATA[picurl]]></PicUrl>
			<Url><![CDATA[url]]></Url>
			</item>
		</Articles>
	</xml> 
 * @author shipeng
 */
public class ReplyMsg4News extends AbstractMsg {

	private Collection<ArticleItem> articleItems = new ArrayList<ArticleItem>();
	
	public ReplyMsg4News() {
		super(COMMON_TYPE_NEWS);
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
	
	public Collection<ArticleItem> getArticleItems() {
		return articleItems;
	}
	
	public String toReplyXml() {
		
		if (this.getArticleCount() <=0) {
			throw new IllegalArgumentException();
		}
		Document document = this.toReplyDoc();
		Element xmlElement = document.getRootElement();
		
		Element ArticleCountElement = xmlElement.addElement("ArticleCount");
		ArticleCountElement.setText(String.valueOf(this.getArticleCount()));
		
		Element ArticlesElement = xmlElement.addElement("Articles");
		for (ArticleItem item : this.getArticleItems()) {
			Element itemElement = ArticlesElement.addElement("item");
			/**
			 * <Title><![CDATA[title1]]></Title> 
				<Description><![CDATA[description1]]></Description>
				<PicUrl><![CDATA[picurl]]></PicUrl>
				<Url><![CDATA[url]]></Url>
			 */
			Element titleElement = itemElement.addElement("Title");
			titleElement.setText(item.getTitle());
			
			Element descriptionElement = itemElement.addElement("Description");
			descriptionElement.setText(item.getDescription());
			
			Element picUrlElement = itemElement.addElement("PicUrl");
			picUrlElement.setText(item.getPicUrl());
			
			Element UrlElement = itemElement.addElement("Url");
			UrlElement.setText(item.getUrl());
		}
		return document.asXML();
	}
}
