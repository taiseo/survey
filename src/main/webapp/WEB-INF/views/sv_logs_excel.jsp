<%@ page language="java"
	contentType="application/vnd.ms-excel; charset=UTF-8"
	pageEncoding="UTF-8"%><%@ taglib
	uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib
	uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%><%@ taglib
	uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	response.setHeader("Content-Disposition",
			"attachment; filename=sv_logs.xls");
	response.setHeader("Content-Description", "JSP Generated Data");
%><!DOCTYPE table PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
</head>

<table class="table table-striped" cellpadding="5" cellspacing="0"
	border="1">
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
		<c:set var="index" value="${fn:length(logs) }" />
		<c:forEach items="${pagedListHolder.pageList}" var="log"
			varStatus="status">
			<tr>
				<td>${pagedListHolder.nrOfElements - (pagedListHolder.pageSize * pagedListHolder.page) - status.count +1}</td>
				<td>${log.username }</td>
				<td>${log.name }</td>
				<td>${log.datetime }</td>
				<td>${log.ip }</td>
				<td>${log.content}</td>
			</tr>
			<c:set var="index" value="${index-1}" />
		</c:forEach>
	</tbody>
</table>