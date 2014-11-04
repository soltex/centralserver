/**
 * 
 */
package com.vanstone.weixin.client;

import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Test;

import com.vanstone.centralserver.common.CommonDateUtil;
import com.vanstone.centralserver.common.weixin.WeixinException;
import com.vanstone.centralserver.common.weixin.wrap.Language;
import com.vanstone.centralserver.common.weixin.wrap.msg.template.TemplateBodyData;
import com.vanstone.centralserver.common.weixin.wrap.msg.template.ValueAndColor;
import com.vanstone.centralserver.common.weixin.wrap.user.UserOpenIdCollection;
import com.vanstone.centralserver.common.weixin.wrap.user.UserWeixinBaseInfo;

/**
 * @author shipeng
 */
public class SendTemplateMsgTest {

	@Test
	public void testsendTemplateMsg() throws WeixinException {
		String appname = "jiujuyayuan";
		IWeixinAPIManager weixinAPIManager = WeixinClientFactory.getWeixinAPIManager();
		UserOpenIdCollection userOpenIdCollection = weixinAPIManager.getUserOpenIdCollection(appname, null);
		Collection<String> openids = userOpenIdCollection.getOpenids();
		Map<String, String> values = new LinkedHashMap<String, String>();
		for (String openid : openids) {
			UserWeixinBaseInfo info = weixinAPIManager.getUserWeixinBaseInfo(appname, openid, Language.zh_CN);
			values.put(info.getOpenid(), info.getNickname());
		}
		for (Map.Entry<String, String> entry : values.entrySet()) {
			System.out.println(entry.getKey() + "\t" + entry.getValue());
		}
		weixinAPIManager.close();
	}
	
	
	@Test
	public void testSendTemplateMsgByOpenid() throws WeixinException {
		String openid = "oE9mFuDKRV09gLRjZL5a6UkIB4UY";
		String appname = "jiujuyayuan";
		ValueAndColor firstValueColor = new ValueAndColor("手机号验证结果通知");
		TemplateBodyData templateBodyData = new TemplateBodyData(openid, "MGGIW5oxkT7HxuyKc__Ar8EYV3Gb3SRf2lZmjKsdLNw", "http://www.innodev.com.cn", null, firstValueColor, null, null, false);
		
		templateBodyData.addParam("service", "久居雅园社区手机号验证通知", null);
		templateBodyData.addParam("detail", "呵呵呵呵呵呵");
		templateBodyData.addParam("time", CommonDateUtil.date2String(new Date(), "yyyy-MM-dd HH:mm:ss"));
		templateBodyData.addParam("location", "业主手机号更变为 13426173105");
		templateBodyData.addParam("reason", "业主主动更变手机号");
		
		IWeixinAPIManager weixinAPIManager = WeixinClientFactory.getWeixinAPIManager();
		
		System.out.println(templateBodyData.toJson());
		
		weixinAPIManager.sendTemplateMsg(appname, templateBodyData);
		weixinAPIManager.close();
	}
}
