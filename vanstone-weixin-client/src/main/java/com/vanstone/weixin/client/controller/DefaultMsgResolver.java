/**
 * 
 */
package com.vanstone.weixin.client.controller;

import javax.servlet.http.HttpServletRequest;

import com.vanstone.centralserver.common.MyAssert;
import com.vanstone.centralserver.common.ServletUtil;
import com.vanstone.centralserver.common.weixin.wrap.msg.AbstractMsg;
import com.vanstone.centralserver.common.weixin.wrap.msg.MessageHelper;

/**
 * @author shipeng
 *
 */
public class DefaultMsgResolver implements MsgResolver {

	/* (non-Javadoc)
	 * @see com.vanstone.weixin.client.controller.MsgResolver#resolve(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public AbstractMsg resolve(HttpServletRequest servletRequest) {
		MyAssert.notNull(servletRequest);
		String requestBody = ServletUtil.readRequestBody(servletRequest);
		return MessageHelper.parseMsg(requestBody);
	}

}
