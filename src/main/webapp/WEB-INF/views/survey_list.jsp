<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="include/header.jsp" />

<div class="limit-width  center-block">
	<h1 class="page-title">${pageTitle}</h1>
	
	<p class="text-center">
		<a href="<%=request.getContextPath() %>/surveys/insert" class="btn  btn-primary  btn-large">
			새 설문
		</a>
	</p>
	
	<ul>
	    <li>목록</li>
	</ul>
</div>

<jsp:include page="include/footer.jsp" />