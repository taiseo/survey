<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.freeneo.survey.domain.Question" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<jsp:include page="include/header.jsp" />
<div class="limit-width  center-block">
	
	<div class="page-header">
		<h1>${pageTitle } <small>${survey.respondentCount }명 응답</small></h1>
	</div>

    <c:forEach items="${survey.questions }" var="question">
    	<c:if test="${question.type != '페이지-나누기' }">
			<h2>
				${question.content }
				<small>${question.contentDetail }</small>
			</h2>
			<p class="text-right"><small>${question.questionRespondentCount }명<!-- 
				 -->(<fmt:formatNumber 
					value="${question.questionRespondentCount / survey.respondentCount * 100 }"
					pattern=".00" />%) 
				응답</small></p>
			<c:if test="${fn:contains(question.type, '주관식')}">
				<ul>
				<c:forEach items="${question.responses }" var="response">
					<li>${response }</li>
				</c:forEach>
				</ul>
			</c:if>
			<c:if test="${fn:contains(question.type, '객관식')}">
				<table class="table">
				<thead>
					<tr>
						<th>답항</th>
						<th>수</th>
						<th>비율</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${question.responseItems }" var="responseItem">
						<tr>
							<th>${responseItem.content }</th>
							<td>${responseItem.responseItemCount }</td>
							<td>
								<c:if test="${responseItem.responseItemCount > 0 }">
									<fmt:formatNumber 
										value="${responseItem.responseItemCount / survey.respondentCount * 100}"
										pattern=".00" />%
								</c:if>
							</td>
						</tr>
					</c:forEach>
					</tbody>
				</table>
			</c:if>
			<c:if test="${question.type == '점수범위'}">
				<table class="table">
				<thead>
				<tr>
					<th>점수</th>
					<th>수</th>
					<th>비율</th>
				</tr>
				</thead>
				<tbody>
				<c:forEach items="${question.pointResponseCount }" var="point">
					<tr>
						<td>${point.key }</td>
						<td>
							${point.value }
						</td>
						<td>
							<c:if test="${point.value > 0 }">
								<fmt:formatNumber 
									value="${point.value / survey.respondentCount * 100}"
									pattern=".00" />%
							</c:if>
						</td>
					</tr>
				</c:forEach>
				</tbody>
				</table>
			</c:if>
    	</c:if>
    	
    </c:forEach>

</div>
<jsp:include page="include/footer.jsp" />