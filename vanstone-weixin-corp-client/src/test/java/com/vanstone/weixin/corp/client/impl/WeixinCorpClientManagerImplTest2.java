package com.vanstone.weixin.corp.client.impl;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.vanstone.centralserver.common.corp.user.UserCollectionWithTag;
import com.vanstone.centralserver.common.util.DebugUtil;
import com.vanstone.centralserver.common.weixin.WeixinException;
import com.vanstone.weixin.corp.client.WeixinCorpClientFactory;
import com.vanstone.weixin.corp.client.WeixinCorpClientManager;
import com.vanstone.weixin.corp.client.conf.xml.DefaultXmlConfInitiator;

public class WeixinCorpClientManagerImplTest2 {
	
	private WeixinCorpClientManager weixinCorpClientManager = WeixinCorpClientFactory.getWeixinCorpClientManager();
	
	@Before
	public void init() {
		DefaultXmlConfInitiator initiator = new DefaultXmlConfInitiator();
		initiator.initial();
	}
	
	@Test
	public void testGetAccessToken() {
		fail("Not yet implemented");
	}

	@Test
	public void testCreateMenu() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetCorpAppInfo() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdateCorpAppInfo() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetCorpAppInfos() {
		fail("Not yet implemented");
	}

	@Test
	public void testUploadTempMedia() {
		fail("Not yet implemented");
	}

	@Test
	public void testDownloadTempMedia() {
		fail("Not yet implemented");
	}

	@Test
	public void testUploadMPNewsArticle() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdateMPNewsArticle() {
		fail("Not yet implemented");
	}

	@Test
	public void testUploadForeverMedia() {
		fail("Not yet implemented");
	}

	@Test
	public void testDownloadForeverMedia() {
		fail("Not yet implemented");
	}

	@Test
	public void testDeleteForeverMedia() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetMediaStat() {
		fail("Not yet implemented");
	}

	@Test
	public void testSendCorpMsg() {
		fail("Not yet implemented");
	}

	@Test
	public void testSendCorpReply() {
		fail("Not yet implemented");
	}

	@Test
	public void testCreateOAuth2RedirectUrl() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetRedirectResult() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetUserInfo() {
		fail("Not yet implemented");
	}

	@Test
	public void testAuthSuccess() {
		fail("Not yet implemented");
	}

	@Test
	public void testCreateDepartmentStringIntIntegerInteger() {
		fail("Not yet implemented");
	}

	@Test
	public void testCreateDepartmentString() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdateDepartment() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpateDepartment() {
		fail("Not yet implemented");
	}

	@Test
	public void testDeleteDepartment() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetDepartmentsInt() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetDepartments() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetCorpUserInfo() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddCorpUserInfo() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdateCorpUserInfo() {
		fail("Not yet implemented");
	}

	@Test
	public void testDeleteCorpUser() {
		fail("Not yet implemented");
	}

	@Test
	public void testBatchDeleteCorpUsers() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetSimpleListCorpUsers() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetFullListCorpUsers() {
		fail("Not yet implemented");
	}

	@Test
	public void testSendInvite() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddTag() {
		try {
			int tagid = this.weixinCorpClientManager.addTag("呵呵呵，测试一下标签123", null);
			System.out.println(tagid);
		} catch (WeixinException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testUpdateTag() {
		try {
			this.weixinCorpClientManager.updateTag(1, "你说呢");
		} catch (WeixinException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testDeleteTag() {
		try {
			this.weixinCorpClientManager.deleteTag(1);
			this.weixinCorpClientManager.deleteTag(2);
		} catch (WeixinException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGetUserCollectionWithTag() {
		try {
			UserCollectionWithTag userCollectionWithTag = this.weixinCorpClientManager.getUserCollectionWithTag(1);
			DebugUtil.print(userCollectionWithTag);
		} catch (WeixinException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testAddTagRelUsers() {
		List<String> userids = new ArrayList<String>();
		userids.add("liyajun");
		try {
			this.weixinCorpClientManager.addTagRelUsers(1, userids, null);
		} catch (WeixinException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testDeleteTagRelUsers() {
		List<String> userids = new ArrayList<String>();
		userids.add("liyajun");
		try {
			this.weixinCorpClientManager.deleteTagRelUsers(1, userids, null);
		} catch (WeixinException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testGetTags() {
		try {
			UserCollectionWithTag userCollectionWithTag = this.weixinCorpClientManager.getUserCollectionWithTag(1);
			DebugUtil.print(userCollectionWithTag);
		} catch (WeixinException e) {
			e.printStackTrace();
		}
	}
}
