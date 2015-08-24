/**
 * 
 */
package com.vanstone.weixin.corp.client.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.DocumentException;

import com.vanstone.centralserver.common.corp.ICorpApp;
import com.vanstone.centralserver.common.util.aes.AesException;
import com.vanstone.centralserver.common.util.aes.WXBizMsgCrypt;
import com.vanstone.weixin.corp.client.conf.CorpClientConf;

/**
 * @author shipeng
 * 企业号servlet
 */
public abstract class AbstractCorpWeixinServlet extends HttpServlet {

	private static final long serialVersionUID = -6487295452263362481L;

	private ListenerSelector listenerSelector = new ListenerSelector();
	
	/**
	 * 指定当前的CorpApp是什么?
	 * @return
	 */
	public abstract ICorpApp getCorpApp();
	
	@Override
	protected void doPost(HttpServletRequest servletRequest, HttpServletResponse servletResponse) throws ServletException, IOException {
		
		//首次验证
		if (__is_valiate_echostring(servletRequest, servletResponse)) {
			__validate_echostring(servletRequest, servletResponse);
			return;
		}
		//监听器选择
		try {
			this.listenerSelector.execute(this.getCorpApp(), servletRequest, servletResponse);
		} catch (AesException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}
	
	private boolean __is_valiate_echostring(HttpServletRequest servletRequest, HttpServletResponse servletResponse) {
		String echostring = servletRequest.getParameter("echostr");
		if(echostring == null || echostring.equals("")) {
			return false;
		}
		return true;
	}
	
	private void __validate_echostring(HttpServletRequest servletRequest, HttpServletResponse servletResponse) {
		String msg_signature = servletRequest.getParameter("msg_signature");
		String timestamp = servletRequest.getParameter("timestamp");
		String nonce = servletRequest.getParameter("nonce");
		String echoStr = servletRequest.getParameter("echostr");
		try {
			WXBizMsgCrypt wxBizMsgCrypt = new WXBizMsgCrypt(this.getCorpApp().getToken(), this.getCorpApp().getEncodingAESKey(), CorpClientConf.getInstance().getCorp().getAppID());
			String string = wxBizMsgCrypt.VerifyURL(msg_signature, timestamp, nonce, echoStr);
			servletResponse.getWriter().print(string);
		} catch (AesException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doPost(req, resp);
	}
}
