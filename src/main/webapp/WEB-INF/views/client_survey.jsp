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
			<c:choose>
				<c:when test="${question.content == '$$$pageBreaker$$$'}">
					<%= "</div>" %>
					<%= "<div class='questions-block' style='display: none'>" %>
				</c:when>
				<c:otherwise>
					<h2>
						${question.content }
						<small>${question.contentDetail }</small>
					</h2>
				</c:otherwise>
			</c:choose>
		</c:forEach>
	</div>
	<p class="text-center">
		<input type="button" value="이전 페이지" class="btn  js-prev" style="display: none" />
		<input type="button" value="다음 페이지" class="btn  js-next" />
	</p>

</div>

<jsp:include page="include/footer.jsp" />