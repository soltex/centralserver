<%@page
	import="com.vanstone.centralserver.common.util.aes.WXBizMsgCrypt"%><%@page
	import="java.util.Enumeration"%><%@page import="java.util.Map.Entry"%><%@page
	import="java.util.Set"%><%@page import="java.util.Map"%><%@ page
	language="java" contentType="text/html; charset=UTF-8"%>
<%
	String msg_signature = request.getParameter("msg_signature");
	String timestamp = request.getParameter("timestamp");
	String nonce = request.getParameter("nonce");
	String echoStr = request.getParameter("echostr");
	String token = "sagacity";
	String encodingAESKEY = "jWmYm7qr5nMoAUwZRjGtBxmz3KA1tkAj3ykkR6q2B2C";
	try {
		WXBizMsgCrypt msgCrypt = new WXBizMsgCrypt(token, encodingAESKEY, "wx87c2c0c1d9ec9f7a");
		String string = msgCrypt.VerifyURL(msg_signature, timestamp, nonce, echoStr);
		System.out.println(string);
		out.print(string);
	} catch (Exception e) {
		e.printStackTrace();
	}
%>