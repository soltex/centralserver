/**
 * Default Weixin Process Servlet
 */
package com.vanstone.weixin.client.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.vanstone.centralserver.common.weixin.wrap.msg.AbstractMsg;
import com.vanstone.weixin.client.IWeixinAPIManager;
import com.vanstone.weixin.client.WeixinClientFactory;

/**
 * @author shipeng
 */
public class DefaultWeixinProcessorServlet extends HttpServlet implements PreMsgHandler, PostMsgHandler {

    private static final long serialVersionUID = 6239495849254928540L;
    private static Log LOG = LogFactory.getLog(DefaultWeixinProcessorServlet.class);
    
    private IWeixinAPIManager weixinAPIManager = WeixinClientFactory.getWeixinAPIManager();
    private MsgHandler handler = new MsgHandler();
    private MsgResolver msgResolver = new DefaultMsgResolver();
    
	@Override
    protected void doGet(HttpServletRequest servletRequest, HttpServletResponse servletResponse) throws ServletException, IOException {
		this.doPost(servletRequest, servletResponse);
	}
	
	@Override
    protected void doPost(HttpServletRequest servletRequest, HttpServletResponse servletResponse) throws ServletException, IOException {
		if (weixinAPIManager.validateWeixin(servletRequest, servletResponse)) {
			LOG.info("Weixin API Validate ECHOSTR OK.");
			return;
		}
		AbstractMsg msg = msgResolver.resolve(servletRequest);
		if (msg == null) {
			return;
		}
		msg = this.doPreMsgHandler(servletRequest, servletResponse, msg);
		handler.eventHandler(msg, servletRequest, servletResponse);
		this.doPostMsgHandler(servletRequest, servletResponse, msg);
	}

	@Override
	public AbstractMsg doPostMsgHandler(HttpServletRequest servletRequest, HttpServletResponse servletResponse,
			AbstractMsg msg) {
		return msg;
	}

	@Override
	public AbstractMsg doPreMsgHandler(HttpServletRequest servletRequest, HttpServletResponse servletResponse,
			AbstractMsg msg) {
		return msg;
	}
	
}
