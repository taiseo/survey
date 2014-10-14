<%@ page language="java" contentType="application/vnd.ms-excel; charset=UTF-8" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %><% 
    response.setHeader("Content-Disposition", "attachment; filename=search-by-user.xls"); 
    response.setHeader("Content-Description", "JSP Generated Data"); 
%><!DOCTYPE table PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
</head>
<body>
<table class="table table-striped" cellpadding="5" cellspacing="0" border="1">
	<thead>
		<tr>
			<th>부서명</th>
			<th>사용자</th>
			<th>설문조사(건)</th>
			<th>조사 대상자</th>
			<th>회신자</th>
			<th>회신율</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${userHistoryList }" var="userHistory">
			<tr>
				<th>${userHistory.user.part }</th>
				<td>${userHistory.user.name }</td>
				<td>${userHistory.surveyCount }</td>
				<td>${userHistory.sendCount }</td>
				<td>${userHistory.respondentCount }</td>
				<td>
					<fmt:formatNumber value="${userHistory.responseRatio }" pattern="0.00"/>%
				</td>
			</tr>
		</c:forEach>
	</tbody>
</table>
</body>
</html>