/**
 * 
 */
package com.vanstone.centralserver.common.corp.media;

import com.vanstone.centralserver.common.MyAssert;

/**
 * @author shipeng
 * title 	是 	图文消息的标题
	thumb_media_id 	是 	图文消息缩略图的media_id, 可以在上传永久素材接口中获得
	author 	否 	图文消息的作者
	content_source_url 	否 	图文消息点击“阅读原文”之后的页面链接
	content 	是 	图文消息的内容，支持html标签
	digest 	否 	图文消息的描述
	show_cover_pic 	否 	是否显示封面，1为显示，0为不显示。默认为0 
 */
public class MPNewsArticle {
	
	private String title;
	private String thumbMediaId;
	private String author;
	private String contentSourceUrl;
	private String content;
	private String digest;
	private boolean showCoverPic = false;

	public MPNewsArticle(String title, String thumbMediaId, String author, String contentSourceUrl, String content, String digest, boolean showCoverPic) {
		MyAssert.hasText(title);
		MyAssert.hasText(thumbMediaId);
		this.title = title;
		this.thumbMediaId = thumbMediaId;
		this.author = author;
		this.contentSourceUrl = contentSourceUrl;
		this.content = content;
		this.digest = digest;
		this.showCoverPic = showCoverPic;
	}
	
	public String getTitle() {
		return title;
	}

	public String getThumbMediaId() {
		return thumbMediaId;
	}

	public String getAuthor() {
		return author;
	}

	public String getContentSourceUrl() {
		return contentSourceUrl;
	}

	public String getContent() {
		return content;
	}

	public String getDigest() {
		return digest;
	}

	public boolean isShowCoverPic() {
		return showCoverPic;
	}
	
}
