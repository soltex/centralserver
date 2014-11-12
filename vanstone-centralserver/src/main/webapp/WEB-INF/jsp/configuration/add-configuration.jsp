<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>


<p></p>
<h3>配置管理器</h3>
<div class="row">
	<div class="col-md-2">
		<div class="list-group">
		  <a href="/admin/configuration/view-configurations.jhtml" data-history class="list-group-item ">配置信息</a>
		  <a href="/admin/configuration/add-configuration.jhtml" data-history class="list-group-item active">增加配置信息</a>
		  <a href="/admin/configuration/clear-all-configurations.jhtml" data-todo="ajaxTodo" class="list-group-item "  title="确认清除全部配置信息？">清除全部配置信息</a>
		</div>
	</div>
	<div class="col-md-10">
		<form:form action="/admin/configuration/add-configuration-action.jhtml" method="post" commandName="confForm" cssClass="required-validate">
			<div class="form-group">
				 <label for="groupId">GROUPID</label>
				<form:input path="groupId" cssClass="form-control" placeholder="groupId"  />
			</div>
			<div class="form-group">
				 <label for="dataId">DATAID</label>
				<form:input path="dataId" cssClass="form-control" placeholder="dataId"  required="required" />
			</div>
			<div class="form-group">
				 <label for="value">值</label>
				<form:textarea path="value" cssClass="form-control" rows="3" placeholder="配置信息值"  required="required" ></form:textarea>
			</div>
			<button type="submit" class="btn btn-default">保存配置信息</button>
		</form:form>
	</div>
</div>
