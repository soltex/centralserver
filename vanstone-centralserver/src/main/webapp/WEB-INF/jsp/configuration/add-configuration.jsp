<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>

<%@ include file="/inc/header.jsp"%>


<ul class="nav nav-tabs" role="tablist">
  <li role="presentation"><a href="/admin/configuration/view-configurations.jhtml">配置信息</a></li>
  <li role="presentation" class="active"><a href="#">增加配置信息</a></li>
</ul>

<p></p>

<div class="row">
	<div class="col-md-12">
		<form:form action="/admin/configuration/add-configuration-action.jhtml" method="post" commandName="confForm">
			<div class="form-group">
				 <label for="groupId">GROUPID</label>
				<form:input path="groupId" cssClass="form-control" placeholder="groupId" />
			</div>
			<div class="form-group">
				 <label for="dataId">DATAID</label>
				<form:input path="dataId" cssClass="form-control" placeholder="dataId" />
			</div>
			<div class="form-group">
				 <label for="value">值</label>
				<form:textarea path="value" cssClass="form-control" rows="3" placeholder="配置信息值"></form:textarea>
			</div>
			<button type="submit" class="btn btn-default">保存配置信息</button>
		</form:form>
	</div>
</div>



<%@ include file="/inc/footer.jsp"%>