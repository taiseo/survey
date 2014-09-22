<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.freeneo.survey.domain.Question" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<jsp:include page="include/header.jsp" />
<div class="limit-width  center-block">
	
	<ul class="nav nav-pills" id="top">
	<c:forEach items="${surveys }" var="survey">
		<li>
			<a href="#${survey.title}">${survey.title }</a>
		</li>
	</c:forEach>
	</ul>
	
	<div class="page-header">
		<h1>
			${pageTitle } 
			<small>${startDate }~${endDate}</small>
		</h1>
	</div>


	<c:forEach items="${surveys}" var="survey">
		
		<div class="page-header" style="margin-top: 100px" id="${survey.title }">
			<h1>
				${survey.title } <small>${survey.respondentCount }명 응답</small>
				<a href="#top" class="btn btn-small pull-right">맨 위로</a>
			</h1>
		</div>
		<c:set var="survey" value="${survey }" scope="request"/>
		<jsp:include page="include/statistics_part.jsp"/>
	</c:forEach>
</div>
<jsp:include page="include/footer.jsp" />