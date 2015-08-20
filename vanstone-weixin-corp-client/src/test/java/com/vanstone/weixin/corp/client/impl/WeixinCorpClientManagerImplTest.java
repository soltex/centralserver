package com.vanstone.weixin.corp.client.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

import org.junit.Test;

import com.vanstone.centralserver.common.corp.CorpAppInfo;
import com.vanstone.centralserver.common.corp.media.MPNewsArticle;
import com.vanstone.centralserver.common.corp.media.MediaResult;
import com.vanstone.centralserver.common.corp.media.MediaStat;
import com.vanstone.centralserver.common.corp.media.MediaType;
import com.vanstone.centralserver.common.util.DebugUtil;
import com.vanstone.centralserver.common.weixin.WeixinException;
import com.vanstone.centralserver.common.weixin.wrap.ButtonType;
import com.vanstone.centralserver.common.weixin.wrap.menu.Menu;
import com.vanstone.centralserver.common.weixin.wrap.menu.MenuItem;
import com.vanstone.weixin.corp.client.WeixinCorpClientFactory;
import com.vanstone.weixin.corp.client.WeixinCorpClientManager;

public class WeixinCorpClientManagerImplTest {

	@Test
	public void testGetAccessToken() throws WeixinException {
		WeixinCorpClientManager weixinCorpClientManager = WeixinCorpClientFactory.getWeixinCorpClientManager();
		String accessToken = weixinCorpClientManager.getAccessToken(CorpInstance.Sagacityidea);
		System.out.println(accessToken);
	}
	
	@Test
	public void testCreateMenu() throws WeixinException {
		WeixinCorpClientManager weixinCorpClientManager = WeixinCorpClientFactory.getWeixinCorpClientManager();
		Menu menu = new Menu();
		MenuItem item1 = new MenuItem("菜单1");
		
		
		MenuItem item11 = new MenuItem(ButtonType.View, "菜单11", "http://www.baidu.com");
		MenuItem item12 = new MenuItem(ButtonType.Click, "菜单12", "http://www.baidu.com");
		MenuItem item13 = new MenuItem(ButtonType.View, "菜单13", "http://www.baidu.com");
		MenuItem item14 = new MenuItem(ButtonType.View, "菜单14", "http://www.baidu.com");
		item1.addSubMenuItem(item11);
		item1.addSubMenuItem(item12);
		item1.addSubMenuItem(item13);
		item1.addSubMenuItem(item14);
		
		menu.addMenuItem(item1);
		
		System.out.println(menu.toJson());
		
		weixinCorpClientManager.createMenu(CorpInstance.Sagacityidea, CorpAppInstance.App1, menu);
//		weixinCorpClientManager.createMenu(CorpInstance.Sagacityidea, CorpAppInstance.App1, menu);
//		weixinCorpClientManager.createMenu(CorpInstance.Sagacityidea, CorpAppInstance.App3, menu);
//		weixinCorpClientManager.createMenu(CorpInstance.Sagacityidea, CorpAppInstance.App4, menu);
//		weixinCorpClientManager.createMenu(CorpInstance.Sagacityidea, CorpAppInstance.App5, menu);
	}
	
	@Test
	public void testgetCorpAppInfo() throws WeixinException {
		WeixinCorpClientManager weixinCorpClientManager = WeixinCorpClientFactory.getWeixinCorpClientManager();
		CorpAppInfo corpAppInfo = weixinCorpClientManager.getCorpAppInfo(CorpInstance.Sagacityidea, CorpAppInstance.App1);
		DebugUtil.print(corpAppInfo);
	}
	
	@Test
	public void testgetCorpAppInfos() throws WeixinException {
		WeixinCorpClientManager weixinCorpClientManager = WeixinCorpClientFactory.getWeixinCorpClientManager();
		Collection<CorpAppInfo> appInfos = weixinCorpClientManager.getCorpAppInfos(CorpInstance.Sagacityidea);
		DebugUtil.print(appInfos);
	}
	
	@Test
	public void testuploadTempMedia() {
		WeixinCorpClientManager weixinCorpClientManager = WeixinCorpClientFactory.getWeixinCorpClientManager();
		try {
			MediaResult result = weixinCorpClientManager.uploadTempMedia(CorpInstance.Sagacityidea, MediaType.Image, new File("F:/2020.jpg"));
			DebugUtil.print(result);
		} catch (WeixinException e) {
			e.printStackTrace();
		}
	}
	
	private WeixinCorpClientManager weixinCorpClientManager = WeixinCorpClientFactory.getWeixinCorpClientManager();
	
	@Test
	public void testdownloadTempMedia() throws WeixinException {
		this.weixinCorpClientManager.downloadTempMedia(CorpInstance.Sagacityidea, "1ZEFQQFPexFbYysKnw5ONukzsJTwMdp8OpVIimdz9guqLHW8ZSJCk3fgZldWXircN5W2VdJPGbty0XribS4eMKw", new File("e:/aaa.jpg"));
	}
	
	@Test
	public void testuploadMPNewsArticle() throws WeixinException {
		this.weixinCorpClientManager.downloadForeverMedia(CorpInstance.Sagacityidea, CorpAppInstance.App1, "2ZQyuFRuvewj5_9jW6Qt8BsmKNNNTS8JA4ZbUuLgbmxalAy7YHdhGaEmHU71Uj1wMeDaL5HDS-ZANvTx71n7RWw", new File("e:/aaa.jpg"));
	}
	
	@Test
	public void testuploadForeverMedia() throws WeixinException {
		String mediaID = this.weixinCorpClientManager.uploadForeverMedia(CorpInstance.Sagacityidea, CorpAppInstance.App1, MediaType.Image, new File("f:/2020.jpg"));
		System.out.println(mediaID);
	}
	
	@Test
	public void testuloadmpnewsForeverMedia() throws WeixinException {
		Collection<MPNewsArticle> articles = new ArrayList<MPNewsArticle>();
		String mediaID = this.weixinCorpClientManager.uploadForeverMedia(CorpInstance.Sagacityidea, CorpAppInstance.App1, MediaType.Image, new File("f:/2020.jpg"));
		MPNewsArticle article = new MPNewsArticle("呵呵呵", mediaID, null, null, "呵呵呵呵呵呵呵", "和是简介", false);
		articles.add(article);
		this.weixinCorpClientManager.uploadMPNewsArticle(CorpInstance.Sagacityidea, CorpAppInstance.App1, articles);
	}
	
	@Test
	public void testdownloadmpnews() throws WeixinException {
		String mediaID = "2iazhR8urNTCADmj3vthPhrBFSMP0y1rRKyqTP9nolv9-4tnOXHN6qKW86ABd8Qd7";
		Collection<MPNewsArticle> mpNewsArticles = this.weixinCorpClientManager.downloadForeverMedia(CorpInstance.Sagacityidea, CorpAppInstance.App1, mediaID, null);
		DebugUtil.print(mpNewsArticles);
	}
	
	@Test
	public void testgetMediaStat() throws WeixinException {
		MediaStat stat = this.weixinCorpClientManager.getMediaStat(CorpInstance.Sagacityidea, CorpAppInstance.App1);
		DebugUtil.print(stat);
	}
}
