<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>

<%@ include file="/inc/header.jsp"%>



<ul class="nav nav-tabs" role="tablist">
  <li role="presentation" class="active"><a href="#">微信配置信息</a></li>
  <li role="presentation"><a href="/admin/weixin/add-weixininfo.jhtml">增加微信配置信息</a></li>
</ul>

<p></p>

<div class="row">
	<div class="col-md-12">
		<a href="/admin/weixin/update-all-weixins.jhtml" target="ajax" class=" btn btn-default">强制更新</a> <a href="#" class="btn btn-default">更新选定项</a> <a href="" class="btn btn-danger">删除</a>
	</div>
</div>
<p></p>
<table class="table">
	<thead>
		<tr>
			<th>微信应用描述</th>
			<th>微信ID</th>
			<th>AppID</th>
			<th>AppSecret</th>
			<th>入库时间</th>
			<th>更新时间</th>
			<th>AccessToken时间</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach var="model" items="${pageInfo.objects }">
			<tr>
				<td>${model.content }</td>
				<td>${model.id }</td>
				<td>${model.appid }</td>
				<td>${model.appSecret }</td>
				<td><fmt:formatDate value="${model.sysInsertTime }" pattern="yyyy-MM-dd hh:mm:ss" /></td>
				<td><fmt:formatDate value="${model.sysUpdateTime }" pattern="yyyy-MM-dd hh:mm:ss" /></td>
				<td><fmt:formatDate value="${model.lastRetrievalTokenTime }" pattern="yyyy-MM-dd hh:mm:ss" /></td>
			</tr>
			<tr class="danger">
				<td colspan="7"><span class="badge badge-danger" >Weixin Access Token　　</span>${model.accessToken }</td>
			</tr>
		</c:forEach>
	</tbody>
</table>


<%@ include file="/inc/footer.jsp"%>