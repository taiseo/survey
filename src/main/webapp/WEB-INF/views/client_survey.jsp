<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="include/header.jsp" />

<div class="limit-width  center-block">

	<div class="page-header">
		<h1>${pageTitle }</h1>
	</div>
	
	<div class="questions-block">
		<div class="well">
			${survey.description }
		</div>
		<c:forEach items="${survey.questions}" var="question">
			<c:set var="question" value="${question }" scope="request"/>
			<c:choose>
				<c:when test="${question.type == '페이지-나누기'}">
					<%= "</div>" %>
					<%= "<div class='questions-block'>" %>
				</c:when>
				<c:when test="${question.type == '주관식1'}">
					<jsp:include page="include/client_answer_question.jsp"/>
				</c:when>
				<c:when test="${question.type == '주관식2'}">
					<jsp:include page="include/client_answer_question.jsp"/>
				</c:when>
				<c:when test="${question.type == '객관식1'}">
					<jsp:include page="include/client_choice_question.jsp"/>
				</c:when>
				<c:when test="${question.type == '객관식2'}">
					<jsp:include page="include/client_choice_question.jsp"/>
				</c:when>
				<c:when test="${question.type == '점수범위'}">
					<jsp:include page="include/client_number_range_question.jsp"/>
				</c:when>
				
			</c:choose>
		</c:forEach>
	</div>
	<p class="text-center">
		<input type="button" value="이전 페이지" class="btn  js-prev" style="display: none" />
		<input type="button" value="다음 페이지" class="btn  js-next" />
	</p>

</div>

<jsp:include page="include/footer.jsp" />