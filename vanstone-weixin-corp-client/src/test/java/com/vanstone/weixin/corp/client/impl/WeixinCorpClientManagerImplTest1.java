package com.vanstone.weixin.corp.client.impl;

import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.vanstone.centralserver.common.corp.user.CorpUserInfo;
import com.vanstone.centralserver.common.util.DebugUtil;
import com.vanstone.centralserver.common.weixin.WeixinException;
import com.vanstone.centralserver.common.weixin.wrap.Sex;
import com.vanstone.weixin.corp.client.WeixinCorpClientFactory;
import com.vanstone.weixin.corp.client.WeixinCorpClientManager;
import com.vanstone.weixin.corp.client.conf.xml.DefaultXmlConfInitiator;

public class WeixinCorpClientManagerImplTest1 {

	private WeixinCorpClientManager weixinCorpClientManager = WeixinCorpClientFactory.getWeixinCorpClientManager();
	
	@Before
	public void init() {
		DefaultXmlConfInitiator initiator = new DefaultXmlConfInitiator();
		initiator.initial();
	}
	
	@Test
	public void testAuthSuccess() {
		
	}

	@Test
	public void testCreateDepartmentStringIntIntegerInteger() {
		try {
			weixinCorpClientManager.createDepartment("呵呵呵");
		} catch (WeixinException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testCreateDepartmentString() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdateDepartment() {
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
		try {
			this.weixinCorpClientManager.addCorpUserInfo("liyajun", "李亚军", 1, "测试管理", null, null, "liyajun198750", Sex.Male, null, null);
		} catch (WeixinException e) {
			e.printStackTrace();
		}
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
		try {
			List<CorpUserInfo> userInfos = this.weixinCorpClientManager.getSimpleListCorpUsers(1, true, true, false, false, false);
			DebugUtil.print(userInfos);
		} catch (WeixinException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testGetFullListCorpUsers() {
		fail("Not yet implemented");
	}

	@Test
	public void testSendInvite() {
		fail("Not yet implemented");
	}

}
