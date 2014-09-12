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
				<table class="table  table-striped">
				<colgroup>
					<col style="width: 50%" />
					<col style="width: 25%" />
				</colgroup>
				<thead>
					<tr>
						<th>답항</th>
						<th>수</th>
						<th>비율</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${question.responseItems }" var="responseItem">
						<c:if test="${responseItem.content != '$$$etc$$$' }">
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
						</c:if>
						
						<c:if test="${responseItem.content == '$$$etc$$$' }">
						<tr>
							<th>
								<a href="#" onclick="$(this).parents('tr').next().slideToggle(); return false;">
									기타
								</a>
							</th>
							<td>${fn:length(question.etcResponses) }</td>
							<td>
								<c:if test="${fn:length(question.etcResponses) > 0 }">
									<fmt:formatNumber 
										value="${fn:length(question.etcResponses) / survey.respondentCount * 100}"
										pattern=".00" />%
								</c:if>
							</td>
						</tr>
						<tr style="display: none">
							<td colspan="3">
								<ul>
								<c:forEach items="${question.etcResponses }" var="etc">
									<li>${etc }</li>
								</c:forEach>
								</ul>
							</td>
						</tr>
						
						<%-- striped 어긋남 방지용 --%>
						<tr style="display: none"></tr>
						</c:if>
					</c:forEach>
					</tbody>
				</table>
			</c:if>
			<c:if test="${question.type == '점수범위'}">
				<table class="table  table-striped">
				<colgroup>
					<col style="width: 50%" />
					<col style="width: 25%" />
				</colgroup>
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
    	<hr />
    </c:forEach>

</div>
<jsp:include page="include/footer.jsp" />