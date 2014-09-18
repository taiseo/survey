<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<table class="table table-striped">
	<thead>
		<tr>
			<th>구분</th>
			<th>문자 발송</th>
			<th>설문 회신</th>
			<th>회신율</th>
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
				<td>
					<form action="#" onclick="alert('제작중');return false">결과 보기</form>
				</td>
			</tr>
			</c:if>
		</c:forEach>
	</tbody>
</table>