/**
 * 
 */
package com.vanstone.centralserver.common.weixin.wrap.msg.mass;

import java.util.ArrayList;
import java.util.Collection;

import com.vanstone.centralserver.common.JsonUtil;
import com.vanstone.centralserver.common.MyAssert;
import com.vanstone.centralserver.common.weixin.WeixinException;

/**
 * @author shipeng
 * 
 */
public class UploadNewsBean extends AbstractUploadBean {
	
	/** 图文消息，一个图文消息支持1到10条图文 */
	private Collection<NewsBeanItem> articles = new ArrayList<UploadNewsBean.NewsBeanItem>();
	
	public void addArticle(String thumb_media_id, String author, String title, String content_source_url,
			String content, String digest, boolean show_cover_pic) throws WeixinException  {
		if (articles.size() >10) {
			throw new WeixinException(WeixinException.ErrorCode.MAX_ARTICLE_ITEM_COUNT_GT_10);
		}
		MyAssert.hasText( thumb_media_id );
		MyAssert.hasText("title");
		MyAssert.hasText("content");
		NewsBeanItem item = new NewsBeanItem();
		item.setThumb_media_id(thumb_media_id);
		item.setAuthor(author);
		item.setTitle(title);
		item.setContent_source_url(content_source_url);
		item.setContent(content);
		item.setDigest(digest);
		item.setShow_cover_pic(show_cover_pic ? 1 : 0);
		this.articles.add(item);
	}
	
	public void clear() {
		this.articles.clear();
	}
	
	/**
	 * 转换为Json字符串
	 * @return
	 */
	@Override
	public String toJson() {
		return JsonUtil.object2PrettyString(this, false);
	}
	
	
	
	
	
	
	
	
	
	
	public class NewsBeanItem {
		/** 图文消息缩略图的media_id，可以在基础支持-上传多媒体文件接口中获得 */
		private String thumb_media_id;
		/**图文消息的作者*/
		private String author;
		/**图文消息的标题*/
		private String title;
		/** 在图文消息页面点击“阅读原文”后的页面 */
		private String content_source_url;
		/** 图文消息页面的内容，支持HTML标签 */
		private String content;
		/**  图文消息页面的内容，支持HTML标签  */
		private String digest;
		/** 是否显示封面，1为显示，0为不显示 */
		private int show_cover_pic;
		
		public String getContent() {
			return content;
		}

		public void setContent(String content) {
			this.content = content;
		}

		public String getThumb_media_id() {
			return thumb_media_id;
		}

		public void setThumb_media_id(String thumb_media_id) {
			this.thumb_media_id = thumb_media_id;
		}

		public String getAuthor() {
			return author;
		}

		public void setAuthor(String author) {
			this.author = author;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public String getContent_source_url() {
			return content_source_url;
		}

		public void setContent_source_url(String content_source_url) {
			this.content_source_url = content_source_url;
		}

		public String getDigest() {
			return digest;
		}

		public void setDigest(String digest) {
			this.digest = digest;
		}

		public int getShow_cover_pic() {
			return show_cover_pic;
		}

		public void setShow_cover_pic(int show_cover_pic) {
			this.show_cover_pic = show_cover_pic;
		}
		
	}

}
