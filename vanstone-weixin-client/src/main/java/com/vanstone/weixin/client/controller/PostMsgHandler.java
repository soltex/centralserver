/**
 * 
 */
package com.vanstone.weixin.client.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.vanstone.centralserver.common.weixin.wrap.msg.AbstractMsg;

/**
 * @author shipeng
 *
 */
public interface PostMsgHandler {

	/**
	 * 消息处理后处理
	 * @param servletRequest
	 * @param servletResponse
	 * @return
	 */
	AbstractMsg doPostMsgHandler(HttpServletRequest servletRequest, HttpServletResponse servletResponse, AbstractMsg msg);
	
}
