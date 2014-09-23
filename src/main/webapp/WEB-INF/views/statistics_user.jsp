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


	<c:forEach items="${surveys}" var="survey" varStatus="status">
		
		<h1 class="normal-size  jquery-on-container  no-margin">
			<span class="index-number">
				${fn:length(surveys) - status.count +1}
			</span>
			<a href="#" class="js-open-target" data-target="#survey${survey.id }">
				${survey.title } <small>${survey.respondentCount }명 응답</small>
			</a>
		</h1>
		
		<c:set var="survey" value="${survey }" scope="request"/>
		<div id="survey${survey.id }" style="display: none">
			<jsp:include page="include/statistics_part.jsp"/>
		</div>
		
		<hr class="no-margin" />
		
	</c:forEach>
	
</div>
<jsp:include page="include/footer.jsp" />