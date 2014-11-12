<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>


<ul class="nav nav-tabs" role="tablist">
  <li role="presentation"><a href="/admin/weixin/view-weixininfos.jhtml" data-history>微信配置信息</a></li>
  <li role="presentation" class="active"><a href="#" >增加微信配置信息</a></li>
</ul>

<p></p>

<form:form role="form" commandName="weixinInfoForm" action="/admin/weixin/add-weixininfo-action.jhtml" method="post" cssClass="required-validate">
	<div class="form-group">
	  <label for="id">应用名称(必须为英文!一旦创建完毕后，不允许修改)</label>
	  <form:input path="id" cssClass="form-control" placeholder="应用名称" required="required"/>
	</div>
	<div class="form-group">
	  <label for="appid">微信AppId</label>
	  <form:input path="appid" cssClass="form-control" placeholder="微信AppId"  required="required"/>
	</div>
	<div class="form-group">
	  <label for="appSecret">微信AppSecret</label>
	  <form:input path="appSecret" cssClass="form-control" placeholder="微信AppSecret"  required="required"/>
	</div>
	<div class="form-group">
	  <label for="content">微信应用描述信息</label>
	  <form:textarea path="content" cssClass="form-control" placeholder="描述信息" rows="3" />
	</div>
	<button type="submit" class="btn btn-default">保存</button>
</form:form>


