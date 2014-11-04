/**
 * 
 */
package com.vanstone.weixin.client;

import com.google.gson.Gson;
import com.vanstone.centralserver.common.weixin.WeixinException;
import com.vanstone.centralserver.common.weixin.wrap.msg.mass.MassGroupTextMsg;
import com.vanstone.centralserver.common.weixin.wrap.msg.mass.MassResult;

/**
 * @author shipeng
 *
 */
public class MassMsgMainApp {
	
	public static void main(String[] args) {
		IWeixinAPIManager weixinAPIManager = WeixinClientFactory.getWeixinAPIManager();
		MassGroupTextMsg massMsg = new MassGroupTextMsg();
		massMsg.setContent("Sagacityidea服务号-程序测试群发消息.");
		massMsg.setGroupId("0");
		try {
			MassResult massResult = weixinAPIManager.sendMassMsgByGroup("sagacityidea", massMsg);
			Gson gson = new Gson();
			System.out.println(gson.toJson(massResult));
		} catch (WeixinException e) {
			e.printStackTrace();
			System.out.println(e.getErrorCode().getCode() + " -- " + e.getErrorCode().getDesc());
		}
	}
}
