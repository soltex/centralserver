<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>


<form:form role="form" commandName="authForm" action="/admin/auth/add-admin-action.jhtml" method="post" cssClass="required-validate">
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
		<h4 class="modal-title">添加管理员信息</h4>
	</div>
	<div class="modal-body">
		<div class="form-group">
		  <label for="appid">管理员账号</label>
		  <form:input path="adminName" cssClass="form-control" placeholder="账号名"  required="required"/>
		</div>
		<div class="form-group">
		  <label for="appSecret">密码</label>
		  <form:password path="adminPwd" cssClass="form-control" placeholder="密码"  required="required"/>
		</div>
		<div class="form-group">
		  <label for="appSecret">姓名</label>
		  <form:input path="fullName" cssClass="form-control" placeholder="姓名" />
		</div>
	</div>
	<div class="modal-footer">
		<button type="submit" class="btn btn-primary">保存</button>
	</div>
</form:form>


	