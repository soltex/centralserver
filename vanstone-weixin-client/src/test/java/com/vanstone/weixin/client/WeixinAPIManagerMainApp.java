/**
 * 
 */
package com.vanstone.weixin.client;

import java.io.File;
import java.util.Collection;
import java.util.Scanner;

import com.vanstone.centralserver.common.JsonUtil;
import com.vanstone.centralserver.common.weixin.WeixinException;
import com.vanstone.centralserver.common.weixin.wrap.Language;
import com.vanstone.centralserver.common.weixin.wrap.MediaType;
import com.vanstone.centralserver.common.weixin.wrap.msg.CCMsg4Music;
import com.vanstone.centralserver.common.weixin.wrap.msg.CCMsg4Voice;
import com.vanstone.centralserver.common.weixin.wrap.user.UserGroupInfo;
import com.vanstone.centralserver.common.weixin.wrap.user.UserOpenIdCollection;
import com.vanstone.centralserver.common.weixin.wrap.user.UserWeixinBaseInfo;

/**
 * @author shipeng
 */
public class WeixinAPIManagerMainApp {
	
	private static IWeixinAPIManager weixinAPIManager = WeixinClientFactory.getWeixinAPIManager();
	
	private static String MY_OPENID = "oE9mFuDKRV09gLRjZL5a6UkIB4UY";
	
	private static final String RENSHENGUO_OPENID = "oE9mFuFYvxRJFECVkuD_qjsaS4FY";
	
	private static final String THINK_OPENID = "oE9mFuKtGtma_eMvuP387OBkH-3I";
	
	public static void main(String[] args) {
		
		System.out.println("===============================================================================\n");
		System.out.println("Please input command : ");
		System.out.println("===============================================================================");
		Scanner scanner = new Scanner(System.in);
		String tmp = null;
		while ((tmp = scanner.next()) != null) {
			if (tmp.equals("exit")) {
				System.exit(0);
				return;
			}
			if (tmp.equalsIgnoreCase("uploadMediaImage")) {
				uploadMediaid();
				continue;
			}
			if (tmp.equalsIgnoreCase("getUserGroupInfos")) {
				getUserGroupIds();
				continue;
			}
			if (tmp.equalsIgnoreCase("createUserGroupInfo")) {
				createUserGroupInfo();
				continue;
			}
			if(tmp.equalsIgnoreCase("updateUserGroupInfo")) {
				updateUserGroupInfo();
				continue;
			}
			if (tmp.equals("getUserInfos")) {
				getUserInfos();
				continue;
			}
			if (tmp.equals("sendCCMsg4Voice")) {
				sendCCMsg4Voice();
				continue;
			}
			if (tmp.equals("sendCCMsg4Music")) {
				sendCCMsg4Music();
				continue;
			}
			if(tmp.equalsIgnoreCase("tree")) {
				continue;
			}
		}
	}
	
	private static void uploadMediaid() {
		File[] files = new File("/var/aiyutian/upload").listFiles();
		for (File file : files) {
			try {
				System.out.println(weixinAPIManager.uploadMedia("sagacityidea", file, MediaType.Image));
			} catch (WeixinException e) {
				e.printStackTrace();
			}
		}
	}
	
	private static void getUserGroupIds() {
		try {
	        Collection<UserGroupInfo> userGroupInfos = weixinAPIManager.getUserGroupInfos("sagacityidea");
	        for (UserGroupInfo info : userGroupInfos) {
	        	System.out.println(JsonUtil.object2PrettyString(info,true));
	        }
        } catch (WeixinException e) {
	        e.printStackTrace();
        }
		
	}
	
	private static void createUserGroupInfo() {
		try {
	        weixinAPIManager.createUserGroupInfo("sagacityidea", "呵呵呵呵呵");
        } catch (WeixinException e) {
	        e.printStackTrace();
        }
	}
	
	public static void updateUserGroupInfo() {
		try {
	        weixinAPIManager.updateUserGroupInfo("sagacityidea", 101, "测试更新分组名称");
        } catch (WeixinException e) {
	        e.printStackTrace();
        }
	}
	
	private static void getUserInfos() {
		try {
			UserOpenIdCollection collection = weixinAPIManager.getUserOpenIdCollection("sagacityidea", null);
			for (String openId : collection.getOpenids()) {
				UserWeixinBaseInfo info = weixinAPIManager.getUserWeixinBaseInfo("sagacityidea", openId, Language.zh_CN);
				System.out.println(JsonUtil.object2PrettyString(info,true));
			}
		} catch (WeixinException e) {
			e.printStackTrace();
		}
	}
	
	private static void sendCCMsg4Voice() {
		String media = "F:/丢了幸福的猪 - 铃声.mp3";
		try {
			String mediaId = weixinAPIManager.uploadMedia("sagacityidea", new File(media), MediaType.Voice);
			CCMsg4Voice ccMsg4Voice = new CCMsg4Voice();
			ccMsg4Voice.setMediaId(mediaId);
			ccMsg4Voice.setTouser(RENSHENGUO_OPENID);
			weixinAPIManager.sendCCMsgVoice("sagacityidea", ccMsg4Voice);
			
			ccMsg4Voice.setTouser(THINK_OPENID);
			weixinAPIManager.sendCCMsgVoice("sagacityidea", ccMsg4Voice);
			
			ccMsg4Voice.setTouser(MY_OPENID);
			weixinAPIManager.sendCCMsgVoice("sagacityidea", ccMsg4Voice);
			
			System.out.println(mediaId);
		} catch (WeixinException e) {
			e.printStackTrace();
		}
	}
	
	private static void sendCCMsg4Music() {
		try {
			CCMsg4Music music = new CCMsg4Music();
			music.setDescription("江南Style");
			music.setMusicurl("http://115.28.215.31:18004/jiangnanstyle.mp3");
			music.setHqmusicurl("http://115.28.215.31:18004/jiangnanstyle.mp3");
			String mediaId = weixinAPIManager.uploadMedia("sagacityidea", new File("/var/aiyutian/a.jpg"), MediaType.Image);
			music.setThumbMediaId(mediaId);
			music.setTitle("娃哈哈哈哈哈");
			music.setTouser(RENSHENGUO_OPENID);
			weixinAPIManager.sendCCMsgMusic("sagacityidea", music);
			
			music.setTouser(MY_OPENID);
			weixinAPIManager.sendCCMsgMusic("sagacityidea", music);
			
			music.setTouser(THINK_OPENID);
			weixinAPIManager.sendCCMsgMusic("sagacityidea", music);
			
		} catch (WeixinException e) {
			e.printStackTrace();
		}
		
	}
}
