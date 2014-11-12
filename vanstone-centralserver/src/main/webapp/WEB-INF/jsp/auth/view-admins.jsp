<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>


<div class="row">
	<div class="col-md-12">
		
	</div>
</div>
<p></p>
<div class="panel panel-default">
  <div class="panel-heading">账号管理 <a href="/admin/auth/add-admin.jhtml"  class="btn btn-info"  data-toggle="modal" data-target="#modal-dialog">添加管理员</a> </div>
  <div class="panel-body">
  <table class="table table-hover">
	<thead>
		<tr>
			<th>管理员名称</th>
			<th>密码</th>
			<th>姓名</th>
			<th>写入时间</th>
			<th>最近更新时间</th>
			<th>最后登录时间</th>
			<th>状态</th>
			<th></th>
		</tr>
	</thead>
	<tbody>
		<c:forEach var="model" items="${pageInfo.objects }">
			<tr>
				<td>${model.adminName }</td>
				<td>${model.adminPwd }</td>
				<td>
				<a href="/admin/auth/update-fullname/${model.id }.jhtml"  data-toggle="modal" data-target="#modal-dialog">
				<c:choose>
					<c:when test="${model.fullName != null}">${model.fullName }</c:when>
					<c:otherwise>######</c:otherwise>
				</c:choose>
				</a></td>
				<td><fmt:formatDate value="${model.sysInsertTime }" pattern="yyyy-MM-dd hh:mm:ss" /></td>
				<td><fmt:formatDate value="${model.sysUpdateTime }" pattern="yyyy-MM-dd hh:mm:ss" /></td>
				<td><fmt:formatDate value="${model.lastLoginTime }" pattern="yyyy-MM-dd hh:mm:ss" /></td>
				<td>${model.adminState.desc }</td>
				<td>
					<a href="/admin/auth/reset-password/${model.id }.jhtml" data-todo="ajaxTodo">更换密码</a> | 
					<a href="/admin/auth/delete-admin/${model.id }.jhtml"   title="确认删除？"  data-todo="ajaxTodo">删除</a>
				</td>
			</tr>
		</c:forEach>
	</tbody>
</table>
</div>
</div>
  
