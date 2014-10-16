<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %><%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %><jsp:include page="include/header.jsp" />

<div class="limit-width center-block">

	<div class="page-header">
		<h1>${pageTitle }</h1>
	</div>
	
	<div class="base-margin  text-right">
		<a href="${listUrl }" class="btn">목록</a>
	</div>
	
	<table class="table">
	<thead>
		<tr>
			<th>No</th>
			<th>사업명</th>
			<th>계약체결일</th>
			<th>지사명</th>
			<th>성명</th>
			<th>핸드폰</th>
		</tr>
	</thead>
	<c:forEach var="target" items="${targets}" varStatus="status">
		<tr>
			<td>${fn:length(targets) - status.count + 1 }</td>
			<td>${target.ptcpTit }</td>
			<td>${target.ptcpDttm }</td>
			<td>${target.etc01 }</td>
			<td>${target.cstNm }</td>
			<td>${target.hp }</td>
		</tr>
	</c:forEach>
	</table>
	
	<div class="base-margin  text-right">
		<a href="${listUrl }" class="btn">목록</a>
	</div>
	
</div>
<jsp:include page="include/footer.jsp" />