package com.vanstone.centralserver.configuration.sdk.services.impl;

import static org.junit.Assert.fail;

import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.vanstone.business.ObjectDuplicateException;
import com.vanstone.centralserver.business.sdk.configuration.IConfigurationServiceMgr;

@ContextConfiguration(locations = { "classpath*:/application-context.xml",
"classpath*:META-INF/vanstone-**-components.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class ConfigurationServiceMgrImplTest {
	
	@Autowired
	private IConfigurationServiceMgr configurationServiceMgr;
	
	@Test
	public void testGetValue() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetIntValue() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetDoubleValue() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetFloatValue() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddConf() {
		for (int i=0;i<20;i++) {
			for (int n=0;n<5;n++) {
				try {
					this.configurationServiceMgr.addConf("weedfs" + i, UUID.randomUUID() + "_weedfs.nginx.address" + n, "http://img.vanstone.dev:9333");
				} catch (ObjectDuplicateException e) {
					e.printStackTrace();
				}
			}
		}
		this.configurationServiceMgr.close();
	}
	
	@Test
	public void testUpdateConf() {
		try {
			this.configurationServiceMgr.updateConf(1, "weedfs", "weedfs.address", "哇哈哈哈哈");
		} catch (ObjectDuplicateException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testDeleteConf() {
		this.configurationServiceMgr.deleteConf("weedfs", "weedfs.address");
		this.configurationServiceMgr.deleteConf("weedfs", "weedfs.nginx.address");
	}
	
	@Test
	public void testGetGroups() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetConfsByGroupId() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetConfs() {
		fail("Not yet implemented");
	}

	@Test
	public void testRefreshAllConfs() {
		fail("Not yet implemented");
	}

	@Test
	public void testRefreshConf() {
		fail("Not yet implemented");
	}

	@Test
	public void testClose() {
		fail("Not yet implemented");
	}

}
