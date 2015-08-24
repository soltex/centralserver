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
import com.vanstone.centralserver.common.corp.msg.CorpMsg4File;
import com.vanstone.centralserver.common.corp.msg.CorpMsg4Img;
import com.vanstone.centralserver.common.corp.msg.CorpMsg4Text;
import com.vanstone.centralserver.common.util.DebugUtil;
import com.vanstone.centralserver.common.weixin.WeixinException;
import com.vanstone.centralserver.common.weixin.wrap.ButtonType;
import com.vanstone.centralserver.common.weixin.wrap.menu.Menu;
import com.vanstone.centralserver.common.weixin.wrap.menu.MenuItem;
import com.vanstone.weixin.corp.client.WeixinCorpClientFactory;
import com.vanstone.weixin.corp.client.WeixinCorpClientManager;
import com.vanstone.weixin.corp.client.conf.CorpClientConf;
import com.vanstone.weixin.corp.client.conf.xml.DefaultXmlConfInitiator;

@SuppressWarnings("unused")
public class WeixinCorpClientManagerImplTest {

	@Test
	public void testGetAccessToken() throws WeixinException {
		WeixinCorpClientManager weixinCorpClientManager = WeixinCorpClientFactory.getWeixinCorpClientManager();
		// String accessToken =
		// weixinCorpClientManager.getAccessToken(CorpInstance.Sagacityidea);
		// System.out.println(accessToken);
	}

	@Test
	public void testCreateMenu() throws WeixinException {
		DefaultXmlConfInitiator initiator = new DefaultXmlConfInitiator();
		initiator.initial();
		
		WeixinCorpClientManager weixinCorpClientManager = WeixinCorpClientFactory.getWeixinCorpClientManager();
		Menu menu = new Menu();
		MenuItem item1 = new MenuItem("菜单1");
		
		MenuItem item11 = new MenuItem(ButtonType.View, "菜单11", "http://www.baidu.com");
		MenuItem item12 = new MenuItem(ButtonType.View, "菜单12", "http://www.baidu.com");
		MenuItem item13 = new MenuItem(ButtonType.View, "菜单13", "http://www.baidu.com");
		MenuItem item14 = new MenuItem(ButtonType.View, "菜单14", "http://www.baidu.com");
		MenuItem item5 = new MenuItem(ButtonType.View, "Oauth2测试", "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx87c2c0c1d9ec9f7a&redirect_uri=http://testcorp.sagacityidea.com/test&response_type=code&scope=snsapi_base&state=ABC#wechat_redirect");
		item1.addSubMenuItem(item11);
		item1.addSubMenuItem(item12);
		item1.addSubMenuItem(item13);
		item1.addSubMenuItem(item14);

		menu.addMenuItem(item1);

		System.out.println(menu.toJson());

		weixinCorpClientManager.createMenu(CorpClientConf.getInstance().getCorp(), CorpClientConf.getInstance().getCorpApp(1), menu);
		// weixinCorpClientManager.createMenu(CorpInstance.Sagacityidea,
		// CorpAppInstance.App1, menu);
		// weixinCorpClientManager.createMenu(CorpInstance.Sagacityidea,
		// CorpAppInstance.App3, menu);
		// weixinCorpClientManager.createMenu(CorpInstance.Sagacityidea,
		// CorpAppInstance.App4, menu);
		// weixinCorpClientManager.createMenu(CorpInstance.Sagacityidea,
		// CorpAppInstance.App5, menu);
	}

	@Test
	public void testgetCorpAppInfo() throws WeixinException {
		WeixinCorpClientManager weixinCorpClientManager = WeixinCorpClientFactory.getWeixinCorpClientManager();
		// CorpAppInfo corpAppInfo =
		// weixinCorpClientManager.getCorpAppInfo(CorpInstance.Sagacityidea,
		// CorpAppInstance.App1);
		// DebugUtil.print(corpAppInfo);
	}

	@Test
	public void testgetCorpAppInfos() throws WeixinException {
		WeixinCorpClientManager weixinCorpClientManager = WeixinCorpClientFactory.getWeixinCorpClientManager();
		Collection<CorpAppInfo> appInfos = weixinCorpClientManager.getCorpAppInfos(CorpClientConf.getInstance().getCorp());
		DebugUtil.print(appInfos);
	}

	@Test
	public void testuploadTempMedia() {
		WeixinCorpClientManager weixinCorpClientManager = WeixinCorpClientFactory.getWeixinCorpClientManager();
		try {
			MediaResult result = weixinCorpClientManager.uploadTempMedia(CorpClientConf.getInstance().getCorp(), MediaType.Image, new File("F:/2020.jpg"));
			DebugUtil.print(result);
		} catch (WeixinException e) {
			e.printStackTrace();
		}
	}

	private WeixinCorpClientManager weixinCorpClientManager = WeixinCorpClientFactory.getWeixinCorpClientManager();

