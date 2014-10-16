<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.freeneo.survey.domain.Question" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<jsp:include page="include/header.jsp" />
<div class="limit-width  center-block">
	
	<div class="page-header">
		<h1>
			${pageTitle } 
			<small>${startDate }~${endDate}</small>
		</h1>
	</div>

	<table class="table  table-striped  table-data">
		<thead>
			<tr>

				<th nowrap="nowrap">No</th>
				<th nowrap="nowrap">제목</th>
				<th nowrap="nowrap">조사기간</th>
				<th nowrap="nowrap">대상</th>
				<th nowrap="nowrap">입력일시</th>
				<th nowrap="nowrap">상태</th>
				<th nowrap="nowrap">응답수</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${surveys}" var="survey" varStatus="status">
			
			<tr>
				<td>
					${fn:length(surveys) - status.count +1}
				</td>
				<td>
					<a href="<%=request.getContextPath() %>/statistics/${branch }/${survey.id}">
						${survey.title }
					</a>
				</td>
				<td nowrap="nowrap">${survey.startDate } ~
					<br />
					${survey.endDate }
				</td>
				<td>
					${survey.targetCategory1 }
					${survey.targetCategory2 }
					<%-- ${survey.targetBranches } --%>
				</td>
				<td nowrap="nowrap" class="align-center">${survey.datetime }</td>
				<td>
					${survey.status }
				</td>
				<td>
					${survey.respondentCount }
				</td>
			</tr>
			
			
		</c:forEach>
		</tbody>
	</table>
	
	
</div>
<jsp:include page="include/footer.jsp" />