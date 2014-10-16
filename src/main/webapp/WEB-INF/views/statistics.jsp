<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@ page import="com.freeneo.survey.domain.Question" %><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %><%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %><%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %><jsp:include page="include/header.jsp" />
<div class="limit-width  center-block">
	
	<ul class="nav nav-pills" id="top">
	<c:forEach items="${surveyByBranch }" var="map">
		<li>
			<a href="#${map.key}">${map.key }</a>
		</li>
	</c:forEach>
	</ul>
	
	<c:if test="${listUrl != null}">
		<div class="text-right">
			<a href="${listUrl }" class="btn">목록</a>
		</div>
	</c:if>
	
	<div class="page-header">
		<h1>${pageTitle } <small>${survey.respondentCount }명 응답</small></h1>
	</div>

	<jsp:include page="include/statistics_part.jsp"/>
	
	<c:forEach items="${surveyByBranch }" var="map">
		<c:set var="survey" value="${map.value }" scope="request"/>
		<div class="page-header" style="margin-top: 100px" id="${map.key }">
			<h1>
				${map.key } <small>${survey.respondentCount }명 응답</small>
				<a href="#top" class="btn btn-small pull-right">맨 위로</a>
			</h1>
		</div>
		<jsp:include page="include/statistics_part.jsp"/>
	</c:forEach>
	
	<c:if test="${listUrl != null }">
		<div class="text-right">
			<a href="${listUrl }" class="btn">목록</a>
		</div>
	</c:if>

</div>
<jsp:include page="include/footer.jsp" />