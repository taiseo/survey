<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="include/header.jsp" />

<div class="limit-width  center-block  client" data-client="${sessionId}" data-survey-id="${survey.id }">
	<div class="page-header">
		<h1>${pageTitle }</h1>
	</div>
	
	<div class="questions-block">
		<div class="well">
			${survey.description }
		</div>
		<div class="js-search-by-branch">
			<h2>귀하께서 계약을 체결하신 공사의 지사는 어디인가요?</h2>
			<select id="bonbu"></select>
			<select name="branch"></select>
		</div>
		<c:forEach items="${survey.questions}" var="question">
			<c:set var="question" value="${question }" scope="request"/>
			<c:choose>
				<c:when test="${question.type == '페이지-나누기'}">
	<%= "</div>" %>
	<%= "<div class='questions-block' style='display: none'>" %>
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

	<div class="complete-page" style="display: none">
		<div class="hero-unit">
			<h1>감사합니다</h1>
			<p>설문을 완료했습니다.</p>
			<a class="btn  btn-large  js-rewrite">
				다시 작성하기
		    </a>
		</div>
	</div>
	<p class="text-center">
		<input type="button" value="이전 페이지" class="btn  js-prev  js-button" style="display: none" />
		<input type="button" value="다음 페이지" class="btn  js-next  js-button" />
		<input type="button" value="작성 완료" class="btn  btn-primary  btn-large  js-complete  js-button" style="display: none" />
	</p>

</div>

<jsp:include page="include/footer.jsp" />
<script type="text/javascript" src="<%= request.getContextPath() %>/resources/js/client_script.js"></script>
<script type="text/javascript" src="<%= request.getContextPath() %>/resources/js/branches.js"></script>
<script type="text/javascript" src="<%= request.getContextPath() %>/resources/js/branch_functions.js"></script>