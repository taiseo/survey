<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.freeneo.survey.domain.Question" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<jsp:include page="include/header.jsp" />
<div class="limit-width  center-block">
	
	<div class="page-header">
		<h1>
			${pageTitle } 
			<small>${startDate }~${endDate}</small>
		</h1>
	</div>
	
	<c:forEach items="${statisticsSurveys }" var="survey">
		<c:set var="survey" value="${survey }" scope="request"/>
		
		<h1 class="normal-size  jquery-on-container">
			<a href="#" class="js-open-target" data-target="#survey${survey.id }">
				${survey.title } <small>${survey.respondentCount }명 응답</small>
			</a>
		</h1>
		
		<div id="survey${survey.id }" style="display: none">
		<jsp:include page="include/statistics_part.jsp"/>
		</div>
		
		
	</c:forEach>

</div>
<jsp:include page="include/footer.jsp" />