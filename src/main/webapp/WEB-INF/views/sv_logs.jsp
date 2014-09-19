<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<jsp:include page="include/header.jsp" />

<div class="limit-width  center-block">
	<div class="page-header">
		<h1>${pageTitle }</h1>
	</div>
</div>

<div class="limit-width  center-block">

	<table class="table  table-striped">
	<thead>
		<tr>
			<th>No</th>
			<th>ID</th>
			<th>성명</th>
			<th>접속 일시</th>
			<th>접속 IP</th>
			<th>수행 업무</th>
		</tr>
	</thead>
	<tbody>
		<c:set var="index" value="${fn:length(logs) }"/>
		<c:forEach items="${logs }" var="log">
		<tr>
			<td>${index}</td>
			<td>${log.username }</td>
			<td>${log.name }</td>
			<td>${log.datetime }</td>
			<td>${log.ip }</td>
			<td>${log.content}</td>
		</tr>
		<c:set var="index" value="${index-1}"/>
		</c:forEach>
	</tbody>
	</table>

</div>
<jsp:include page="include/footer.jsp" />


