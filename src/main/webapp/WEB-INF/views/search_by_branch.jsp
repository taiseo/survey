<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<div class="base-margin  text-right">
	<button type="button" class="btn btn-info" onclick="$('.js-search-by-branch-excel').click()">Excel Download</button>
</div>

<table class="table table-striped">
	<thead>
		<tr>
			<th>구분</th>
			<th>문자 발송</th>
			<th>설문 회신</th>
			<th>회신율</th>
			<th>설문 건수</th>
			<th>결과 보고</th>
		</tr>
	</thead>
	<tbody>
		<tr>
			<th>전체</th>
			<td>${allSendCount }</td>
			<td>${allRespondentCount }</td>
			<td>
				<fmt:formatNumber value="${allResponseRatio }" pattern="0.00"/>%
			</td>
			<td>${allSurveyCount }</td>
			<td></td>
		</tr>
		<c:forEach items="${branchHistoryList }" var="branchHistory">
			<c:if test="${branchHistory.branchName != '지사 없음' }">
			<tr>
				<th>${branchHistory.branchName }</th>
				<td>${branchHistory.sendCount }</td>
				<td>${branchHistory.respondentCount }</td>
				<td>
					<fmt:formatNumber value="${branchHistory.responseRatio }" pattern="0.00"/>%
				</td>
				<td>${branchHistory.surveyCount }</td>
				<td>
					<a target="_blank" href="<%= request.getContextPath() %>/statistics/branch/${branchHistory.branchName }/${startDate}/${endDate}">
						결과 보기
					</a>
				</td>
			</tr>
			</c:if>
		</c:forEach>
	</tbody>
</table>