/**
 * 
 */
package com.vanstone.weixin.client;

import java.io.File;

import com.google.gson.Gson;
import com.vanstone.centralserver.common.weixin.WeixinException;
import com.vanstone.centralserver.common.weixin.wrap.MediaType;
import com.vanstone.centralserver.common.weixin.wrap.msg.mass.UploadBeanResult;
import com.vanstone.centralserver.common.weixin.wrap.msg.mass.UploadNewsBean;

/**
 * @author shipeng
 *
 */
public class UploadMainApp {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		IWeixinAPIManager weixinAPIManager = WeixinClientFactory.getWeixinAPIManager();
		try {
			String mediaId = weixinAPIManager.uploadMedia("sagacityidea", new File("D:/var/aiyutian/tmp/1FAUDYpbBgReKSGAhQn5B.jpg"), MediaType.Image);
			UploadNewsBean bean = new UploadNewsBean();
			bean.addArticle(mediaId, "呵呵呵呵呵", "哈哈哈哈哈", "http://www.baidu.com", "呵呵呵呵呵", "啊啊啊啊", false);
			UploadBeanResult result = weixinAPIManager.uploadNewsBean("sagacityidea", bean);
			Gson gson = new Gson();
			System.out.println(gson.toJson(result));
		} catch (WeixinException e1) {
			System.out.println(e1.getErrorCode().getCode() + " -- " + e1.getErrorCode().getDesc());
			e1.printStackTrace();
		}
		
	}
}
