<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>

<%@ include file="/inc/header.jsp"%>

<ul class="nav nav-tabs" role="tablist">
	<li role="presentation" class="active"><a href="#">配置信息</a></li>
	<li role="presentation"><a href="/admin/configuration/add-configuration.jhtml">增加配置信息</a></li>
</ul>

<p></p>
<div class="row">
	<div class="col-md-12">
		<a href="/admin/configuration/clear-all-configurations.html" class="btn btn-danger">清除全部配置信息</a>
	</div>
</div>
<p></p>
<div class="row">
	<div class="col-md-12">
		<form:form action="/admin/configuration/view-configurations.html" method="get" modelAttribute="confForm">
			<div class="form-group">
				<form:input path="key" cssClass="form-control" placeholder="关键字" />
			</div>
			<button type="submit" class="btn btn-default">检索</button>
		</form:form>
	</div>
</div>

<div class="row">
	<div class="col-md-12">
		<table class="table">
			<thead>
				<tr>
					<th>Id</th>
					<th>GroupId</th>
					<th>DataId</th>
					<th>值</th>
					<th>系统写入时间</th>
					<th>系统更新时间</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="model" items="${pageInfo.objects }">
					<tr>
						<td>${model.id }</td>
						<td>${model.groupId }</td>
						<td>${model.dataId }</td>
						<td>${model.confValue }</td> 
						<td><fmt:formatDate value="${model.sysInsertTime }" pattern="yyyy-MM-dd hh:mm:ss"/></td>
						<td><fmt:formatDate value="${model.sysUpdateTime }" pattern="yyyy-MM-dd hh:mm:ss"/></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<ul class="pagination">
			<li><a href="?p=1&key=${confForm.key }">1</a></li>
		    <li><a href="?p=${pageInfo.prePageNo }&key=${confForm.key } ">&laquo;</a></li>
		    <c:forEach var="i" begin="${pageInfo.rangeOfFirst }" end="${pageInfo.rangeOfEnd }" varStatus="status">
		    	<c:choose>
		    		<c:when test="${pageInfo.pageNo == status.index }">
		    			<li class="active"><span>${pageInfo.pageNo } <span class="sr-only">(current)</span></span></li>
		    		</c:when>
		    		<c:otherwise>
		    			<li><a href="?p=${i }&key=${confForm.key }">${i }</a></li>
		    		</c:otherwise>
		    	</c:choose>
		    </c:forEach>
		    <li><a href="?p=${pageInfo.rangeOfEnd }&key=${confForm.key }">&raquo;</a></li>
		    <li><a href="?p=${pageInfo.pages }&key=${confForm.key }">${pageInfo.pages }</a></li>
		</ul>
	</div>
</div>



<%@ include file="/inc/footer.jsp"%>