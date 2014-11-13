package com.vanstone.centralserver.adminservice.sdk.services.impl;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.vanstone.business.ObjectDuplicateException;
import com.vanstone.centralserver.business.sdk.adminservice.IAuthServiceMgr;

@ContextConfiguration(locations = { "classpath*:/application-context.xml",
"classpath*:META-INF/vanstone-**-components.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class AuthServiceMgrImplTest {

	@Autowired
	private IAuthServiceMgr authServiceMgr;
	
	@Test
	public void testAddAdmin() {
		try {
			this.authServiceMgr.addAdmin("sdf", "sdf", "呵呵呵呵");
		} catch (ObjectDuplicateException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testUpdateAdminFullName() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdatePassword() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetAdmin() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetAdminByAdminName() {
		fail("Not yet implemented");
	}

	@Test
	public void testLogin() {
		fail("Not yet implemented");
	}

	@Test
	public void testLogout() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetAdmins() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetTotalAdmins() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdateAdminState() {
		fail("Not yet implemented");
	}

	@Test
	public void testDeleteAdmin() {
		fail("Not yet implemented");
	}

}
