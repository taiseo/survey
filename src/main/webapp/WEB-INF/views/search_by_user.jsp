<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<table class="table table-striped">
	<thead>
		<tr>
			<th>부서명</th>
			<th>사용자</th>
			<th>설문조사(건)</th>
			<th>조사 대상자</th>
			<th>회신자</th>
			<th>회신율</th>
			<th>결과 보고</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="userHistoryList" var="userHistory">
			<tr>
				<th>${userHistory.user.part }</th>
				<td>${userHistory.user.name }</td>
				<td>${userHistory.surveyCount }</td>
				<td>${userHistory.sendCount }</td>
				<td>${userHistory.respondentCount }</td>
				<td>
					<fmt:formatNumber value="${userHistory.respondentCount / userHistory.sendCount * 100}" pattern=".00" />%">
				</td>
				<td>
					<a href="#" onclick="alert('제작중'); return false;">(보기)</a>
				</td>
			</tr>
		</c:forEach>
	</tbody>
</table>