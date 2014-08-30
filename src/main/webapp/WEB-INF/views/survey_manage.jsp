<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="include/header.jsp" />
<div class="limit-width  center-block">
	
	<div class="page-header">
		<h1>${pageTitle }</h1>
	</div>
	
	<form:form method="${httpMethod }" modelAttribute="survey">
		<table class="table  table-striped">
		</table>
	</form:form>
</div>
<jsp:include page="include/footer.jsp" />