	@Test
	public void testdownloadTempMedia() throws WeixinException {
		this.weixinCorpClientManager.downloadTempMedia(CorpClientConf.getInstance().getCorp(), "2pR72hFxy6P9YklrN-JgGel2pFPM5BbzyjsaceBl0dFSmu0mRKUw24y3vRHz9XmcO7aHJYvE0bQPOD8029KW-qg",
				new File("e:/aaa.jpg"));
	}

	@Test
	public void testuploadMPNewsArticle() throws WeixinException {
		this.weixinCorpClientManager.downloadForeverMedia(CorpClientConf.getInstance().getCorp(), CorpClientConf.getInstance().getCorpApp(1),
				"2ZQyuFRuvewj5_9jW6Qt8BsmKNNNTS8JA4ZbUuLgbmxalAy7YHdhGaEmHU71Uj1wMeDaL5HDS-ZANvTx71n7RWw", new File("e:/aaa.jpg"));
	}

	@Test
	public void testuploadForeverMedia() throws WeixinException {
		String mediaID = this.weixinCorpClientManager.uploadForeverMedia(CorpClientConf.getInstance().getCorp(), CorpClientConf.getInstance().getCorpApp(1), MediaType.Image, new File("f:/2020.jpg"));
		System.out.println(mediaID);
	}

	@Test
	public void testuloadmpnewsForeverMedia() throws WeixinException {
		Collection<MPNewsArticle> articles = new ArrayList<MPNewsArticle>();
		String mediaID = this.weixinCorpClientManager.uploadForeverMedia(CorpClientConf.getInstance().getCorp(), CorpClientConf.getInstance().getCorpApp(1), MediaType.Image, new File("f:/2020.jpg"));
		MPNewsArticle article = new MPNewsArticle("呵呵呵", mediaID, null, null, "呵呵呵呵呵呵呵", "和是简介", false);
		articles.add(article);
		this.weixinCorpClientManager.uploadMPNewsArticle(CorpClientConf.getInstance().getCorp(), CorpClientConf.getInstance().getCorpApp(1), articles);
	}

	@Test
	public void testdownloadmpnews() throws WeixinException {
		String mediaID = "2iazhR8urNTCADmj3vthPhrBFSMP0y1rRKyqTP9nolv9-4tnOXHN6qKW86ABd8Qd7";
		Collection<MPNewsArticle> mpNewsArticles = this.weixinCorpClientManager.downloadForeverMedia(CorpClientConf.getInstance().getCorp(), CorpClientConf.getInstance().getCorpApp(1), mediaID, null);
		DebugUtil.print(mpNewsArticles);
	}

	@Test
	public void testgetMediaStat() throws WeixinException {
		MediaStat stat = this.weixinCorpClientManager.getMediaStat(CorpClientConf.getInstance().getCorp(), CorpClientConf.getInstance().getCorpApp(1));
		DebugUtil.print(stat);
	}

	@Test
	public void testsendCorpMsg() {
		Collection<String> userids = new ArrayList<String>();
		userids.add("shipeng");
		CorpMsg4Text corpMsg4Text = new CorpMsg4Text(CorpClientConf.getInstance().getCorpApp(1), false, userids, null, null);
		corpMsg4Text.setContent("投资公司初期注册资本100亿元，由京津冀三省市政府及铁路总公司按照3:3:3:1的比例共同出资成立");
		try {
			this.weixinCorpClientManager.sendCorpMsg(CorpClientConf.getInstance().getCorp(), corpMsg4Text);
		} catch (WeixinException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testSendCorpMsg4Image() {
		try {
			MediaResult result = this.weixinCorpClientManager.uploadTempMedia(CorpClientConf.getInstance().getCorp(), MediaType.Image, new File("d:/ditu.jpg"));
			CorpMsg4Img corpMsg = new CorpMsg4Img(CorpClientConf.getInstance().getCorpApp(1), false);
			corpMsg.setMediaID(result.getMediaID());
			this.weixinCorpClientManager.sendCorpMsg(CorpClientConf.getInstance().getCorp(), corpMsg);
		} catch (WeixinException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testSendCorpMsg4File() {
		try {
			MediaResult result = this.weixinCorpClientManager.uploadTempMedia(CorpClientConf.getInstance().getCorp(), MediaType.File, new File("e:/about云资源汇总指引V7 -  -.pdf"));
			CorpMsg4File corpMsg4File = new CorpMsg4File(CorpClientConf.getInstance().getCorpApp(1), false);
			corpMsg4File.setMediaID(result.getMediaID());
			this.weixinCorpClientManager.sendCorpMsg(CorpClientConf.getInstance().getCorp(), corpMsg4File);
		} catch (WeixinException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testcreateOAuth2RedirectUrl() {
		DefaultXmlConfInitiator initiator = new DefaultXmlConfInitiator();
		initiator.initial();
		try {
			String url = this.weixinCorpClientManager.createOAuth2RedirectUrl(CorpClientConf.getInstance().getCorp(), "http://testcorp.sagacityidea.com/test", "ABC");
			System.out.println(url);
		} catch (WeixinException e) {
			e.printStackTrace();
		}
	}
}
