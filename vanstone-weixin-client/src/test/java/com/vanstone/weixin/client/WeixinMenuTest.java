/**
 * 
 */
package com.vanstone.weixin.client;

import org.junit.Test;

import com.vanstone.centralserver.common.weixin.WeixinException;
import com.vanstone.centralserver.common.weixin.wrap.ButtonType;
import com.vanstone.centralserver.common.weixin.wrap.menu.Menu;
import com.vanstone.centralserver.common.weixin.wrap.menu.MenuItem;
import com.vanstone.centralserver.common.weixin.wrap.oauth2.Scope;

/**
 * @author shipeng
 */
public class WeixinMenuTest {

	@Test
	public void testCreateMenu() {
		
		String appname = "jiujuyayuan";
		IWeixinAPIManager weixinApiManager = WeixinClientFactory.getWeixinAPIManager();
		
		try {
			Menu weixinMenu = new Menu();
			
			//社区菜单
			MenuItem communityMenuItem = new MenuItem("社区");
			
			MenuItem communityWallItem = new MenuItem(ButtonType.Click, "社区活动墙", Constants1.Weixin_Event_Define.CLICK_COMMUNITY_WALL_EVENT.getCode());
			communityMenuItem.addSubMenuItem(communityWallItem);
			
			MenuItem aboutusItem = new MenuItem(ButtonType.Click, "关于我们",  Constants1.Weixin_Event_Define.CLICK_ABOUTUS_EVENT.getCode());
			communityMenuItem.addSubMenuItem(aboutusItem);
			
			String staffsUrl  = weixinApiManager.getOAuth2Url(appname, "http://weixin-service.c-cap.com.cn/jiujuyayuan/our-staffs.jhtml", Scope.snsapi_base, null);
			System.out.println(staffsUrl);
			MenuItem ourStaffItem = new MenuItem(ButtonType.View, "我们的员工",  staffsUrl);
			communityMenuItem.addSubMenuItem(ourStaffItem);
			
			weixinMenu.addMenuItem(communityMenuItem);
			
			//我的账户
			MenuItem myAccountMenuItem = new MenuItem("我的账户");
			
			MenuItem updatePasswordItem = new MenuItem(ButtonType.View, "修改密码", Constants1.WEIXIN_SERVER_DOMAIN_NAME + "/" + appname + "/my/update-password.jhtml");
			myAccountMenuItem.addSubMenuItem(updatePasswordItem);
			
			MenuItem mobileAuthItem = new MenuItem(ButtonType.View, "手机认证", Constants1.WEIXIN_SERVER_DOMAIN_NAME  + "/" + appname + "/my/mobile-authentication.jhtml");
			myAccountMenuItem.addSubMenuItem(mobileAuthItem);
			
			MenuItem roomAuthItem = new MenuItem(ButtonType.View, "房间认证", Constants1.WEIXIN_SERVER_DOMAIN_NAME  + "/" + appname + "/my/room-authentication.jhtml");
			myAccountMenuItem.addSubMenuItem(roomAuthItem);
			
			MenuItem updateProfileItem = new MenuItem(ButtonType.View, "个人信息修改", Constants1.WEIXIN_SERVER_DOMAIN_NAME  +  "/" + appname + "/my/update-profile.jhtml");
			myAccountMenuItem.addSubMenuItem(updateProfileItem);
			
			weixinMenu.addMenuItem(myAccountMenuItem);
			
			//服务
			MenuItem serviceMenuItem = new MenuItem("服务");
			
			MenuItem announcementItem = new MenuItem(ButtonType.View, "社区公告", Constants1.WEIXIN_SERVER_DOMAIN_NAME  + "/" + appname + "/view-announcements.jhtml");
			serviceMenuItem.addSubMenuItem(announcementItem);
			
			MenuItem accessorQRItem = new MenuItem(ButtonType.Click, "申请访客二维码", Constants1.Weixin_Event_Define.CLICK_ACCESSOR_QR_EVENT.getCode());
			serviceMenuItem.addSubMenuItem(accessorQRItem);
			
			MenuItem applyRepairReportItem = new MenuItem(ButtonType.Click, "申请报修单", Constants1.Weixin_Event_Define.CLICK_APPLY_REPAIR_REPORT_EVENT.getCode());
			serviceMenuItem.addSubMenuItem(applyRepairReportItem);
			
			MenuItem qaItem = new MenuItem(ButtonType.View, "常用知识", Constants1.WEIXIN_SERVER_DOMAIN_NAME + "/" + appname + "/view-qas.jhtml");
			serviceMenuItem.addSubMenuItem(qaItem);
			
			weixinMenu.addMenuItem(serviceMenuItem);
			
			System.out.println(weixinMenu.toJson());
			//weixinApiManager.createWeixinMenu(Constants1.WEIXIN_SERVICE_NUM, weixinMenu);
		} catch (WeixinException e) {
			e.printStackTrace();
		}
	}
}
