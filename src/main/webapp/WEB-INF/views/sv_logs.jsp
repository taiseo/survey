<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%@ taglib uri="http://www.okjsp.pe.kr/ok-taglib" prefix="pagination" %>

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
		<c:forEach items="${pagedListHolder.pageList}" var="log" varStatus="status">
		<tr>
			<td>${pagedListHolder.nrOfElements - (pagedListHolder.pageSize * pagedListHolder.page) - status.count +1}</td>
			<td>${log.username }</td>
			<td>${log.name }</td>
			<td>${log.datetime }</td>
			<td>${log.ip }</td>
			<td>
				<h2 class="jquery-on-container">
					<a class="js-open-target  btn btn-info btn-small" data-target=".closed-content_${index }">펼치기</a>
				</h2>
				<div class="closed-content_${index }">
					${log.content}
				</div>
			</td>
		</tr>
		<c:set var="index" value="${index-1}"/>
		</c:forEach>
	</tbody>
	</table>

	<% String path = request.getContextPath() + "/logs"; %>
	<pagination:paging pageListHolder="${pagedListHolder}" offset="10" contextRoot="<%= path %>"/>

</div>
<jsp:include page="include/footer.jsp" />


