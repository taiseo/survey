<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="include/header.jsp" />

<div class="limit-width center-block">

	<div class="page-header">
		<h1>${pageTitle }</h1>
	</div>
	
	<table class="table">
	<thead>
		<tr>
			<th>cst_no</th>
		</tr>
	</thead>
	<c:forEach var="target" items="${targets}">
		<tr>
			<td>${target.cstNo }</td>
		</tr>
	</c:forEach>
	</table>
	
</div>
<jsp:include page="include/footer.jsp" />