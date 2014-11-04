/**
 * 
 */
package com.vanstone.weixin.client.controller;

import javax.servlet.http.HttpServletRequest;

import com.vanstone.centralserver.common.weixin.wrap.msg.AbstractMsg;

/**
 * @author shipeng
 *
 */
public interface MsgResolver {
	
	/**
	 * 解析Msg
	 * @param servletRequest
	 * @return
	 */
	AbstractMsg resolve(HttpServletRequest servletRequest);
	
}
