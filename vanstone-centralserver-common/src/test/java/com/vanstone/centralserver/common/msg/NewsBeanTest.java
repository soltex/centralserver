/**
 * 
 */
package com.vanstone.centralserver.common.msg;

import org.junit.Test;

import com.vanstone.centralserver.common.weixin.WeixinException;
import com.vanstone.centralserver.common.weixin.wrap.msg.mass.UploadNewsBean;

/**
 * @author shipeng
 *
 */
public class NewsBeanTest {
	
	@Test
	public void newsBeanTest() throws WeixinException {
		UploadNewsBean bean = new UploadNewsBean();
		bean.addArticle("asdfsdf", "sadfsdf", "sfsdfsdfsdf", "sdfsdfsdfdsfsdfds", "sdfsdfsdf", "sdfsdfsdfsdfdsf", true);
		bean.addArticle("asdfsdf", "sadfsdf", "sfsdfsdfsdf", "sdfsdfsdfdsfsdfds", "sdfsdfsdf", "sdfsdfsdfsdfdsf", true);
		bean.addArticle("asdfsdf", "sadfsdf", "sfsdfsdfsdf", "sdfsdfsdfdsfsdfds", "sdfsdfsdf", "sdfsdfsdfsdfdsf", true);
		bean.addArticle("asdfsdf", "sadfsdf", "sfsdfsdfsdf", "sdfsdfsdfdsfsdfds", "sdfsdfsdf", "sdfsdfsdfsdfdsf", true);
		bean.addArticle("asdfsdf", "sadfsdf", "sfsdfsdfsdf", "sdfsdfsdfdsfsdfds", "sdfsdfsdf", "sdfsdfsdfsdfdsf", true);
		System.out.println(bean.toJson());
	}
	
}
