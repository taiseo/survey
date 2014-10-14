<%@ page language="java" contentType="application/vnd.ms-excel; charset=UTF-8" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %><% 
    response.setHeader("Content-Disposition", "attachment; filename=search-by-brach.xls"); 
    response.setHeader("Content-Description", "JSP Generated Data"); 
%><!DOCTYPE table PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
</head>
<body>
<table class="table table-striped" cellpadding="5" cellspacing="0" border="1">
	<thead>
		<tr>
			<th>구분</th>
			<th>문자 발송</th>
			<th>설문 회신</th>
			<th>회신율</th>
			<th>설문 건수</th>
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
			</tr>
			</c:if>
		</c:forEach>
	</tbody>
</table>
</body>
</html>