<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<jsp:include page="include/header.jsp" />
<div class="limit-width  center-block">

	<div class="page-header">
		<h1>${pageTitle}</h1>
	</div>

	<c:forEach items="${branchList }" var="branch">
	<h2>${branch }</h2>
	ㅇㅇㅇ 사업에 관한 0건의 설문
	</c:forEach>

</div>
<jsp:include page="include/footer.jsp" />