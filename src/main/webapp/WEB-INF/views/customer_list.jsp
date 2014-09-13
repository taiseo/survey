<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="include/header.jsp" />

<div class="limit-width center-block">
	<table class="table">
	<thead>
		<tr>
			<th>no</th>
			<th>이름</th>
			<th>전화</th>
		</tr>
	</thead>
	<c:forEach var="customer" items="${customers}">
		<tr>
			<td>
				${customer.cstNo}
			</td>
			<td>
				${customer.cstNm}
			</td>
			<td>${customer.hp}</td>
		</tr>
	</c:forEach>
	</table>
</div>
<jsp:include page="include/footer.jsp" />