<%@page import="com.vanstone.centralserver.common.util.DebugUtil"%>
<%@page import="com.vanstone.centralserver.common.corp.js.TicketObject"%>
<%@page import="com.vanstone.weixin.corp.client.WeixinCorpClientFactory"%>
<%@page import="com.vanstone.weixin.corp.client.WeixinCorpClientManager"%>
<%@page import="java.util.Enumeration"%>
<%@page import="java.util.Map.Entry"%>
<%@page import="java.util.Set"%>
<%@page import="java.util.Map"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>

<%
	//String msg_signature = request.getParameter("msg_signature");
	//String timestamp = request.getParameter("timestamp");
	//String nonce = request.getParameter("nonce");
	//String echoStr = request.getParameter("echostr");
	
	//System.out.println(msg_signature + timestamp + nonce + echoStr);
	
	WeixinCorpClientManager weixinCorpClientManager = WeixinCorpClientFactory.getWeixinCorpClientManager();
	TicketObject object = weixinCorpClientManager.initialJSAPISignature(request);
	DebugUtil.print(object);
%>